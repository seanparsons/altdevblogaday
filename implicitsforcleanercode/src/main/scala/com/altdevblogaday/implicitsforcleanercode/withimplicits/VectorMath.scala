package com.altdevblogaday.implicitsforcleanercode.withimplicits

object ImplicitConversions {
  implicit def vectorToMath(vector: Vector) = new VectorMath(vector)
}

case class VectorMath(leftHandSide: Vector) {
  def dot(rightHandSide: Vector): Double = (leftHandSide.x * rightHandSide.x) + (leftHandSide.y * rightHandSide.y) + (leftHandSide.z * rightHandSide.z)
}
