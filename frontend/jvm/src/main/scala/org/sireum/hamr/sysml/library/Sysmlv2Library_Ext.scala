package org.sireum.hamr.sysml.library

import org.sireum._
import org.sireum.$internal.{RC, Trie}

object Sysmlv2Library_Ext {

  def mockLibraryMap:scala.collection.SortedMap[scala.Vector[Predef.String], Predef.String] = RC.text(Vector("./mock.sysml.library")) { (p, f) =>
    val filename = p.last
    if (filename.endsWith(".sysml")) true
    else false
  }

  def mockLibrary: ISZ[(Option[String], String)] =
    ISZ(mockLibraryMap.toSeq.
      map(p => (Some(String(p._1.mkString("/"))), String(p._2))): _*)

  def files: ISZ[(Option[String], String)] = mockLibrary
}
