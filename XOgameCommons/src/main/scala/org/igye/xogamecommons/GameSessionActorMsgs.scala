package org.igye.xogamecommons

sealed abstract class GameSessionActorMsgs

case class Turn(cell: Int) extends GameSessionActorMsgs