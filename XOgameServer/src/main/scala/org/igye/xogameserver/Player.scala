package org.igye.xogameserver

import akka.actor.ActorRef
import org.igye.xogamecommons.Cell

case class Player(name: String, sessionId: String, ref: ActorRef, cellType: Cell = null) {
  def !(msg: Any) = ref ! msg
}