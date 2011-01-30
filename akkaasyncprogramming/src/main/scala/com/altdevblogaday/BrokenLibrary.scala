package com.altdevblogaday

case class BrokenLibrary(var counter: Int = 0) {
  def maybeBreak(importantNumber: Int): Int = {
    counter += importantNumber
    if (counter > 10) throw new IllegalStateException("Oh noes I have been written badly.")
    return counter
  }
}