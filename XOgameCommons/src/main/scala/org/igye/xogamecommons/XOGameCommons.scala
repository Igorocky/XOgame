package org.igye.xogamecommons

import com.typesafe.config.Config
import com.typesafe.config.ConfigValueFactory._

import scala.annotation.tailrec

object XOGameCommons {
  final val SERVER_SYSTEM_NAME = "XOGameServer"
  final val SERVER_ENTRY_ACTOR_NAME = "EntryActor"

  @tailrec
  def updateConfigFromArgs(config: Config, args: Array[String], mapper: Map[String, String], startIdx: Int = 0): Config = {
    if (startIdx >= args.length) {
      config
    } else {
      if (mapper.contains(args(startIdx))) {
        updateConfigFromArgs(
          config.withValue(mapper(args(startIdx)), fromAnyRef(args(startIdx + 1))),
          args,
          mapper,
          startIdx + 2
        )
      } else {
        updateConfigFromArgs(
          config,
          args,
          mapper,
          startIdx + 1
        )
      }
    }
  }
}
