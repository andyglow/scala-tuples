package scala.tuples.generic

import scala.tuples._
import scala.tuples.ToTuple._
import scala.tuples.FromTuple._


object auto {

  implicit def deriveToTuple[T]: ExportedTo[T] = macro macros.ToTupleMacro.exported[T]

  implicit def deriveFromTuple[TT, T]: ExportedFrom[TT, T] = macro macros.FromTupleMacro.exported[TT, T]
}
