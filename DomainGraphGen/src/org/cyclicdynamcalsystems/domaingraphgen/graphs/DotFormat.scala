package org.cyclicdynamcalsystems.domaingraphgen.graphs

/**
 * This entity can be represented in DOT.
 */
trait DotFormat {
  def toDot: String
}
