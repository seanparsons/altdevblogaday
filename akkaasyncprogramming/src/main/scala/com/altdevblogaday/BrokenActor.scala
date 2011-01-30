package com.altdevblogaday

import akka.actor.Actor


class BrokenActor extends Actor {
  var brokenLibrary = new BrokenLibrary()
  def receive = {
    case importantNumber: Int => self.reply_?(brokenLibrary.maybeBreak(importantNumber))
  }

  override def postRestart(reason: Throwable) = {
    println("Creating new BrokenLibrary.")
    brokenLibrary = new BrokenLibrary()
  }
}