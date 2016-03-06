package org.igye.xogameclient;

import org.igye.xogamecommons.Cell;
import org.igye.xogamecommons.XOField;
import scala.Option;

public interface XOGamePlayer {
    void gameStarted(String msg, Cell cellType);
    int turn(XOField field);
    void gameOver(Option<String> winner, String msg);
}
