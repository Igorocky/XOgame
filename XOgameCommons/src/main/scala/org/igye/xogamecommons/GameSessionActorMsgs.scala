package org.igye.xogamecommons

import akka.actor.ActorRef

sealed abstract class GameSessionActorMsgs

case class GameStarted(sessionActor: ActorRef, msg: String)