package scala.tuples.generic

import scala.tuples._

object semiauto {

  def deriveToTuple[T]: DerivedTo[T] = macro macros.ToTupleMacro.derived[T]

  def deriveFromTuple[TT, T]: DerivedFrom[TT, T] = macro macros.FromTupleMacro.derived[TT, T]
}
