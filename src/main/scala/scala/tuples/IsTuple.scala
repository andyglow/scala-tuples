package scala.tuples


sealed trait IsTuple[T]

object IsTuple {
  def forAnything[T]: IsTuple[T] = null

  implicit def forNothing[A]: IsTuple[Nothing] = null
  implicit def forUnit[A]: IsTuple[Unit] = null
  implicit def forTuple1[A]: IsTuple[Tuple1[A]] = null
  implicit def forTuple2[A, B]: IsTuple[(A, B)] = null
  implicit def forTuple3[A, B, C]: IsTuple[(A, B, C)] = null
  implicit def forTuple4[A, B, C, D]: IsTuple[(A, B, C, D)] = null
  implicit def forTuple5[A, B, C, D, E]: IsTuple[(A, B, C, D, E)] = null
  implicit def forTuple6[A, B, C, D, E, F]: IsTuple[(A, B, C, D, E, F)] = null
  implicit def forTuple7[A, B, C, D, E, F, G]: IsTuple[(A, B, C, D, E, F, G)] = null
  implicit def forTuple8[A, B, C, D, E, F, G, H]: IsTuple[(A, B, C, D, E, F, G, H)] = null
  implicit def forTuple9[A, B, C, D, E, F, G, H, I]: IsTuple[(A, B, C, D, E, F, G, H, I)] = null
  implicit def forTuple10[A, B, C, D, E, F, G, H, I, J]: IsTuple[(A, B, C, D, E, F, G, H, I, J)] = null
  implicit def forTuple11[A, B, C, D, E, F, G, H, I, J, K]: IsTuple[(A, B, C, D, E, F, G, H, I, J, K)] = null
  implicit def forTuple12[A, B, C, D, E, F, G, H, I, J, K, L]: IsTuple[(A, B, C, D, E, F, G, H, I, J, K, L)] = null
  implicit def forTuple13[A, B, C, D, E, F, G, H, I, J, K, L, M]: IsTuple[(A, B, C, D, E, F, G, H, I, J, K, L, M)] = null
  implicit def forTuple14[A, B, C, D, E, F, G, H, I, J, K, L, M, N]: IsTuple[(A, B, C, D, E, F, G, H, I, J, K, L, M, N)] = null
  implicit def forTuple15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O]: IsTuple[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)] = null
  implicit def forTuple16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P]: IsTuple[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)] = null
  implicit def forTuple17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q]: IsTuple[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)] = null
  implicit def forTuple18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R]: IsTuple[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)] = null
  implicit def forTuple19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S]: IsTuple[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)] = null
  implicit def forTuple20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T]: IsTuple[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)] = null
  implicit def forTuple21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U]: IsTuple[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)] = null
  implicit def forTuple22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V]: IsTuple[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)] = null
}