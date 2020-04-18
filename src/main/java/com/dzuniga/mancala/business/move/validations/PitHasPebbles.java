package com.dzuniga.mancala.business.move.validations;

import com.dzuniga.mancala.business.exceptions.NoPebblesInPitException;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Turn;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Validates that the start position is from a pit that contains at least one pebble
 */
@Component
public class PitHasPebbles implements MoveValidation {

  @Override
  public void validate(Move move) {
    Objects.requireNonNull(move, "The move must not be null");

    Turn currentTurn = move.getCurrentTurn();
    Gameboard currentBoard = currentTurn.getCurrentBoard();

    if (currentBoard.getNumberOfPebblesInPit(move.getStartPosition()) == 0) {
      throw new NoPebblesInPitException(move);
    }
  }
}
