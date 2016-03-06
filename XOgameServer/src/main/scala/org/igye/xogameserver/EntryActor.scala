package org.igye.xogameserver

import akka.actor.Actor
import org.apache.logging.log4j.LogManager
import org.igye.xogamecommons.WantToPlay

class EntryActor extends Actor {
  val log = LogManager.getLogger()

  var awaitingPlayers = Map[String, Player]()

  def receive = {
    case WantToPlay(name, sessionId) =>
      log.info(s"'$name' wants to play with id='$sessionId'")
      sender ! s"you are accepted with name='$name' and id='$sessionId' "
      if (awaitingPlayers.contains(sessionId)) {
        val players = List(awaitingPlayers(sessionId), Player(name, sessionId, sender))
        val className = classOf[MatchSessionActor].getSimpleName
        context.system.actorOf(
          MatchSessionActor.props(players),
          s"${className}:$sessionId"
        )
        awaitingPlayers -= sessionId
        log.info(s"${className} was created for $players")
      } else {
        awaitingPlayers += sessionId -> Player(name, sessionId, sender)
      }

  }
}
