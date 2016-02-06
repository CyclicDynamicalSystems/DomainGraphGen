package org.cyclicdynamcalsystems.domaingraphgen

import org.cyclicdynamcalsystems.domaingraphgen.io.SystemDimensionWriter
import org.cyclicdynamcalsystems.domaingraphgen.tests.AllTests

/**
 * Main runner.
 */
object DomainGraphGen {
  def main(args: Array[String]) {
    val allTests = new AllTests()
    allTests.start

    Range(3, 11).map(new SystemDimensionWriter(_))
  }
}
