package org.igye.xogameclient;

import akka.event.LoggingAdapter;
import org.igye.xogamecommons.Cell;
import org.igye.xogamecommons.XOField;
import scala.Option;

import java.util.Map;

public interface XOGamePlayer {
    void setLogger(LoggingAdapter log);
    String getName();
    void matchStarted(String msg);
    void gameStarted(String msg, Cell cellType);
    int turn(XOField field);
    void gameOver(Option<String> winner, String msg, XOField field);
    void matchOver(int gamesPlayed, Map<String, Integer> scores, Option<String> winner);
}
