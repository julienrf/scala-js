package java.util

import scala.math.Ordering

import scala.collection.{immutable, mutable}
import scala.collection.JavaConverters._

private[util] class NavigableView[E](original: NavigableSet[E],
    inner: () => mutable.SortedSet[Box[E]],
    lowerBound: Option[E], lowerInclusive: Boolean,
    upperBound: Option[E], upperInclusive: Boolean)
    extends AbstractCollection[E] with NavigableSet[E] with SortedSet[E] {

  import NavigableView.Compat._
  import Compat.SortedSetRangeTo

  def size(): Int =
    iterator.asScala.size

  override def contains(o: Any): Boolean =
    inner().contains(Box(o.asInstanceOf[E]))

  override def add(e: E): Boolean = {
    val comp = comparator()
    lowerBound.foreach { bound =>
      val cmp = comp.compare(e, bound)
      if (cmp < 0 || (!lowerInclusive && cmp==0))
        throw new IllegalArgumentException()
    }
    upperBound.foreach { bound =>
      val cmp = comp.compare(e, bound)
      if (cmp > 0 || (!upperInclusive && cmp==0))
        throw new IllegalArgumentException()
    }
    original.add(e)
  }

  override def remove(o: Any): Boolean =
    original.remove(o)

  private def _iterator(iter: scala.collection.Iterator[E]): Iterator[E] = {
    new Iterator[E] {
      private var last: Option[E] = None

      def hasNext(): Boolean = iter.hasNext

      def next(): E = {
        last = Some(iter.next())
        last.get
      }

      def remove(): Unit = {
        if (last.isEmpty) {
          throw new IllegalStateException()
        } else {
          last.foreach(original.remove(_))
          last = None
        }
      }
    }
  }

  def iterator(): Iterator[E] =
    _iterator(inner().iterator.map(_.inner))

  def descendingIterator(): Iterator[E] =
    _iterator(iterator.asScala.toList.reverse.iterator)

  override def removeAll(c: Collection[_]): Boolean = {
    val iter = c.iterator()
    var changed = false
    while (iter.hasNext)
      changed = remove(iter.next) || changed
    changed
  }

  override def addAll(c: Collection[_ <: E]): Boolean =
    original.addAll(c)

  def lower(e: E): E =
    headSet(e, false).asScala.lastOption.getOrElse(null.asInstanceOf[E])

  def floor(e: E): E =
    headSet(e, true).asScala.lastOption.getOrElse(null.asInstanceOf[E])

  def ceiling(e: E): E =
    tailSet(e, true).asScala.headOption.getOrElse(null.asInstanceOf[E])

  def higher(e: E): E =
    tailSet(e, false).asScala.headOption.getOrElse(null.asInstanceOf[E])

  def pollFirst(): E = {
    val polled = inner().headOption
    if (polled.isDefined) {
      val elem = polled.get.inner
      remove(elem)
      elem
    } else null.asInstanceOf[E]
  }

  def pollLast(): E = {
    val polled = inner().lastOption
    if (polled.isDefined) {
      val elem = polled.get.inner
      remove(elem)
      elem
    } else null.asInstanceOf[E]
  }

  def comparator(): Comparator[E] = {
    new Comparator[E] {
      val ordering = inner().ordering

      def compare(a: E, b: E): Int =
        ordering.compare(Box(a), Box(b))
    }
  }

  def first(): E = {
    val iter = iterator()
    if (iter.hasNext) iter.next
    else null.asInstanceOf[E]
  }

  def last(): E = {
    val iter = iterator()
    var result = null.asInstanceOf[E]
    while (iter.hasNext)
      result = iter.next()
    result
  }

  def subSet(fromElement: E, fromInclusive: Boolean, toElement: E,
      toInclusive: Boolean): NavigableSet[E] = {
    val innerNow = inner()
    val boxedFrom = Box(fromElement)
    val boxedTo = Box(toElement)

    val subSetFun = { () =>
      val toTs =
        if (toInclusive) innerNow.rangeTo(boxedTo)
        else innerNow.until(boxedTo)
      if (fromInclusive) toTs.from(boxedFrom)
      else toTs.from(boxedFrom).clone() -= boxedFrom
    }

    new NavigableView(this, subSetFun,
        Some(fromElement), fromInclusive,
        Some(toElement), toInclusive)
  }

  def headSet(toElement: E, inclusive: Boolean): NavigableSet[E] = {
    val innerNow = inner()
    val boxed = Box(toElement)

    val headSetFun =
      if (inclusive) () => innerNow.rangeTo(boxed)
      else () => innerNow.until(boxed)

    new NavigableView(this, headSetFun,
        None, true,
        Some(toElement), inclusive)
  }

  def tailSet(fromElement: E, inclusive: Boolean): NavigableSet[E] = {
    val innerNow = inner()
    val boxed = Box(fromElement)

    val tailSetFun =
      if (inclusive) () => innerNow.from(boxed)
      else () => innerNow.from(boxed).clone() -= boxed

    new NavigableView(this, tailSetFun,
        Some(fromElement), inclusive,
        None, true)
  }

  def subSet(fromElement: E, toElement: E): NavigableSet[E] =
    subSet(fromElement, true, toElement, false)

  def headSet(toElement: E): NavigableSet[E] =
    headSet(toElement, false)

  def tailSet(fromElement: E): NavigableSet[E] =
    tailSet(fromElement, true)

  def descendingSet(): NavigableSet[E] = {
    val descSetFun = { () =>
        val innerNow = inner()
        val retSet = new mutable.TreeSet[Box[E]]()(innerNow.ordering.reverse)
        retSet ++= innerNow
        retSet
    }

    new NavigableView(this, descSetFun, None, true, None, true)
  }
}

object NavigableView {

  private object Compat {

//    implicit class IterableExtensionMethods(val fact: scala.collection.Iterable.type) extends AnyVal {
//      def from[A](source: scala.collection.Iterator[A]): scala.collection.Iterable[A] =
//        fact.apply(source.toSeq: _*)
//    }

//    implicit class ImmutableSortedSetExtensionMethods(val fact: immutable.SortedSet.type) extends AnyVal {
//      def from[A: Ordering](source: scala.collection.Iterable[A]): immutable.SortedSet[A] =
//        fact.apply(source.toSeq: _*)
//    }
//
//    implicit class MutableSortedSetExtensionMethods(val fact: mutable.SortedSet.type) extends AnyVal {
//      def from[A: Ordering](source: scala.collection.Iterable[A]): mutable.SortedSet[A] =
//        fact.apply(source.toSeq: _*)
//    }

  }

}