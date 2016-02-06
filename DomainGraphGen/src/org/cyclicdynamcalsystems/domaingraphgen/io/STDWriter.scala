package org.cyclicdynamcalsystems.domaingraphgen.io

import java.io.{File, PrintWriter}

import org.cyclicdynamcalsystems.domaingraphgen.graphs.DynamicalSystem
import sys.process._

/**
 * Writes STD to files.
 */
class STDWriter(system: DynamicalSystem, outputDir: String, graphVizPath: String) {
  private val graphVizDotPath = graphVizPath + "/bin/dot.exe"

  private def writeDotGraphToFile(text: String, dir: String, fileName: String) = {
    if (text != null) {
      def dirFile = new File(dir)
      if (dirFile.exists() || dirFile.mkdirs()) {
        val dotFile = dir + "/" + fileName + ".dot"
        val pngFile = dir + "/" + fileName + ".png"
        new PrintWriter(dotFile) { write(text); close() }
        val dotCommand = "\"" + graphVizDotPath + "\""  + " -Tpng " + dotFile + " -o " + pngFile
        dotCommand !
      } else {
        println("Cannot create dir: " + dir)
      }
    }
  }

  private def writeMappingToFile(text: String, dir: String, fileName: String) = {
    if (text != null) {
      def dirFile = new File(dir)
      if (dirFile.exists() || dirFile.mkdirs()) {
        val path = dir + "/" + fileName
        new PrintWriter(path) { write(text); close() }
      } else {
        println("Cannot create dir: " + dir)
      }
    }
  }

  writeDotGraphToFile(system.getDotGraph, outputDir, "graph")

  writeDotGraphToFile(system.getDotCondensedGraph, outputDir, "condensed_graph")

  writeMappingToFile(system.getCondensationMapping, outputDir, "condensed_mapping.txt")

  writeMappingToFile(system.getIsomorphismMapping, outputDir, "isomorphism_mapping.txt")
}
