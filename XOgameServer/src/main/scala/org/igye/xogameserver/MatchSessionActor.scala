package org.igye.xogameserver

import java.util.Random

import akka.actor.{Actor, Props}
import akka.event.{DiagnosticLoggingAdapter, Logging}
import org.igye.xogamecommons._
import org.igye.xogameserver.Constants.NUMBER_OF_GAMES

class MatchSessionActor(pls: List[Player], numberOfGames: Int) extends Actor {
  assert(pls.size == 2)

  private val log: DiagnosticLoggingAdapter = Logging(this)
  log.mdc(Map("sessionId" -> pls(0).sessionId))

  private var players: List[Player] = pls.foldLeft(List[Player]()){
    case (Nil, pl) => List(pl.copy(cellType = if(new Random().nextInt(2) == 1) Cells.X else Cells.O))
    case (list @ List(pl1), pl2) => pl2.copy(cellType = if (pl1.cellType == Cells.X) Cells.O else Cells.X)::list
  }

  private var gamesPlayed = 0
  private var scores = Map(
    players(0).name -> 0,
    players(1).name -> 0
  )

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    super.preStart()
    players(0) ! MatchStarted(s"You will play with ${players(1).name}.")
    players(1) ! MatchStarted(s"You will play with ${players(0).name}.")
    log.info(s"Match started. Player1 - ${players(0)}. Player2 - ${players(1)}")
    self ! StartMatch()
  }

  override def receive: Receive = {
    case StartMatch() =>
      startNewGame()
    case GameOver(winner, _, field) =>
      context.stop(sender)
      gamesPlayed += 1
      if (winner.isDefined) {
        scores = scores.updated(winner.get, scores(winner.get) + 1)
      }
      if (gamesPlayed == numberOfGames) {
        matchOver()
      } else {
        startNewGame()
      }
  }

  private def matchOver(): Unit = {
    val matchOver = MatchOver(gamesPlayed, scores, defineWinner)
    players(0) ! matchOver
    players(1) ! matchOver
    log.info(s"MatchOver: $matchOver()")
    if (XOConfig.getBoll(Constants.SHUTDOWN_ON_MATCHOVER)) {
      context.system.terminate()
    } else {
      context.stop(self)
    }
  }

  private def defineWinner: Option[String] = {
    if (scores(players(0).name) > scores(players(1).name)) {
      Some(players(0).name)
    } else if (scores(players(0).name) < scores(players(1).name)) {
      Some(players(1).name)
    } else {
      None
    }
  }

  private def startNewGame(): Unit = {
    swapPlayersCells()
    context.actorOf(
      GameSessionActor.props(players),
      s"${classOf[GameSessionActor].getSimpleName}:${players(0).sessionId}:${System.currentTimeMillis()}"
    )
  }

  private def swapPlayersCells(): Unit = {
    players = List(
      players(0).copy(cellType = players(1).cellType),
      players(1).copy(cellType = players(0).cellType)
    )
  }
}

object MatchSessionActor {
  def props(players: List[Player]): Props = Props(new MatchSessionActor(players, XOConfig.getInt(NUMBER_OF_GAMES)))
}