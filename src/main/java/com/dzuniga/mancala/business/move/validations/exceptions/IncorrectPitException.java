package com.dzuniga.mancala.business.move.validations.exceptions;

import com.dzuniga.mancala.domain.Move;

/**
 * Move validation exception with semantic value indicating that the move
 * must be from a pit, not a mancala
 */
public class IncorrectPitException extends MoveValidationException {
  public IncorrectPitException(Move move) {
    super(move);
  }

  @Override
  public String getMessage() {
    return "The move is invalid, the selected position is a mancala.";
  }
}
