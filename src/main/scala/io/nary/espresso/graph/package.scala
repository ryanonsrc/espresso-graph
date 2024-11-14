package io.nary.espresso

package object graph {
  trait HasDirection
  trait HasNoDirection
  trait InBound extends HasDirection
  trait OutBound extends HasDirection

  case class Vertex[A](value: A)

  sealed trait Edge[A, B] { val value: B; val in: Vertex[A]; val out: Vertex[A] }
  case class InBoundEdge[A, B](value: B, in: Vertex[A], out: Vertex[A]) extends Edge[A, B] with HasDirection with InBound
  case class OutBoundEdge[A, B](value: B, in: Vertex[A], out: Vertex[A]) extends Edge[A, B] with HasDirection with OutBound
  case class BiDirectionalEdge[A, B](value: B, in: Vertex[A], out: Vertex[A]) extends Edge[A, B] with HasDirection with InBound with OutBound
  case class UnDirectedEdge[A, B](value: B, in: Vertex[A], out: Vertex[A]) extends Edge[A, B] with HasNoDirection

  def vertex[A](value: A): Vertex[A] = Vertex(value)
  def undEdge[A, B](value: B, in: Vertex[A], out: Vertex[A]): UnDirectedEdge[A, B] = UnDirectedEdge(value, in, out)
  def bidEdge[A, B](value: B, in: Vertex[A], out: Vertex[A]): BiDirectionalEdge[A, B] = BiDirectionalEdge(value, in, out)
  def inEdge[A, B](value: B, in: Vertex[A], out: Vertex[A]): InBoundEdge[A, B] = InBoundEdge(value, in, out)
  def outEdge[A, B](value: B, in: Vertex[A], out: Vertex[A]): OutBoundEdge[A, B] = OutBoundEdge(value, in, out)

  sealed trait Graph[A, B, E <: Edge[A, B]] { val vertices: Vertex[A]; val edges: Set[E] }
  case class DirGraph[A, B](vertices: Vertex[A], edges: Set[Edge[A, B] with HasDirection]) extends Graph[A, B, Edge[A, B] with HasDirection]
  case class UnDirGraph[A, B](vertices: Vertex[A], edges: Set[Edge[A, B] with HasNoDirection]) extends Graph[A, B, Edge[A, B] with HasNoDirection]

  def diGraph[A, B](vertices: Vertex[A], edges: Set[Edge[A, B] with HasDirection]) = DirGraph[A, B](vertices, edges)
  def graph[A, B](vertices: Vertex[A], edges: Set[Edge[A, B] with HasNoDirection]) = UnDirGraph[A, B](vertices, edges)
}