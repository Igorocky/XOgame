package org.igye.xogamecommons

import com.typesafe.config.Config
import com.typesafe.config.ConfigValueFactory._

import scala.annotation.tailrec

object XOGameCommons {
  @tailrec
  def updateConfigFromArgs(config: Config, args: Array[String], startIdx: Int = 0): Config = {
    if (startIdx >= args.length) {
      config
    } else {
      args(startIdx) match {
        case "-h" => updateConfigFromArgs(
          config.withValue("akka.remote.netty.tcp.hostname", fromAnyRef(args(startIdx + 1))),
          args,
          startIdx + 2
        )
        case "-p" => updateConfigFromArgs(
          config.withValue("akka.remote.netty.tcp.port", fromAnyRef(args(startIdx + 1))),
          args,
          startIdx + 2
        )
      }
    }
  }
}
