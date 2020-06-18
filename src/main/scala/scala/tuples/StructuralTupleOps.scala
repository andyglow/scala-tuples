package scala.tuples


class StructuralTupleOps[T] private[tuples] (val tuple: T) extends AnyVal {
  import StructuralTupleOps._

  def append[S](suffix: S)(implicit op: AppendOp[T, S]): op.Out = op(tuple, suffix)
  def :+[S](suffix: S)(implicit op: AppendOp[T, S]): op.Out = append(suffix)(op)

  def prepend[P](prefix: P)(implicit op: PrependOp[P, T]): op.Out = op(prefix, tuple)
  def +:[P](prefix: P)(implicit op: PrependOp[P, T]): op.Out = prepend(prefix)(op)

  def concat[P](suffix: P)(implicit op: ConcatOp[T, P]): op.Out = op(tuple, suffix)
  def :++[P](suffix: P)(implicit op: ConcatOp[T, P]): op.Out = op(tuple, suffix)
  def ++:[P](prefix: P)(implicit op: ConcatOp[P, T]): op.Out = op(prefix, tuple)

  def reversed(implicit op: ReverseOp[T]): op.Out = op(tuple)

  def rotatedRight(implicit op: RotateRightOp[T]): op.Out = op(tuple)

  def rotatedLeft(implicit op: RotateLeftOp[T]): op.Out = op(tuple)
}

object StructuralTupleOps {

  trait AppendOp[T, S] {
    type Out

    def apply(prefix: T, suffix: S): Out
  }

  object AppendOp extends AppendOpInstances {
    type Aux[T, S, Out0] = AppendOp[T, S] { type Out = Out0 }
  }

  trait PrependOp[P, T] {
    type Out

    def apply(prefix: P, suffix: T): Out
  }

  object PrependOp extends PrependOpInstances {
    type Aux[P, T, Out0] = PrependOp[P, T] { type Out = Out0 }
  }

  trait ConcatOp[L, R] {
    type Out

    def apply(prefix: L, suffix: R): Out
  }

  object ConcatOp extends ConcatOpInstances {
    type Aux[L, R, Out0] = ConcatOp[L , R] { type Out = Out0 }
  }

  trait ReverseOp[T] {
    type Out
    def apply(tuple: T): Out
  }

  object ReverseOp extends ReverseOpInstances {
    type Aux[T, Out0] = ReverseOp[T] { type Out = Out0 }
  }

  trait RotateRightOp[T] {
    type Out
    def apply(tuple: T): Out
  }

  object RotateRightOp extends RotateRightOpInstances {
    type Aux[T, Out0] = RotateRightOp[T] { type Out = Out0 }
  }

  trait RotateLeftOp[T] {
    type Out
    def apply(tuple: T): Out
  }

  object RotateLeftOp extends RotateLeftOpInstances {
    type Aux[T, Out0] = RotateLeftOp[T] { type Out = Out0 }
  }
}