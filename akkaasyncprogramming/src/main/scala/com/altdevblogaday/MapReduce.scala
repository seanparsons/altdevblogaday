package com.altdevblogaday

import java.util.concurrent.atomic.AtomicInteger
import akka.actor._
import akka.actor.Actor._
import akka.dispatch.Future

case class Reduce(val size: Int)

case class MappingDetail[T](val item: T, val reducer: ActorRef)

class MapperActor[T, U](val mapping: (T) => U) extends Actor {
  def receive = {
    case MappingDetail(item: T, reducer) => reducer ! mapping(item)
  }
}

class ReducerActor[U, V](val initialValue: V, val reduction: (V, U) => V) extends Actor {
  def receive = {
    case Reduce(size) => {
      val counter = new AtomicInteger(size)
      val senderFuture = self.senderFuture()
      var result = initialValue
      become {
        case mappedItem: U => {
          result = reduction(result, mappedItem)
          if (counter.decrementAndGet <= 0) {
            unbecome
            senderFuture.foreach(_.completeWithResult(result))
          }
        }
      }
    }
  }
}

object MapReduce {
  def mapReduce[T, U, V](values: Seq[T],
                         mapping: (T) => U,
                         reduction: (V, U) => V,
                         initialReductionValue: V): V = {
    val reducerActor = actorOf(new ReducerActor[U, V](initialReductionValue, reduction)).start
    val future: Future[V] = reducerActor !!! new Reduce(values.size)
    values.foreach(value => actorOf(new MapperActor[T, U](mapping)).start ! MappingDetail(value, reducerActor))
    return future.awaitBlocking.result.get
  }

  def main(args: Array[String]): Unit = {
    val mapping: (Int) => (Int) = { number =>
      Thread.sleep(1000)
      number * 2
    }
    val reduction: (Int, Int) => (Int) = { (left, right) =>
      left + right
    }
    val numbers = (1 to 11)
    println("Result of mapped sum = " + mapReduce(numbers, mapping, reduction, 0))
    Actor.registry.shutdownAll()
  }
}