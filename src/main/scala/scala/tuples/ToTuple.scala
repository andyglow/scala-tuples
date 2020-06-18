package scala.tuples


trait ToTuple[T] {
  type Out

  def apply(x: T): Out
}

object ToTuple extends LowPriorityToTuple {
  type Aux[T, TT] = ToTuple[T] { type Out = TT }

  implicit def tupleToTuple[T: IsTuple]: Aux[T, T] = new ToTuple[T] {
    override type Out = T
    override def apply(x: T): Out = x
  }

  implicit def derivedToTuple[T](implicit derived: DerivedTo[T]): ToTuple[T] = derived.x

  def apply[T](x: T)(implicit tt: ToTuple[T]): tt.Out = tt(x)
}

private[tuples] trait LowPriorityToTuple extends LowestPriorityToTuple {
  import ToTuple._

  implicit def exportedToTuple[T](implicit exported: ExportedTo[T]): ToTuple[T] = exported.x

  def productToTuple[T <: Product]: Aux[T, Product] = new ToTuple[T] {
    override type Out = Product
    override def apply(x: T): Out = ProductToTuple(x)
  }
}

private[tuples] trait LowestPriorityToTuple {

//  implicit def anyToTuple1[T]: Aux[T, Tuple1[T]] = new ToTuple[T] {
//    override type Out = Tuple1[T]
//    override def apply(x: T): Out = Tuple1(x)
//  }
}