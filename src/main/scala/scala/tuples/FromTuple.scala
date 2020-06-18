package scala.tuples


trait FromTuple[T] {
  type In
  def apply(x: In): T
}

trait LowPriorityFromTuple {
  import FromTuple._
//
//  implicit def productToTuple[T <: Product]: ToTuple[T] = new ToTuple[T] {
//    override type Out = Product
//    override def apply(x: T): Out = ProductToTuple(x)
//  }

  implicit def tupleFromTuple[T: IsTuple]: Aux[T, T] = new FromTuple[T] {
    override type In = T
    override def apply(x: T): T = x
  }

}

object FromTuple extends LowPriorityFromTuple {
  type Aux[TT, T] = FromTuple[T] {type In = TT}

  implicit def exportedFromTuple[TT, T](implicit exported: ExportedFrom[TT, T]): FromTuple.Aux[TT, T] = exported.x

  implicit def derivedFromTuple[TT, T](implicit derived: DerivedFrom[TT, T]): FromTuple.Aux[TT, T] = derived.x

  class Ops[TT](tuple: TT) {
    def to[T](implicit tt: Aux[TT, T]): T = tt(tuple)
  }

  //  implicit def fromTuple1ToAny[T]: Aux[Tuple1[T], T] = new FromTuple[T] {
  //    override type In = Tuple1[T]
  //    override def apply(x: In): T = x._1
  //  }

  def apply[TT](x: TT): Ops[TT] = new Ops(x)
}
