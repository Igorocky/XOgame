package org.igye.xogameclient

import org.igye.commonutils.Enum

case class Cell(value: String)

object Cells extends Enum[Cell] {
  val EMPTY = addElem(Cell(" "))
  val X = addElem(Cell("X"))
  val O = addElem(Cell("O"))
}
