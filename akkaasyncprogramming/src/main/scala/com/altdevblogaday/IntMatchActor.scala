package com.altdevblogaday

import akka.actor.Actor

class IntMatchActor extends Actor {
  def receive = {
    case 1 => self.reply("This is number 1.")
    case 2 => self.reply("This is number 2, it comes after 1.")
    case _ => self.reply("This value is neither 1 nor 2.")
  }
}