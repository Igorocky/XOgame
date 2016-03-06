package org.igye.xogameclient

import akka.actor.{Actor, ActorRef}
import akka.event.{DiagnosticLoggingAdapter, Logging}
import org.igye.xogamecommons.XOGameCommons._
import org.igye.xogamecommons._

class ClientActor(serverHost: String, serverPort: Int, sessionId: String, player: XOGamePlayer) extends Actor {
  private val log: DiagnosticLoggingAdapter = Logging(this)
  log.mdc(Map("sessionId" -> sessionId))

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
      log.info(m.toString)
      log.info(s"Game started. I am playing with ${cellType.value}")
      this.sessionActor = sessionActor
      player.gameStarted(msg, cellType)
    case m @ YourTurn(field: XOField) =>
      log.info(s"field:\n${field}")
      val turn = Turn(player.turn(field))
      log.info(s"my answer is ${turn.cell}")
      sessionActor ! turn
    case m @ GameOver(winner: Option[String], msg: String) =>
      log.info(m.toString)
      player.gameOver(winner, msg)
      context.system.terminate()
  }
}
