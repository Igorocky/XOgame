package org.igye.xogamecommons

sealed abstract class MatchSessionActorMsgs

case class StartMatch() extends MatchSessionActorMsgs