package io.nary.espresso.graph

import cats.syntax.all._
import cats.data.Kleisli
import io.nary.espresso.adapters.readers
import io.nary.espresso.{compose, defs}

object Syntax {
  import defs._
  import compose._
  import readers._
  import Library._


  case class EvalError(msg: String)

  implicit class DirGraphExpr[A, B](val l: Expr[EvalError, Unit, DirGraph[A, B]]) extends AnyVal {
    def unary_- : Expr[EvalError, Nothing, DirGraph[A, B]] = eval1(l, flip[A, B, EvalError])
  }

  implicit def const[A, B, E <: Edge[A, B], G <: Graph[A, B, E]](g: G) : Expr[EvalError, Nothing, G] =
    Kleisli[λ[α => Term[EvalError, α]], Nothing, G] { _ => g.validNel }

}
