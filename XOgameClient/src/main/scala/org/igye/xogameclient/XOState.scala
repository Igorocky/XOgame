package org.igye.xogameclient

import org.igye.xogameclient.Fields._

case class XOState(state: Vector[Field]) {
  import XOState._

  assert(state.size == LENGTH)

  def this() = this(Vector.fill(XOState.LENGTH)(EMPTY))

  def withField(idx: Int, value: Field): XOState = XOState(state.updated(idx, value))
  def withField(row: Int, col: Int, value: Field): XOState = withField(calcIdx(row, col), value)

  def apply(idx: Int) = state(idx)
  def apply(row: Int, col: Int) = state(calcIdx(row, col))

  override def toString: String =
    s"${state(0).value}|${state(1).value}|${state(2).value}\n" +
      s"-----\n" +
      s"${state(3).value}|${state(4).value}|${state(5).value}\n" +
      s"-----\n" +
      s"${state(6).value}|${state(7).value}|${state(8).value}"

  private def calcIdx(row: Int, col: Int) = row*COLUMN_CNT + col
}

object XOState {
  private final val ROW_CNT = 3
  private final val COLUMN_CNT = 3
  private final val LENGTH = ROW_CNT * COLUMN_CNT

  def apply() = new XOState()
}