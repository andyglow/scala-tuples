package scala.tuples.macros

import scala.reflect.macros.blackbox
import scala.tuples._


class FromTupleMacro(val c: blackbox.Context) extends Extractors with HasContext with HasLog {
  import c.universe._

  def exported[TT, T](implicit tt: c.WeakTypeTag[TT], t: c.WeakTypeTag[T]): c.Expr[ExportedFrom[TT, T]] = {
    val tpe = t.tpe
    val ttpe = tt.tpe
    val tree = impl[TT, T].tree
    c.Expr[ExportedFrom[TT, T]](q"ExportedFrom[$ttpe, $tpe]($tree)")
  }

  def derived[TT, T](implicit tt: c.WeakTypeTag[TT], t: c.WeakTypeTag[T]): c.Expr[DerivedFrom[TT, T]] = {
    val tpe = t.tpe
    val ttpe = tt.tpe
    val tree = impl[TT, T].tree
    c.Expr[DerivedFrom[TT, T]](q"DerivedFrom[$ttpe, $tpe]($tree)")
  }

  def impl[TT, T](implicit t: c.WeakTypeTag[T]): c.Expr[FromTuple.Aux[TT, T]] = {
    val tpe = t.tpe

    val tree = tpe match {
      case tpe if isTuple(tpe) => q"FromTuple.tupleFromTuple[$tpe]"
      case CaseClass(cc) =>
        val ccTypeName     = tpe.typeSymbol.name.toTypeName
        if (cc.fields.nonEmpty) {
          val fields         = cc.fields.toList
          val tupleTpeSym    = c.mirror.staticClass(s"scala.Tuple${cc.fields.length}")
          val tupleTpeParams = fields map { _.effectiveTpe }
          val tupleTpe       = appliedType(tupleTpeSym, tupleTpeParams)
          q"""
            new FromTuple[$tpe] {
              override type In = $tupleTpe
              override def apply(x: In): $tpe = new $ccTypeName(..${fields.zipWithIndex map { case (_, i) => q"x.${TermName(s"_${i + 1}")}" } })
            }
          """
        } else
          q"""
            new FromTuple[$tpe] {
              override type In = Unit
              override def apply(x: In): $tpe = new $ccTypeName()
            }
          """
      case _ => q"FromTuple.fromTuple1ToAny[$tpe]"
    }

    if(c.settings.contains("print-from-tuple-code"))
      info(showCode(tree))

    c.Expr[FromTuple.Aux[TT, T]](tree)
  }
}