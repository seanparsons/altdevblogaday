package com.altdevblogaday

import akka.actor.Actor

class SecondLongProcessActor extends Actor {
  def receive = {
    case number: Int => {
      println("2: Sleeping for " + number + "ms.")
      Thread.sleep(number)
      println("2: Slept for " + number + "ms.")
      self.reply(null)
    }
  }
}