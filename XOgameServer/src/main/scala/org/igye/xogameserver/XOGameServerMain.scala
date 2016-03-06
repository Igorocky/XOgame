package org.igye.xogameserver

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}
import org.igye.xogamecommons.XOGameCommons._

object XOGameServerMain {
  def main(args: Array[String]) {
    val config: Config = updateConfigFromArgs(
      ConfigFactory.load(),
      args,
      Map(
        "-h" -> "akka.remote.netty.tcp.hostname",
        "-p" -> "akka.remote.netty.tcp.port",
        "-s" -> Constants.SHUTDOWN_ON_GAMEOVER
      )
    )
    XOConfig.setup(config)
    val system = ActorSystem(SERVER_SYSTEM_NAME, config)
    val entryActor = system.actorOf(Props[EntryActor], name = SERVER_ENTRY_ACTOR_NAME)
  }
}
