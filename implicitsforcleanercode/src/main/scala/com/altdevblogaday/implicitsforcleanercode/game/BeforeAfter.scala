package com.altdevblogaday.implicitsforcleanercode.game

case class BeforeAfter[T](before: T, after: T)

object BeforeAfter {
  def captureChanges[T, U](toCapture: T, modification: () => T)(implicit converter: Converter[T, U]): BeforeAfter[U] = {
    val before = converter(toCapture)
    val after = converter(modification())
    return new BeforeAfter[U](before, after)
  }
  implicit val carIdentityConverter = new CarIdentityConverter()
  implicit val boxIdentityConverter = new BoxIdentityConverter()
  implicit val carJSONConverter = new CarJSONConverter()
  implicit val boxJSONConverter = new BoxJSONConverter()
}

abstract class Converter[-T, +U] extends Function1[T, U]

case class JSON(content: String)

case class CarIdentityConverter() extends Converter[Car, Car] {
  def apply(car: Car): Car = car.copy()
}

case class BoxIdentityConverter() extends Converter[Box, Box] {
  def apply(box: Box): Box = box.copy()
}

case class CarJSONConverter() extends Converter[Car, JSON] {
  def apply(car: Car): JSON = JSON("""{
    "x":"%s"
    "y":"%s"
    }""".format(car.x, car.y))
}

case class BoxJSONConverter() extends Converter[Box, JSON] {
  def apply(box: Box): JSON = JSON("""{
    "x":"%s"
    "y":"%s"
    }""".format(box.x, box.y))
}