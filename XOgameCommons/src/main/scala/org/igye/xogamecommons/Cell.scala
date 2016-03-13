package org.igye.xogamecommons

import org.igye.commonutils.Enum

case class Cell(value: String) {
  def opponent = this match {
    case Cells.X => Cells.O
    case Cells.O => Cells.X
    case _ => ???
  }
}

object Cells extends Enum[Cell] {
  val EMPTY = addElem(Cell(" "))
  val X = addElem(Cell("X"))
  val O = addElem(Cell("O"))
}
