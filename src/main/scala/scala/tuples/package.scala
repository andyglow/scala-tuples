package scala

import scala.language.implicitConversions

package object tuples {

  implicit def makePrefixSuffixTupleOps[T: IsTuple](tuple: T): StructuralTupleOps[T] = new StructuralTupleOps(tuple)
}
