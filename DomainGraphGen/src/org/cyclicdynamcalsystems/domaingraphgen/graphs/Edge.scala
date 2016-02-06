package org.cyclicdynamcalsystems.domaingraphgen.graphs

/**
 * Represents graph's edge.
 */
class Edge(from: Domain, to: Domain) extends DomainMapping(from, to) with DotFormat {
  private var multiplicity = 1

  def getSystem = source.getSystem

  def isWithinLevel = source.getValency == dest.getValency

  def getMultiplicity = multiplicity

  def increaseMultiplicity() = multiplicity = multiplicity + 1

  override def toDot: String = {
    val color = if (isWithinLevel) "red" else "blue"
    // TODO Add label?
    "edge [color = " + color + ", label = \"\"] " + this.toString + " ;"
  }
}

object Edge {
  implicit val ordering = new Ordering[Edge]() {
    override def compare(x: Edge, y: Edge): Int = x.compare(y)
  }
}