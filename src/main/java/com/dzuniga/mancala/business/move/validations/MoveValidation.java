package com.dzuniga.mancala.business.move.validations;

import com.dzuniga.mancala.domain.Move;

import java.util.Objects;

/**
 * Functional Interface used to indicate a validation that has to be done before applying a move
 */
@FunctionalInterface
public interface MoveValidation {
  /**
   * Preconditions: - Move must not be null
   *
   * <p>Validate that the move follows the validation rule
   *
   * <p>Expected Exceptions: - NullPointerException when Move is null
   *
   * @param move move object containing the necessary info to apply the next move
   */
  void validate(Move move);

  /**
   * Returns a composed function that first validate move and then run the {@code after} validation
   *
   * @param after validation that will be called after
   */
  default MoveValidation andThen(MoveValidation after) {
    Objects.requireNonNull(after);

    return (Move m) -> {
      validate(m);
      after.validate(m);
    };
  }
}
