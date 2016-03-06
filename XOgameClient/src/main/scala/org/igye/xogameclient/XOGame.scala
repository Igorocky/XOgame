package org.igye.xogameclient

import akka.actor.{ActorSystem, Props}

object XOGame {
  def start(serverHost: String, serverPort: Int, name: String, sessionId: String, player: XOGamePlayer): Unit = {
    val system = ActorSystem("XOGameClient")
    system.actorOf(Props(new ClientActor(serverHost, serverPort, name, sessionId, player)), name = "ClientActor")
  }
}
