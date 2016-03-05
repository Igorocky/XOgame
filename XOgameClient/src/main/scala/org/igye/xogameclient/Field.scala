package org.igye.xogameclient

import org.igye.commonutils.Enum

case class Field(value: String)

object Fields extends Enum[Field] {
  val EMPTY = addElem(Field(" "))
  val X = addElem(Field("X"))
  val O = addElem(Field("O"))
}
