package org.igye.xogamecommons

sealed abstract class EntryActorMsgs

case class WantToPlay(name: String, sessionId: String) extends EntryActorMsgs
