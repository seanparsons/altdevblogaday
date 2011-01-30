package com.altdevblogaday

import akka.actor.Actor._
import System.currentTimeMillis
import akka.dispatch.Futures
import akka.actor._
import akka.config.Supervision._

object AkkaAsync {
  val sean = new Player("Sean", 5, 10)
  val mike = new Player("Mike", 5, 5)

  def main(args: Array[String]): Unit = {
    try
    {
      // Basic actor.
      println("Basic actor test.")
      val intMatchActor = actorOf[IntMatchActor].start
      printlnRequestResult(intMatchActor, 1)
      printlnRequestResult(intMatchActor, 2)
      printlnRequestResult(intMatchActor, 99)
      println()

      // Run two long processes simultaneously.
      println("Two processes simultaneously test.")
      val firstLongProcessActor = actorOf[FirstLongProcessActor].start
      val secondLongProcessActor = actorOf[SecondLongProcessActor].start
      val startTime = currentTimeMillis
      val firstFuture = firstLongProcessActor !!! 1000
      val secondFuture = secondLongProcessActor !!! 1000
      Futures.awaitAll(List(firstFuture, secondFuture))
      val timeTaken = currentTimeMillis - startTime
      println("Process took " + timeTaken + "ms.")
      println()

      // Broken library test.
      println("Broken library test.")
      val supervisor = Supervisor(SupervisorConfig(
        AllForOneStrategy(List(classOf[IllegalStateException]), Some(3), Some(1000)),
        Supervise(actorOf[BrokenActor], Permanent) :: Nil
      ))
      supervisor.start
      val brokenActor = supervisor.children.head
      printlnRequestResult(brokenActor, 1)
      printlnRequestResult(brokenActor, 5)
      try {
        printlnRequestResult(brokenActor, 20)
      } catch {
        case _=> println("Expected exception thrown.")
      }
      printlnRequestResult(brokenActor, 5)
      println()

      // Game server like actor.
      println("Game server test.")
      val gameActor = actorOf(new GameActor(List(sean, mike))).start
      printlnRequestResult(gameActor, new PlayerMoved("Sean", 2, 10))
      printlnRequestResult(gameActor, new ShotFired("Mike", 5, 10))
      printlnRequestResult(gameActor, new ShotFired("Sean", 5, 5))
      printlnRequestResult(gameActor, new PlayerMoved("Mike", 2, 2))
      printlnRequestResult(gameActor, new ShotFired("Mike", 2, 10))
      printlnRequestResult(gameActor, new MedikitUsed("Sean", 0))
      printlnRequestResult(gameActor, new MedikitUsed("Mike", 1))
      printlnRequestResult(gameActor, new ShotFired("Sean", 2, 2))
      printlnRequestResult(gameActor, new MedikitUsed("Mike", 2))
      printlnRequestResult(gameActor, new SpecialWeaponUsed("Sean", new NuclearBomb(20)))
      println()
    }
    finally {
      // Turn off all the actors at once.
      println("Shutting down actors.")
      registry.shutdownAll()
    }
  }

  def printlnRequestResult(actor: ActorRef, request: Any) = {
    val response = actor !! request
    println("Request = " + request + ", Result = " + response)
  }
}