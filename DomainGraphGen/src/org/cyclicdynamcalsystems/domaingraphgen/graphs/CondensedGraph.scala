package org.cyclicdynamcalsystems.domaingraphgen.graphs

import scala.collection.mutable

/**
 * Implements condensation of graph.
 */
class CondensedGraph(dynamicalSystem: DynamicalSystem) extends DomainGraph {
  val condensationMapping = new mutable.TreeSet[DomainMapping]()

  override def getSystem: DynamicalSystem = dynamicalSystem

  domains ++= getSystem.getDomains.map(x => {
    val y = new Domain(x)
    condensationMapping += new DomainMapping(x, y)
    y
  })

  getSystem.getEdges.map(e => {
    val source = condensationMapping.find(_.source == e.source).orNull.dest
    val dest = condensationMapping.find(_.source == e.dest).orNull.dest

    addEdge(new Edge(source, dest))
  })

  def getMapping = condensationMapping.mkString("\n")

  override def getDomains = {
    val unique = condensationMapping.map(_.dest).toSet
    unique.toVector
  }
}
