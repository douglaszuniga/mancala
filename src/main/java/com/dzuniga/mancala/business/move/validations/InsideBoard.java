package com.dzuniga.mancala.business.move.validations;

import com.dzuniga.mancala.business.move.validations.exceptions.OutsideGameboardException;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Validates that the start position is inside the boundaries of the gameboard
 */
@Component
public class InsideBoard implements MoveValidation {

  @Override
  public void validate(Move move) {
    Objects.requireNonNull(move, "The move must not be null");

    if (Gameboard.SIZE <= move.getStartPosition() || move.getStartPosition() < 0) {
      throw new OutsideGameboardException(move);
    }
  }
}
