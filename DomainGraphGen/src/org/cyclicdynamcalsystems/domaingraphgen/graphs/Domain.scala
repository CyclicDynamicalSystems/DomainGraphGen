package org.cyclicdynamcalsystems.domaingraphgen.graphs

import scala.collection.mutable.ArrayBuffer

/**
 * Represents a domain of a dynamical system.
 */
class Domain(dynamicalSystem: DynamicalSystem, domain: Vector[Int], origin: Domain) extends Ordered[Domain] {
  def this(dynamicalSystem: DynamicalSystem, domain: Vector[Int]) = this(dynamicalSystem, domain, null)

  private val incoming = new ArrayBuffer[Edge]()

  private val outcoming = new ArrayBuffer[Edge]()

  def isOriginal = origin == null

  def getDomain = domain

  def getSystem = dynamicalSystem

  def size = getSystem.size

  def at(i: Int) = getDomain(((i % size) + size) % size)

  def incomingEdges = incoming.toVector

  def outcomingEdges = outcoming.toVector

  def getValency: Int = if (isOriginal) outcoming.size else origin.getValency

  def getValencyWithinLevel = outcoming.count(_.isWithinLevel)

  def hasOutBy(i: Int) = (at(i) ^ at(i - 1) ^ getSystem.at(i)) == 0

  def invert(i: Int) = getDomain.updated(i, 1 - getDomain(i))

  def addIncoming(edge: Edge) = incoming += edge

  def addOutcoming(edge: Edge) = outcoming += edge

  def hasEdgeFrom(from: Domain) = outcoming.find(_.source.compare(from) == 0).orNull != null

  def hasEdgeTo(to: Domain) = outcoming.find(_.dest.compare(to) == 0).orNull != null

  override def compare(that: Domain): Int = {
    for (i <- Range(0, size)) {
      if (this.getDomain(i) != that.getDomain(i)) return this.getDomain(i) - that.getDomain(i)
    }
    0
  }

  override def toString = getDomain.mkString("")

  private def shift(shift: Int) = getDomain.takeRight(size - shift) ++ getDomain.take(shift)

  private def genShifts = domain.indices.map(shift)

  protected def prototype: Domain = genShifts.map(getSystem.getDomain).min

  def this(origin: Domain) = this(origin.prototype.getSystem, origin.prototype.getDomain, origin)
}

object Domain {
  implicit val ordering = new Ordering[Domain]() {
    override def compare(x: Domain, y: Domain): Int = x.compare(y)
  }
}