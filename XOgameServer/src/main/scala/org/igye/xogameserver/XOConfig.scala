package org.igye.xogameserver

import com.typesafe.config.Config

object XOConfig {
  private var configVar: Config = _
  private lazy val cfg: Config = configVar

  def setup(config: Config): Unit = configVar = config

  def getInt(prop: String) = cfg.getInt(prop)

  def getBoll(prop: String) = cfg.getBoolean(prop)
}
