import sbt._
import sbt.internal.util.ManagedLogger

object Boiler {

  val header = "// auto-generated"

  def gen(dir: File, scalaVersion: String, log: ManagedLogger): Seq[File] = {

//    val isGreaterOrEqualThen213 = CrossVersion.partialVersion(scalaVersion) match {
//      case Some((2, minor)) if minor >= 13 => true
//      case _                               => false
//    }

    def mkConcat: File = {
      val file = dir / "scala" / "tuples" / "ConcatOpInstances.scala"
      log.info("Generating 'ConcatOpInstances.scala'")
      def genConcat(left: Int, right: Int): String = {
        val ll = (1 to left).map { i => s"L$i"} mkString ", "
        val rr = (1 to right).map { i => s"R$i"} mkString ", "
        val lt = if (left == 1) s"Tuple1[$ll]" else s"($ll)"
        val rt = if (right == 1) s"Tuple1[$rr]" else s"($rr)"
        val gl = (1 to left).map { i => s"l._$i"} mkString ", "
        val gr = (1 to right).map { i => s"r._$i"} mkString ", "

        s"""implicit def concat${left}_$right[$ll, $rr]: Aux[$lt, $rt, ($ll, $rr)] = new ConcatOp[$lt, $rt] {
           | type Out = ($ll, $rr)
           | def apply(l: $lt, r: $rt): ($ll, $rr) = ($gl, $gr)
           |}
           |""".stripMargin
      }

      val methods = for {
        i <- 1 to 21
        j <- 1 to (21 - i)
      } yield genConcat(i, j)

      val body =
        s"""package scala.tuples
           |
           |import StructuralTupleOps._
           |
           |private[tuples] abstract class ConcatOpInstances {
           |  import ConcatOp._
           |  ${methods.mkString("\n", "\n", "\n")}
           |}
           |""".stripMargin

      IO.write(file, body)
      file
    }

    def mkReverse: File = {
      val file = dir / "scala" / "tuples" / "ReverseOpInstances.scala"
      log.info("Generating 'ReverseOpInstances.scala'")

      def genInvert(n: Int): String = {
        val ft = (for { i <- 0 until n } yield s"T$i") mkString ", "
        val rt = (for { i <- (n - 1) to 0 by -1 } yield s"T$i") mkString ", "
        val conv = (for { i <- 0 until n } yield s"x._${n - i}") mkString ", "

        s"""implicit def reverse$n[$ft]: Aux[($ft), ($rt)] = new ReverseOp[($ft)] {
           | type Out = ($rt)
           | def apply(x: ($ft)): ($rt) = ($conv)
           |}
           |""".stripMargin
      }

      val methods = for { i <- 2 to 22 } yield genInvert(i)

      val body =
        s"""package scala.tuples
           |
           |import StructuralTupleOps._
           |
           |private[tuples] abstract class ReverseOpInstances {
           |  import ReverseOp._
           |
           |  implicit def reverse0: Aux[Unit, Unit] =
           |    new ReverseOp[Unit] {
           |      type Out = Unit
           |      def apply(tuple: Unit): Unit = tuple
           |    }
           |
           |  implicit def reverse1[T]: Aux[Tuple1[T], Tuple1[T]] =
           |    new ReverseOp[Tuple1[T]] {
           |      type Out = Tuple1[T]
           |      def apply(tuple: Tuple1[T]): Tuple1[T] = tuple
           |    }
           |
           |  ${methods.mkString("\n", "\n", "\n")}
           |}
           |""".stripMargin

      IO.write(file, body)
      file
    }

    def mkRotate(right: Boolean): File = {
      val label = if (right) "Right" else "Left"
      val file = dir / "scala" / "tuples" / s"Rotate${label}OpInstances.scala"

      log.info(s"Generating 'Rotate${label}OpInstances.scala'")

      def genRotate(n: Int): String = {
        def rotate(x: Range): Seq[Int] = if (right) {
          val last = x.last
          last +: x.dropRight(1)
        } else {
          val head = x.head
          x.drop(1) :+ head
        }

        val ft   = (for { i <- 0 until n }         yield s"T$i") mkString ", "
        val rt   = (for { i <- rotate(0 until n) } yield s"T$i") mkString ", "
        val conv = (for { i <- rotate(0 until n) } yield s"x._${i + 1}") mkString ", "

        s"""implicit def rotate${label}$n[$ft]: Aux[($ft), ($rt)] = new Rotate${label}Op[($ft)] {
           | type Out = ($rt)
           | def apply(x: ($ft)): ($rt) = ($conv)
           |}
           |""".stripMargin
      }

      val methods = for { i <- 2 to 22 } yield genRotate(i)

      val body =
        s"""package scala.tuples
           |
           |import StructuralTupleOps._
           |
           |private[tuples] abstract class Rotate${label}OpInstances {
           |  import Rotate${label}Op._
           |
           |  implicit def rotate${label}0: Aux[Unit, Unit] =
           |    new Rotate${label}Op[Unit] {
           |      type Out = Unit
           |      def apply(tuple: Unit): Unit = tuple
           |    }
           |
           |  implicit def rotate${label}1[T]: Aux[Tuple1[T], Tuple1[T]] =
           |    new Rotate${label}Op[Tuple1[T]] {
           |      type Out = Tuple1[T]
           |      def apply(tuple: Tuple1[T]): Tuple1[T] = tuple
           |    }
           |
           |  ${methods.mkString("\n", "\n", "\n")}
           |}
           |""".stripMargin

      IO.write(file, body)
      file
    }

    Seq(mkConcat, mkReverse, mkRotate(true), mkRotate(false))
  }
}
