# Scala Tuple Extensions

Typesafe operations on tuples.

[![Build Status](https://cloud.drone.io/api/badges/andyglow/scala-tuples/status.svg)](https://cloud.drone.io/andyglow/scala-tuples)
[![codecov](https://codecov.io/gh/andyglow/scala-tuples/branch/master/graph/badge.svg)](https://codecov.io/gh/andyglow/scala-tuples)

- [x] prepend value `"abc" +: (1, false) == ("abc", 1, false)`
- [x] append value `(1, false) :+ "abc" == (1, false, "abc")`
- [x] concatenate `(1, false) :++ ("abc", 3.14) == (1, false, "abc", 3.14); (1, false) ++: ("abc", 3.14) == (1, false, "abc", 3.14)`
- [x] reverse `(1, 2, 3).reversed == (3, 2, 1)`
- [x] rotateRight `(1, 2, 3).rotatedRight == (3, 1, 2)`
- [x] rotateLeft `(1, 2, 3).rotatedLeft == (2, 3, 1)`
- [x] case class ToTuple 
      ```
      case class CC(a: Int, b: String, c: Float)
      ToTuple(CC(1, "b", 3.14)) == (1, "b", 3.14)
      ```
      So this is designed as less type-confusing analog of `CC.unapply(CC(1, "b", 3.14)).get`
- [x] case class FromTuple 
      ```
      case class CC(a: Int, b: String, c: Float)
      FromTuple((1, "b", 3.14)).to[CC] == CC(1, "b", 3.14)
      ```