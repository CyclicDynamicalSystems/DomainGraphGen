package org.cyclicdynamcalsystems.domaingraphgen.graphs

/**
 * Represents a valency level.
 */
class ValencyLevel(graph: DomainGraph, valency: Int) extends DomainGraph {
  override def getSystem = graph.getSystem

  def getValency = valency

  override def toString = "Valency level " + getValency + super.toString

  domains ++= graph.getDomains.filter(_.getValency == valency)

  edges ++= graph.getEdges.filter(x => x.isWithinLevel && x.source.getValency == valency)

  override def toDot: String = "subgraph level_" + getValency + " { " +
    "rank = same; node [shape = box] " +
    getDomains.mkString("; ") +
    "};"
}
