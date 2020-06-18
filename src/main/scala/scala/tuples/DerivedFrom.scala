package scala.tuples

case class DerivedFrom[TT, T](x: FromTuple.Aux[TT, T]) extends AnyVal
