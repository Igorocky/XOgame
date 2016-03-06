package org.igye.xogameserver

import akka.actor.ActorRef

case class Player(name: String, sessionId: String, ref: ActorRef) {
  def !(msg: Any) = ref ! msg
}