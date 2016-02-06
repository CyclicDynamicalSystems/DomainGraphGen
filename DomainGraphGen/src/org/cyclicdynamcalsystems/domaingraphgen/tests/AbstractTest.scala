package org.cyclicdynamcalsystems.domaingraphgen.tests

/**
 * Represents abstract test.
 */
abstract class AbstractTest(name: String) {
  protected def run(): Unit

  private var failed = false

  private def report() = println(this.toString + " " + (if (failed) "failed" else "passed"))

  final def start = {
    println("Running " + this)
    run()
    report()
    !failed
  }

  def check(condition: Boolean, errMessage: String) = if (!condition) {
    println(this + " ERROR: " + errMessage)
    failed = true
  }

  override def toString = name
}
