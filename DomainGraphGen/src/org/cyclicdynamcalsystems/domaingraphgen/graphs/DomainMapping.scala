package org.cyclicdynamcalsystems.domaingraphgen.graphs

/**
 * Represents abstract domain mapping <domain -> domain>.
 */
class DomainMapping(from: Domain, to: Domain) extends Ordered[DomainMapping]{
  def source = from

  def dest = to

  override def toString = source + " -> " + dest

  override def compare(o: DomainMapping): Int =
    if (source.compare(o.source) == 0) dest.compare(o.dest) else source.compare(o.source)
}

object DomainMapping {
  implicit val ordering = new Ordering[DomainMapping]() {
    override def compare(x: DomainMapping, y: DomainMapping): Int = x.compare(y)
  }
}