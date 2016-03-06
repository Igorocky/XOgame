package org.igye.xogameserver

import akka.actor.{Actor, Props}
import org.igye.xogamecommons.GameStarted

class GameSessionActor(players: List[Player]) extends Actor {
  assert(players.size == 2)

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    super.preStart()
    players(0) ! GameStarted(self, s"You will play with ${players(1).name}.")
    players(1) ! GameStarted(self, s"You will play with ${players(0).name}.")
  }

  override def receive: Receive = {
    case _ =>
  }
}

object GameSessionActor {
  def props(players: List[Player]): Props = Props(new GameSessionActor(players))
}