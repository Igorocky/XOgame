package org.igye.xogamecommons

import akka.actor.ActorRef

sealed abstract class ClientActorMsgs

case class MatchStarted(msg: String) extends ClientActorMsgs
case class GameStarted(sessionActor: ActorRef, msg: String, cellType: Cell) extends ClientActorMsgs
case class YourTurn(field: XOField) extends ClientActorMsgs
case class GameOver(winner: Option[String], msg: String, field: XOField) extends ClientActorMsgs
case class MatchOver(gamesPlayed: Int, scores: Map[String, Int], winner: Option[String]) extends ClientActorMsgs