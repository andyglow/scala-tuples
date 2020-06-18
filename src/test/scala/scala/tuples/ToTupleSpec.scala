package scala.tuples

import org.scalatest.wordspec._
import org.scalatest.matchers.should.Matchers._


class ToTupleSpec extends AnyWordSpec {
  import ToTupleSpec._

  "ToTuple" should {

    "convert tuples" in {
      ToTuple((1,2)) shouldBe Tuple2(1, 2)
      ToTuple(("a", "b", "c")) shouldBe Tuple3("a", "b", "c")
    }

    "convert case classes. auto derivation" in {
      import scala.tuples.generic.auto._

      ToTuple(CC1(5, "foo")) shouldBe Tuple2(5, "foo")
    }

    "convert case classes. semiauto derivation" in {
      ToTuple(CC2(5, "foo")) shouldBe Tuple2(5, "foo")
    }
  }
}

object ToTupleSpec {

  case class CC1(i: Int, s: String)

  case class CC2(i: Int, s: String)

  final object CC2 {
    import scala.tuples.generic.semiauto._
    implicit val ccTT: ToTuple[CC2] = deriveToTuple[CC2].x
  }
}