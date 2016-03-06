package org.igye.xogameclient

import akka.actor.{Props, ActorSystem}

object XOGameClientMain extends App {
  implicit val system = ActorSystem("XOGameClient")
  val clientActor = system.actorOf(Props(new ClientActor("127.0.0.1", 5150, "client1", "")), name = "ClientActor")
}
