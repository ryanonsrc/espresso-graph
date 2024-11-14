package io.nary.espresso.graph

import io.nary.espresso.defs._
import io.nary.espresso.compose._
import shapeless.{::, HNil}
import cats.syntax.all._

object Library {
  // Reverse all arrows in a directional graph
  def flip[A, B, E]: Expr[E, DirGraph[A, B] :: HNil, DirGraph[A, B]] = op1[E, DirGraph[A, B], DirGraph[A, B]] {
    case t :: HNil =>
      t.map(g => g.copy(edges = g.edges.foldLeft(Set[Edge[A, B] with HasDirection].empty) {
        case (acc, e : InBoundEdge[A, B]) => acc + outEdge[A, B](e.value, e.in, e.out)
        case (acc, e : OutBoundEdge[A, B]) => acc + inEdge[A, B](e.value, e.in, e.out)
        case (acc, e) => acc + e  // flipping a bidirectional edge has no effect
      }))
  }
}
