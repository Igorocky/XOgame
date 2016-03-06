package org.igye.xogameserver

import akka.actor.Actor
import org.igye.xogamecommons.WantToPlay

class EntryActor extends Actor {
  var awaitingPlayers = Map[String, Player]()

  def receive = {
    case WantToPlay(name, sessionId) =>
      println(s"'$name' wants to play with id='$sessionId'")
      sender ! s"you are accepted with name='$name' and id='$sessionId' "
      if (awaitingPlayers.contains(sessionId)) {
        val players = List(awaitingPlayers(sessionId), Player(name, sessionId, sender))
        context.system.actorOf(GameSessionActor.props(players), "GameSessionActor")
        awaitingPlayers -= sessionId
        println(s"GameSessionActor was created for $players")
      } else {
        awaitingPlayers += sessionId -> Player(name, sessionId, sender)
      }

  }
}
