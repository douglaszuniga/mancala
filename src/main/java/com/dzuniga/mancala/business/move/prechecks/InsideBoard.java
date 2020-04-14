package com.dzuniga.mancala.business.move.prechecks;

import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Turn;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class InsideBoard implements PreCheck {

  @Override
  public boolean test(Move move) {
    Objects.requireNonNull(move, "The move must not be null");

    Turn currentTurn = move.getCurrentTurn();
    Gameboard currentBoard = currentTurn.getCurrentBoard();
    return move.getStartPosition() >= 0 && move.getStartPosition() < currentBoard.getGameboard().length;
  }
}
