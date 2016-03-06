package org.igye.xogameclient

import akka.actor.Actor
import org.igye.xogamecommons.WantToPLay
import org.igye.xogamecommons.XOGameCommons._

class ClientActor(serverHost: String, serverPort: Int, name: String, sessionId: String) extends Actor {

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    super.preStart()
    context.actorSelection(
      s"akka.tcp://${SERVER_SYSTEM_NAME}@$serverHost:$serverPort/user/$SERVER_ENTRY_ACTOR_NAME"
    ) ! WantToPLay(name, sessionId)
  }

  def receive = {
    case msg =>
      println(s"ClientActor received message '$msg'")
  }
}
