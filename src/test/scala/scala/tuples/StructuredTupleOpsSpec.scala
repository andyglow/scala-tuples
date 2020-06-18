package scala.tuples

import org.scalatest.matchers.should.Matchers._
import org.scalatest.funsuite._


class StructuredTupleOpsSpec extends AnyFunSuite {

  test("append") {
    (()).append(100)              shouldBe Tuple1(100)
    Tuple1(1).append(100)         shouldBe Tuple2(1, 100)
    (1, 2).append(100)            shouldBe ((1, 2, 100))
    (1, 2, 3).append(100)         shouldBe ((1, 2, 3, 100))
    (1, 2, 3, 4).append(100)      shouldBe ((1, 2, 3, 4, 100))
    (1, 2, 3, 4, 5).append(100)   shouldBe ((1, 2, 3, 4, 5, 100))

    ("a", true, 'c', 22.22) :+ 7  shouldBe (("a", true, 'c', 22.22, 7))
  }

  test("prepend") {
    (()).prepend(100)             shouldBe Tuple1(100)
    Tuple1(1).prepend(100)        shouldBe Tuple2(100, 1)
    (1, 2).prepend(100)           shouldBe ((100, 1, 2))
    (1, 2, 3).prepend(100)        shouldBe ((100, 1, 2, 3))
    (1, 2, 3, 4).prepend(100)     shouldBe ((100, 1, 2, 3, 4))
    (1, 2, 3, 4, 5).prepend(100)  shouldBe ((100, 1, 2, 3, 4, 5))

    7 +: ("a", true, 'c', 22.22)  shouldBe ((7, "a", true, 'c', 22.22))
  }

  test("concat") {
    Tuple1(0) concat Tuple1(1)    shouldBe ((0, 1))
    (0, 1) concat Tuple2(2, 3)    shouldBe ((0, 1, 2, 3))
    (0, 1) ++: Tuple3(2, 3, 4)    shouldBe ((0, 1, 2, 3, 4))
    (0, 1, 2) :++ Tuple2(3, 4)    shouldBe ((0, 1, 2, 3, 4))
  }

  test("inverted") {
    ().reversed                   shouldBe (())
    Tuple1(0).reversed            shouldBe (Tuple1(0))
    (0, 1).reversed               shouldBe ((1, 0))
    (0, 1, 2).reversed            shouldBe ((2, 1, 0))
    (0, 1, 2, 3).reversed         shouldBe ((3, 2, 1, 0))

    ("a", true, 'c', 22.22).reversed shouldBe ((22.22, 'c', true, "a"))
  }

  test("rotatedRight") {
    ().rotatedRight             shouldBe (())
    Tuple1(0).rotatedRight      shouldBe (Tuple1(0))
    (0, 1).rotatedRight         shouldBe ((1, 0))
    (0, 1, 2).rotatedRight      shouldBe ((2, 0, 1))
    (0, 1, 2, 3).rotatedRight   shouldBe ((3, 0, 1, 2))

    ("a", true, 'c', 22.22).rotatedRight shouldBe ((22.22, "a", true, 'c'))
  }

  test("rotatedLeft") {
    ().rotatedLeft             shouldBe (())
    Tuple1(0).rotatedLeft      shouldBe (Tuple1(0))
    (0, 1).rotatedLeft         shouldBe ((1, 0))
    (0, 1, 2).rotatedLeft      shouldBe ((1, 2, 0))
    (0, 1, 2, 3).rotatedLeft   shouldBe ((1, 2, 3, 0))

    ("a", true, 'c', 22.22).rotatedLeft shouldBe ((true, 'c', 22.22, "a"))
  }
}
