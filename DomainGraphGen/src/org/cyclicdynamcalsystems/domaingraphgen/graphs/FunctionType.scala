package org.cyclicdynamcalsystems.domaingraphgen.graphs

/**
 * Represents a type of a function.
 */
object FunctionType extends Enumeration {
  type FunctionType = Value

  val MonotonicallyDecreasing, MonotonicallyIncreasing = Value

  def asChar(functionType: FunctionType) = functionType match {
    case MonotonicallyDecreasing => "-"
    case MonotonicallyIncreasing => "+"
  }

  def fromChar(c: Char) = c match {
    case '-' => MonotonicallyDecreasing
    case '+' => MonotonicallyIncreasing
  }

  def asInt(functionType: FunctionType) = functionType match {
    case MonotonicallyDecreasing => 0
    case MonotonicallyIncreasing => 1
  }
}
