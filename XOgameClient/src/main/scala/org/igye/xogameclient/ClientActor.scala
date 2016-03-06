package org.igye.xogameclient

import akka.actor.Actor
import org.apache.logging.log4j.LogManager
import org.igye.xogamecommons.WantToPlay
import org.igye.xogamecommons.XOGameCommons._

class ClientActor(serverHost: String, serverPort: Int, name: String, sessionId: String) extends Actor {
  val log = LogManager.getLogger()

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    super.preStart()
    context.actorSelection(
      s"akka.tcp://${SERVER_SYSTEM_NAME}@$serverHost:$serverPort/user/$SERVER_ENTRY_ACTOR_NAME"
    ) ! WantToPlay(name, sessionId)
  }

  def receive = {
    case msg =>
      log.info(s"ClientActor received message '$msg'")
  }
}
