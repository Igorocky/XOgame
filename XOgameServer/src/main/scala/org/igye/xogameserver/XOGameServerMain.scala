package org.igye.xogameserver

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import org.igye.xogamecommons.XOGameCommons.updateConfigFromArgs

object XOGameServerMain {
  def main(args: Array[String]) {
    val system = ActorSystem("HelloRemoteSystem", updateConfigFromArgs(ConfigFactory.load(), args))
    val remoteActor = system.actorOf(Props[RemoteActor], name = "RemoteActor")
    remoteActor ! "The RemoteActor is alive"
  }
}
