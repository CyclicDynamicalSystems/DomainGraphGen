package org.cyclicdynamcalsystems.domaingraphgen.graphs

import org.cyclicdynamcalsystems.domaingraphgen.graphs.FunctionType._

/**
 * Represents a cyclic dynamical system of form:
 *
 * dx_i/dt = f_i(x_i-1) - x_i
 */
class DynamicalSystem(profileString: String) extends DomainGraph {
  def hasEdge(source: Domain, dest: Domain): Boolean =
    edges.find(x => x.source.compare(source) == 0 && x.dest.compare(dest) == 0).orNull != null

  private val profile = profileString.toVector.map(fromChar)

  def getProfile = profile.map(asInt)

  def getProfileAsString = profileString

  def size = profile.size

  def getIncreasingFunctionsCount: Int = profile.count(_ == MonotonicallyIncreasing)

  def isEven = (getIncreasingFunctionsCount % 2) == 0

  def isOdd = !isEven

  def isSmall = size <= 5

  def isBig = size >= 10

  def isMedium = !isSmall && !isBig

  def at(i: Int) = asInt(profile(((i + size) % size) % size))

  override def toString = "System " + profileString + " (" + size + "D)" + super.toString

  def transitionBy(from: Domain, i: Int): Domain = {
    if (from.hasOutBy(i)) getDomain(from.invert(i)) else null
  }

  private def buildDomainsHelper(v: Int, domain: Array[Int]): Unit = {
    if (v == size) {
      getDomain(domain.toVector)
    } else {
      for (i <- Range(0, 2)) {
        domain(v) = i
        buildDomainsHelper(v + 1, domain)
      }
    }
  }

  def buildDomains() = buildDomainsHelper(0, new Array[Int](size))

  def buildEdges() = for (from <- getDomains) {
    for (to <- Range(0, size).map(transitionBy(from, _)).filter(_ != null)) {
      val edge = new Edge(from, to)
      from.addOutcoming(edge)
      to.addIncoming(edge)
      addEdge(edge)
    }
  }

  override def getSystem: DynamicalSystem = this

  def isCondensable = getIncreasingFunctionsCount == 0 || (isEven && getIncreasingFunctionsCount == size)

  def getCondensate = if (isCondensable) new CondensedGraph(this) else null

  override def toDot: String = {
    var ret = ""
    if (isSmall) ret += super.toDot + "\n"
    if (isCondensable) ret += getCondensate.toDot + "\n"
    ret
  }

  def getDotGraph: String = if (isSmall) super.toDot else null

  def getDotCondensedGraph: String = if (isCondensable) getCondensate.toDot else null

  def getCondensationMapping = {
    if (isCondensable) {
      getCondensate.getMapping
    } else {
      null
    }
  }

  def getIsomorphismMapping = new Isomorphism(this, NormalizedSystems.getNormalizedSystem(this)).toString

  buildDomains()
  buildEdges()
}
