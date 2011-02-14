package com.altdevblogaday.implicitsforcleanercode.without

case class Vector(val x: Double, val y: Double, val z: Double) {
  def +(vector: Vector) = Vector(x + vector.x, y + vector.y, z + vector.z)
  def -(vector: Vector) = Vector(x - vector.x, y - vector.y, z - vector.z)
  def *(number: Double) = Vector(x * number, y * number, z * number)
  def /(number: Double) = Vector(x / number, y / number, z / number)
  def dot(vector: Vector): Double = (x * vector.x) + (y * vector.y) + (z * vector.z)
}