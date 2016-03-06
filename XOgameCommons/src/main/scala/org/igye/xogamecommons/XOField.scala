package org.igye.xogamecommons

import org.igye.xogamecommons.Cells._

case class XOField(state: Vector[Cell]) {
  import XOField._

  assert(state.size == LENGTH)

  def this() = this(Vector.fill(XOField.LENGTH)(EMPTY))

  def withField(idx: Int, value: Cell): XOField = XOField(state.updated(idx, value))
  def withField(row: Int, col: Int, value: Cell): XOField = withField(calcIdx(row, col), value)

  def apply(idx: Int): Cell = state(idx)
  def apply(row: Int, col: Int): Cell = state(calcIdx(row, col))

  override def toString: String =
    s"${state(0).value}|${state(1).value}|${state(2).value}\n" +
      s"-----\n" +
      s"${state(3).value}|${state(4).value}|${state(5).value}\n" +
      s"-----\n" +
      s"${state(6).value}|${state(7).value}|${state(8).value}"

  private def calcIdx(row: Int, col: Int) = row*COLUMN_CNT + col
}

object XOField {
  private final val ROW_CNT = 3
  private final val COLUMN_CNT = 3
  private final val LENGTH = ROW_CNT * COLUMN_CNT

  def apply() = new XOField()
}