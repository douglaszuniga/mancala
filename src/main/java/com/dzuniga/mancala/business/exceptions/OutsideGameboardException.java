package com.dzuniga.mancala.business.exceptions;

import com.dzuniga.mancala.domain.Move;

/**
 * Validation exception with semantic value indicating that the move
 * position must be inside the gameboard.
 */
public class OutsideGameboardException extends MoveValidationException {
  public OutsideGameboardException(Move move) {
    super(move);
  }

  @Override
  public String getMessage() {
    return "The move is invalid, the selected position is outside the boundaries of the gameboard.";
  }
}
