package org.cyclicdynamcalsystems.domaingraphgen.tests

import org.cyclicdynamcalsystems.domaingraphgen.graphs.{DynamicalSystem, Isomorphism}
import org.cyclicdynamcalsystems.domaingraphgen.util.Utils

/**
 * Tests N-dimensional isomorphism.
 */
class IsomorphismTest(dimension: Int) extends AbstractTest("Isomorphism " + dimension + "D") {


  override protected def run(): Unit = {
    val profiles = Utils.generateAllProfiles(dimension)
    val systems = profiles.map(new DynamicalSystem(_))
    for (from <- systems) for (to <- systems) if (from.isEven == to.isEven) {
//      println("Checking isomorphism between " + from.getProfileAsString + " and " + to.getProfileAsString)
      val isomorphism = new Isomorphism(from, to)
      check(isomorphism.validate,
        "Wrong isomorphism between " + from.getProfileAsString + " and " + to.getProfileAsString +
          "\n(transformations: " + isomorphism.transformationSequence.mkString(", ") + ")")
    }
  }
}
