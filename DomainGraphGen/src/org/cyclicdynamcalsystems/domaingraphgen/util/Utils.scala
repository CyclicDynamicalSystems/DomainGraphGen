package org.cyclicdynamcalsystems.domaingraphgen.util

import scala.collection.mutable.ArrayBuffer

/**
 * Contains some useful util functions.
 */
object Utils {
  private def generationHelper(v: Int, profile: Array[Char], ret: ArrayBuffer[String]): Unit = {
    if (v == profile.size) {
      ret += profile.mkString("")
    } else {
      for (c <- Vector('-', '+')) {
        profile(v) = c
        generationHelper(v + 1,profile, ret)
      }
    }
  }

  def generateAllProfiles(dimension: Int): Vector[String] = {
    val ret = new ArrayBuffer[String]()
    generationHelper(0, new Array[Char](dimension), ret)
    ret.toVector
  }
}
