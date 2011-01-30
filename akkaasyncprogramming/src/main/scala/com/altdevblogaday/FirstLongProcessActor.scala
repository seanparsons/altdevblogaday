package com.altdevblogaday

import akka.actor.Actor

class FirstLongProcessActor extends Actor {
  def receive = {
    case number: Int => {
      println("1: Sleeping for " + number + "ms.")
      Thread.sleep(number)
      println("1: Slept for " + number + "ms.")
      self.reply(null)
    }
  }
}