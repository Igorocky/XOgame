package org.igye.xogamecommons

sealed abstract class EntryActorMsgs

case class WantToPLay(name: String, sessionId: String) extends EntryActorMsgs
