package org.mechaflow.primitives

import collection.mutable

/**
 *
 */
trait Prettyable {

  def prettyPrint(builder: StringBuilder, indent: String = "")

  def prettyList(l: Seq[_ <: Prettyable], separator: String, builder: StringBuilder, indent: String, addSeparatorAfterLast: Boolean = false) {
    var first = true
    l foreach {e =>
      if (first) first = false
      else {
        builder append separator
      }

      e.prettyPrint(builder, indent)
    }

    if (!l.isEmpty && addSeparatorAfterLast) builder append separator
  }

  def prettySymbols(l: Seq[Symbol], separator: String, builder: StringBuilder, indent: String) {
    var first = true
    l foreach {e =>
      if (first) first = false
      else {
        builder append separator
      }

      builder append e.name
    }
  }

  def prettyString(l: Seq[String], separator: String, builder: StringBuilder, indent: String) {
    var first = true
    l foreach {e =>
      if (first) first = false
      else {
        builder append separator
      }

      builder append e
    }
  }

  override def toString: String = {
    val sb = new mutable.StringBuilder()
    prettyPrint(sb)
    sb.toString()
  }
}