package com.dzuniga.mancala.business.move.validations;

import com.dzuniga.mancala.business.move.validations.exceptions.IncorrectPitException;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Validates that the start position is from a pit and not a mancala
 */
@Component
public class FromAPit implements MoveValidation {

  @Override
  public void validate(Move move) {
    Objects.requireNonNull(move, "The move must not be null");

    if (Gameboard.MANCALAS.containsValue(move.getStartPosition())) {
      throw new IncorrectPitException(move);
    }
  }
}
