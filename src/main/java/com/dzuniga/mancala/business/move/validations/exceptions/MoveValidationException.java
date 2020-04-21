package com.dzuniga.mancala.business.move.validations.exceptions;

import com.dzuniga.mancala.domain.Move;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/** Base exception used when a move validation did not pass */
public class MoveValidationException extends RuntimeException {
  /** Move object for dev information purposes */
  private final Move move;

  public MoveValidationException(Move move) {
    this.move = move;
  }

  public Move getMove() {
    return move;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("move", move)
        .append("message", getMessage())
        .toString();
  }
}
