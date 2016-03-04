package org.igye.xogameclient

import akka.actor.{Props, ActorSystem}

object XOGameClientMain extends App {
  implicit val system = ActorSystem("LocalSystem")
  val localActor = system.actorOf(Props[LocalActor], name = "LocalActor")
  localActor ! "START"
}
