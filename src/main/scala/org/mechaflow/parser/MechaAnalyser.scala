package org.mechaflow.parser

import org.mechaflow.primitives.Module

/**
 *
 */
class MechaAnalyser {

  private val parser = new MechaParser()

  def loadString(source: String, name: String = "Text Source"): Scope = {
    // Parse
    val module: Module = parser.parseString(source, name)

    // Build symbol tables
    val rootScope = new Scope()
    module.buildScope(rootScope)

    rootScope
  }


}
