package org.igye.xogameclient

import akka.actor.{ActorRef, Actor}
import org.apache.logging.log4j.LogManager
import org.igye.xogamecommons._
import org.igye.xogamecommons.XOGameCommons._

class ClientActor(serverHost: String, serverPort: Int, sessionId: String, player: XOGamePlayer) extends Actor {
  private val log = LogManager.getLogger()

  private var sessionActor: ActorRef = _

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    super.preStart()
    context.actorSelection(
      s"akka.tcp://${SERVER_SYSTEM_NAME}@$serverHost:$serverPort/user/$SERVER_ENTRY_ACTOR_NAME"
    ) ! WantToPlay(player.getName, sessionId)
  }

  def receive = {
    case m @ GameStarted(sessionActor, msg, cellType) =>
      log.info(m)
      this.sessionActor = sessionActor
      player.gameStarted(msg, cellType)
    case m @ YourTurn(field: XOField) =>
      log.info(m)
      sessionActor ! Turn(player.turn(field))
    case m @ GameOver(winner: Option[String], msg: String) =>
      log.info(m)
      player.gameOver(winner, msg)
      context.system.terminate()
  }
}
