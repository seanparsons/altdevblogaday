package com.altdevblogaday.implicitsforcleanercode.game

abstract class WorldObject

case class Sky() extends WorldObject

case class Box(var x: Int, var y: Int) extends WorldObject

case class Car(var x: Double, var y: Double) extends WorldObject