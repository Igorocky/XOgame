package org.igye.xogameserver

import akka.actor.{Props, ActorSystem}

object XOGameServerMain extends App {
  val system = ActorSystem("HelloRemoteSystem")
  val remoteActor = system.actorOf(Props[RemoteActor], name = "RemoteActor")
  remoteActor ! "The RemoteActor is alive"
}
