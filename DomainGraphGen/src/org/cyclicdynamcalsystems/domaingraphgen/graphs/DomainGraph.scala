package org.cyclicdynamcalsystems.domaingraphgen.graphs

import scala.collection.mutable

/**
 * Represents abstract graph consisting of domains and edges.
 */
abstract class DomainGraph extends DotFormat {
  protected val domains = new mutable.TreeSet[Domain]()

  protected val edges = new mutable.TreeSet[Edge]()(Edge.ordering)

  def isEmpty = domains.isEmpty

  def isNonEmpty = !isEmpty

  def getDomains = domains.toVector

  def getEdges = edges.toVector

  def domainsCount = domains.size

  def edgesCount = edges.size

  def getSystem: DynamicalSystem

  def addEdge(edge: Edge) = {
    val e = edges.find(_.compare(edge) == 0).orNull
    if (e == null) edges += edge else e.increaseMultiplicity()
  }

  override def toString =
    "\n\t" + domainsCount + " domains:\n\t\t" +
    getDomains.map(x => x.toString + " valency " + x.getValency).mkString("\n\t\t") +
    "\n\t" + edgesCount + " edges:\n\t\t" +
    getEdges.mkString("\n\t\t")

  def getDomain(domain: Vector[Int]): Domain = {
    val rd = new Domain(getSystem, domain)

    val found = domains.find(_.compare(rd) == 0).orNull
    if (found != null) {
      found
    } else {
      domains += rd
      rd
    }
  }

  def getValencyLevel(valency: Int) = new ValencyLevel(this, valency)

  def getAllLevels = Range(0, this.getSystem.size + 1).reverse.map(getValencyLevel).filter(_.isNonEmpty)


  override def toDot: String = {
    // Map valency levels.
    val levels = getAllLevels.map(_.toDot).map("\t" + _ + "\n").mkString("")
    val edges = getEdges.map(_.toDot).map("\t" + _ + "\n").mkString("")
    "digraph {\n" + levels + "\n" + edges + "}"
  }
}
