package org.igye.xogameserver

import java.util.Random

import akka.actor.{ActorRef, Actor, Props}
import org.apache.logging.log4j.LogManager
import org.igye.xogamecommons._

class GameSessionActor(pls: List[Player]) extends Actor {
  assert(pls.size == 2)

  private val log = LogManager.getLogger()

  private val players: List[Player] = pls.foldLeft(List[Player]()){
    case (Nil, pl) => List(pl.copy(cellType = if(new Random().nextInt(2) == 1) Cells.X else Cells.O))
    case (list @ List(pl1), pl2) => pl2.copy(cellType = if (pl1.cellType == Cells.X) Cells.O else Cells.X)::list
  }
  private var field = XOField()
  private var waitNextTurnFrom: Player = players.find(_.cellType == Cells.X).get


  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    super.preStart()
    players(0) ! GameStarted(self, s"You will play with ${players(1).name}.", players(0).cellType)
    players(1) ! GameStarted(self, s"You will play with ${players(0).name}.", players(1).cellType)
    waitNextTurnFrom ! YourTurn(field)
  }

  override def receive: Receive = {
    case Turn(cellNumber) =>
      if (waitNextTurnFrom.ref != sender) {
        gameOver(
          Some(waitNextTurnFrom.name),
          s"$waitNextTurnFrom was expected to make turn but ${getPlayer(sender)} made turn."
        )
      } else {
        if (field(cellNumber) != Cells.EMPTY) {
          gameOver(
            Some(getOpponent(waitNextTurnFrom).name),
            s"$waitNextTurnFrom tried to occupy nonempty field $cellNumber"
          )
        } else {
          field = field.withField(cellNumber, waitNextTurnFrom.cellType)
          val winner: Option[Player] = findWinner(field).flatMap(winnerCell => players.find(_.cellType == winnerCell))
          if (winner.isDefined) {
            winner.foreach(w => gameOver(Some(w.name), s"winner is ${w.name}"))
          } else {
            waitNextTurnFrom = getOpponent(waitNextTurnFrom)
            waitNextTurnFrom ! YourTurn(field)
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

  private def gameOver(winner: Option[String], msg: String): Unit = {
    val gameOver = GameOver(winner, msg)
    players(0) ! gameOver
    players(1) ! gameOver
    context.stop(self)
  }
}

object GameSessionActor {
  def props(players: List[Player]): Props = Props(new GameSessionActor(players))
}