package org.igye.xogameserver

import java.util.Random

import akka.actor.{Actor, ActorRef, Props}
import akka.event.{DiagnosticLoggingAdapter, Logging}
import org.igye.xogamecommons._

class GameSessionActor(players: List[Player]) extends Actor {
  assert(players.size == 2)

  private val log: DiagnosticLoggingAdapter = Logging(this)
  log.mdc(Map("sessionId" -> players(0).sessionId))

  private var field = XOField()
  private var waitNextTurnFrom: Player = players.find(_.cellType == Cells.X).get


  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    super.preStart()
    players(0) ! GameStarted(self, s"New game started. Your cell type is ${players(0).cellType.value}", players(0).cellType)
    players(1) ! GameStarted(self, s"New game started. Your cell type is ${players(1).cellType.value}", players(1).cellType)
    waitNextTurnFrom ! YourTurn(field)
    log.info(s"Game started. Player1 - ${players(0)}. Player2 - ${players(1)}")
  }

  override def receive: Receive = {
    case Turn(cellNumber) =>
      if (waitNextTurnFrom.ref != sender) {
        gameOver(
          Some(waitNextTurnFrom.name),
          s"$waitNextTurnFrom was expected to make turn but ${getPlayer(sender)} made turn.",
          field
        )
      } else {
        if (field(cellNumber) != Cells.EMPTY) {
          gameOver(
            Some(getOpponent(waitNextTurnFrom).name),
            s"$waitNextTurnFrom tried to occupy nonempty field $cellNumber",
            field
          )
        } else {
          log.info(s"${waitNextTurnFrom.name} answered - $cellNumber")
          field = field.withField(cellNumber, waitNextTurnFrom.cellType)
          log.info(s"\n$field")
          val winner: Option[Player] = findWinner(field).flatMap(winnerCell => players.find(_.cellType == winnerCell))
          if (winner.isDefined) {
            winner.foreach(w => gameOver(Some(w.name), s"winner is ${w.name}", field))
          } else if (field.cells.exists(_ == Cells.EMPTY)) {
            waitNextTurnFrom = getOpponent(waitNextTurnFrom)
            waitNextTurnFrom ! YourTurn(field)
          } else {
            gameOver(
              None,
              s"No more empty fields.",
              field
            )
          }
        }
      }
  }

  private def findWinner(field: XOField): Option[Cell] = {
    var res = getCellIfAllAreSame(field(0, 0), field(0, 1), field(0, 2))
    if (res.isDefined) {
      res
    } else {
      res = getCellIfAllAreSame(field(1, 0), field(1, 1), field(1, 2))
      if (res.isDefined) {
        res
      } else {
        res = getCellIfAllAreSame(field(2, 0), field(2, 1), field(2, 2))
        if (res.isDefined) {
          res
        } else {
          res = getCellIfAllAreSame(field(0, 0), field(1, 0), field(2, 0))
          if (res.isDefined) {
            res
          } else {
            res = getCellIfAllAreSame(field(0, 1), field(1, 1), field(2, 1))
            if (res.isDefined) {
              res
            } else {
              res = getCellIfAllAreSame(field(0, 2), field(1, 2), field(2, 2))
              if (res.isDefined) {
                res
              } else {
                res = getCellIfAllAreSame(field(0, 0), field(1, 1), field(2, 2))
                if (res.isDefined) {
                  res
                } else {
                  res = getCellIfAllAreSame(field(0, 2), field(1, 1), field(2, 0))
                  if (res.isDefined) {
                    res
                  } else {
                    None
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  private def getCellIfAllAreSame(cell1: Cell, cell2: Cell, cell3: Cell) = {
    if (cell1 == cell2 && cell2 == cell3) {
      Some(cell1)
    } else {
      None
    }
  }

  private def getPlayer(ref: ActorRef): Player = {
    players.find(_.ref == ref).get
  }

  private def getOpponent(pl: Player): Player = {
    players.find(_ != pl).get
  }

  private def gameOver(winner: Option[String], msg: String, field: XOField): Unit = {
    val gameOver = GameOver(winner, msg, field)
    players(0) ! gameOver
    players(1) ! gameOver
    log.info(s"GameOver:${gameOver.msg}\n${gameOver.field}")
    context.parent ! gameOver
  }
}

object GameSessionActor {
  def props(players: List[Player]): Props = Props(new GameSessionActor(players))
}