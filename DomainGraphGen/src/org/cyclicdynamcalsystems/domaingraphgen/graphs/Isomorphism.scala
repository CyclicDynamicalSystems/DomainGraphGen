package org.cyclicdynamcalsystems.domaingraphgen.graphs

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * Represents isomorphism between dynamical systems.
 */
class Isomorphism(from: DynamicalSystem, to: DynamicalSystem) {
  private val mapping = new mutable.TreeSet[DomainMapping]()

  def get(domain: Domain) = mapping.find(_.source.compare(domain) == 0).orNull.dest

  private def getNormalizingSequence(system: DynamicalSystem): Vector[Int] = {
    val answer = new ArrayBuffer[Int]()

    val profile = system.getProfile

    for (i <- Range(0, system.size)) if (profile(i) == 1) {
      val target = profile.take(i).count(_ == 1)
      answer ++= Range(target, i)
    }

    answer.toVector
  }

  private def getDimensionAdjustingSequence = {
    val x = from.getIncreasingFunctionsCount
    val y = to.getIncreasingFunctionsCount
    val start = math.min(x, y)
    val end = math.max(x, y)
    Range(start, end , 2)
  }

  def getTransformationSequence = {
    getNormalizingSequence(from).reverse ++ getDimensionAdjustingSequence ++ getNormalizingSequence(to)
  }


  def validate: Boolean = {
    for (edge <- from.getEdges) {
      val mapSource = get(edge.source)
      val mapDest = get(edge.dest)
      if (!to.hasEdge(mapSource, mapDest)) {
        println("ISOMORPHISM ERROR: no corresponding edge for " +
          edge + " (should be " + mapSource + " -> " + mapDest + ")")
        return false
      }
    }

    true
  }

  val transformationSequence = getTransformationSequence

  override def toString = "Mapping from " + from.getProfileAsString + " to " + to.getProfileAsString +
    "\n" + mapping.toVector.mkString("\n")

  for (domain <- from.getDomains) {
    var mapTo: Vector[Int] = domain.getDomain
    for (i <- transformationSequence) {
      mapTo = mapTo.updated(i, 1 - mapTo(i))
    }

    val target = to.getDomain(mapTo)
    mapping += new DomainMapping(domain, target)
  }
}
