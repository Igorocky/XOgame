package org.igye.xogamecommons

import org.igye.xogamecommons.Cells._

import scala.collection.JavaConverters._

case class XOField(cells: Vector[Cell]) {
  import XOField._

  assert(cells.size == LENGTH)

  def this() = this(Vector.fill(XOField.LENGTH)(EMPTY))

  def withField(idx: Int, value: Cell): XOField = XOField(cells.updated(idx, value))
  def withField(row: Int, col: Int, value: Cell): XOField = withField(calcIdx(row, col), value)

  def apply(idx: Int): Cell = cells(idx)
  def cellAt(idx: Int): Cell = apply(idx)

  def apply(row: Int, col: Int): Cell = cells(calcIdx(row, col))
  def cellAt(row: Int, col: Int): Cell = apply(row, col)

  def cellsAsList: java.util.List[Cell] = cells.asJava

  override def toString: String =
    s"${cells(0).value}|${cells(1).value}|${cells(2).value}\n" +
      s"-----\n" +
      s"${cells(3).value}|${cells(4).value}|${cells(5).value}\n" +
      s"-----\n" +
      s"${cells(6).value}|${cells(7).value}|${cells(8).value}"

  private def calcIdx(row: Int, col: Int) = row*COLUMN_CNT + col
}

object XOField {
  private final val ROW_CNT = 3
  private final val COLUMN_CNT = 3
  private final val LENGTH = ROW_CNT * COLUMN_CNT

  def apply() = new XOField()
}