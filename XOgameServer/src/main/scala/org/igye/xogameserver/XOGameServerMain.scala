package org.igye.xogameserver

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import org.igye.xogamecommons.XOGameCommons._

object XOGameServerMain {
  def main(args: Array[String]) {
    val system = ActorSystem(SERVER_SYSTEM_NAME, updateConfigFromArgs(ConfigFactory.load(), args))
    val entryActor = system.actorOf(Props[EntryActor], name = SERVER_ENTRY_ACTOR_NAME)
  }
}
