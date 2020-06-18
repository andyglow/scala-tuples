package scala.tuples

case class ExportedFrom[TT, T](x: FromTuple.Aux[TT, T]) extends AnyVal
