package org.igye.xogameserver

import akka.actor.Actor
import org.igye.xogamecommons.WantToPLay

class EntryActor extends Actor {
  def receive = {
    case WantToPLay(name, sessionId) =>
      println(s"'$name' wants to play with id='$sessionId'")
      sender ! s"you are accepted with name='$name' and id='$sessionId'"
  }
}
