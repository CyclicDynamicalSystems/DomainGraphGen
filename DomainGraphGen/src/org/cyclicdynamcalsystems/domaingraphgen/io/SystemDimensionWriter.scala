package org.cyclicdynamcalsystems.domaingraphgen.io

import org.cyclicdynamcalsystems.domaingraphgen.graphs.DynamicalSystem
import org.cyclicdynamcalsystems.domaingraphgen.util.Utils

/**
 * Writes all systems of certain dimensions to files.
 */
class SystemDimensionWriter(dimensions: Int) {
  val path = "graphs/" + dimensions + "D/"

  // Generate all systems.
  val systems = Utils.generateAllProfiles(dimensions).par.map(new DynamicalSystem(_))

  systems.par.map(x => new STDWriter(x, path + x.getProfileAsString, "C:/Program Files (x86)/GraphViz2.38"))
}
