package scala.tuples

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._


class FromTupleSpec extends AnyWordSpec {
  import FromTupleSpec._

  "FromTuple" should {

    "convert tuples" in {
      FromTuple((1,2)).to[(Int, Int)] shouldBe Tuple2(1, 2)
      FromTuple(("a", "b", "c")).to[(String, String, String)] shouldBe Tuple3("a", "b", "c")
    }

    "convert case classes. auto derivation" in {
      import scala.tuples.generic.auto._

      FromTuple((5, "foo")).to[CC1] shouldBe CC1(5, "foo")
    }

    "convert case classes. semiauto derivation" in {
      FromTuple((5, "foo")).to[CC2] shouldBe CC2(5, "foo")
    }
  }
}

object FromTupleSpec {

  case class CC1(i: Int, s: String)

  case class CC2(i: Int, s: String)

  final object CC2 {
    import scala.tuples.generic.semiauto._

    implicit val ccTT: DerivedFrom[(Int, String), CC2] = deriveFromTuple[(Int, String), CC2]
  }
}

