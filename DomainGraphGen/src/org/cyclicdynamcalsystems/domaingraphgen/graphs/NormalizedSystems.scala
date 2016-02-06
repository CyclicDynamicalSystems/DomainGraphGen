package org.cyclicdynamcalsystems.domaingraphgen.graphs

/**
 * Stores normalized systems for predefined dimensions.
 */
object NormalizedSystems {
  private def getNormalizedProfile(size: Int, even: Boolean): String = {
    if (even) "-" * size else "+" + getNormalizedProfile(size - 1, even = true)
  }

  private val minSize = 2
  private val maxSize = 12
  private val evenSystems = Range(minSize, maxSize).map(getNormalizedProfile(_, even = true)).map(new DynamicalSystem(_))
  private val oddSystems = Range(minSize, maxSize).map(getNormalizedProfile(_, even = false)).map(new DynamicalSystem(_))

  def getNormalizedSystem(dynamicalSystem: DynamicalSystem) = {
    if (dynamicalSystem.isEven) {
      evenSystems(dynamicalSystem.size - minSize)
    } else {
      oddSystems(dynamicalSystem.size - minSize)
    }
  }
}
