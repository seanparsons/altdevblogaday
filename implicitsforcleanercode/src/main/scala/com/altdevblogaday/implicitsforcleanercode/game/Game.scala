package com.altdevblogaday.implicitsforcleanercode.game

import PushableImplicits._
import BeforeAfter._

object Game {
  def implicitConversionsAndParametersJSON() = {
    val car = new Car(1, 20)
    val box = new Box(4, 20)
    val player = new Player()
    println(box.push[JSON](new Direction(-1, 10)))
    println(car.push[JSON](new Direction(1.5, 12.25)))
  }
  def implicitConversionsAndParametersDirectType() = {
    val car = new Car(1, 20)
    val box = new Box(4, 20)
    val player = new Player()
    println(box.push[Box](new Direction(-1, 10)))
    println(car.push[Car](new Direction(1.5, 12.25)))
  }
}