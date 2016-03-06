package org.igye.xogamecommons

import akka.actor.ActorRef

sealed abstract class GameSessionActorMsgs

case class GameStarted(sessionActor: ActorRef, msg: String, cellType: Cell)
case class YourTurn(field: XOField)
case class Turn(cell: Int)
case class GameOver(winner: Option[String], msg: String)