package org.cyclicdynamcalsystems.domaingraphgen.tests

/**
 * Runs all tests together.
 */
class AllTests extends AbstractTest("All tests") {
  override def run(): Unit = {
    val tests = Vector[AbstractTest](
    ) ++
    Range(2, 6).map(new IsomorphismTest(_))
    tests.foreach(x => check(x.start, x + " failed"))
  }
}
