package com.dzuniga.mancala.business.move.validations.exceptions;

import com.dzuniga.mancala.domain.Move;

/**
 * Move validation exception with semantic value indicating that the move
 * must be from a pit that contains at least one pebble
 */
public class NoPebblesInPitException extends MoveValidationException {
  public NoPebblesInPitException(Move move) {
    super(move);
  }

  @Override
  public String getMessage() {
    return "The move is invalid, the pit is empty, it must contain at least one pebble.";
  }
}
