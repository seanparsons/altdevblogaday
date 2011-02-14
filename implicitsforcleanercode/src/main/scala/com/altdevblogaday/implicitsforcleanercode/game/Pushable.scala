package com.altdevblogaday.implicitsforcleanercode.game

case class Direction(x: Double, y: Double)

object PushableImplicits {
  implicit def carToPushable(car: Car) = new CarPushable(car)
  implicit def boxToPushable(box: Box) = new BoxPushable(box)
}

abstract class Pushable[T <: WorldObject] {
  def push[U](direction: Direction)(implicit converter: Converter[T, U]): BeforeAfter[U]
}

case class CarPushable(val car: Car) extends Pushable[Car] {
  def push[U](direction: Direction)(implicit converter: Converter[Car, U]): BeforeAfter[U] = {
    BeforeAfter.captureChanges(car, {() =>
      car.x += direction.x
      car.y += direction.y
      car
    })
  }
}

case class BoxPushable(val box: Box) extends Pushable[Box] {
  def push[U](direction: Direction)(implicit converter: Converter[Box, U]): BeforeAfter[U]= {
    // In this game boxes can only be moved along the 2 main axes.
    BeforeAfter.captureChanges(box, {() =>
      if (direction.x.abs > direction.y.abs) {
        box.x += direction.x.toInt
      }
      else {
        box.y += direction.y.toInt
      }
      box
    })
  }
}