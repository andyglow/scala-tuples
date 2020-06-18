package scala.tuples.macros

import scala.reflect.macros.blackbox
import scala.tuples._
import scala.tuples.ToTuple._


class ToTupleMacro(val c: blackbox.Context) extends Extractors with HasContext with HasLog {
  import c.universe._

  def exported[T](implicit t: c.WeakTypeTag[T]): c.Expr[ExportedTo[T]] = {
    val tpe = t.tpe
    val tree = impl[T].tree
    c.Expr[ExportedTo[T]](q"ExportedTo[$tpe]($tree)")
  }

  def derived[T](implicit t: c.WeakTypeTag[T]): c.Expr[DerivedTo[T]] = {
    val tpe = t.tpe
    val tree = impl[T].tree
    c.Expr[DerivedTo[T]](q"DerivedTo[$tpe]($tree)")
  }

  def impl[T](implicit t: c.WeakTypeTag[T]): c.Expr[ToTuple[T]] = {
    val ProductTpe = weakTypeOf[Product]

    val tpe = t.tpe

    val tree = tpe match {
      case tpe if isTuple(tpe) => q"ToTuple.tupleToTuple[$tpe]"
      case CaseClass(cc) =>
        if (cc.fields.nonEmpty) {
          val fields         = cc.fields.toList
          val tupleTpeSym    = c.mirror.staticClass(s"scala.Tuple${cc.fields.length}")
          val tupleTpeName   = tupleTpeSym.name.toTypeName
          val tupleTpeParams = fields map { _.effectiveTpe }
          val tupleTpe       = appliedType(tupleTpeSym, tupleTpeParams)

          q"""
            new ToTuple[$tpe] {
              override type Out = $tupleTpe
              override def apply(x: $tpe): Out = new $tupleTpeName(..${fields map { f => q"x.${f.name}" }})
            }
          """
        } else
          q"""
            new ToTuple[$tpe] {
              override type Out = Unit
              override def apply(x: $tpe): Out = ()
            }
          """
      case tpe if tpe <:< ProductTpe => q"ToTuple.productToTuple[$tpe]"
      case _ => q"ToTuple.anyToTuple1[$tpe]"
    }

    if(c.settings.contains("print-to-tuple-code"))
      info(showCode(tree))

    c.Expr[ToTuple[T]](tree)
  }
}