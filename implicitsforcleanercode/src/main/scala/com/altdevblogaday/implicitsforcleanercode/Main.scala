package com.altdevblogaday.implicitsforcleanercode

import com.altdevblogaday.implicitsforcleanercode.without.{Vector => StockVector}
import com.altdevblogaday.implicitsforcleanercode.withimplicits.{Vector => ImplicitVector}
import com.altdevblogaday.implicitsforcleanercode.withimplicits.ImplicitConversions._
import game.{JSON, Game}

object Main {
  def without() = {
    val first = StockVector(1.2, 9.7, 10.2)
    val second = StockVector(0, 5, 5)
    println(first.dot(second))
  }

  def withimplicits() = {
    val first = ImplicitVector(1.2, 9.7, 10.2)
    val second = ImplicitVector(0, 5, 5)
    println(first.dot(second))
  }

  def main(args: Array[String]): Unit = {
    without()
    withimplicits()
    Game.implicitConversionsAndParametersJSON()
    Game.implicitConversionsAndParametersDirectType()
  }
}