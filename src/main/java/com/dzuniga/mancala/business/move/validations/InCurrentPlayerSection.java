package com.dzuniga.mancala.business.move.validations;

import com.dzuniga.mancala.business.move.validations.exceptions.IncorrectPlayerSectionException;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Section;
import com.dzuniga.mancala.domain.Turn;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Validates that the start position is inside the current player's section of the board
 */
@Component
public class InCurrentPlayerSection implements MoveValidation {

  @Override
  public void validate(Move move) {
    Objects.requireNonNull(move, "The move must not be null");

    Turn currentTurn = move.getCurrentTurn();

    Section currentPlayerSection = Gameboard.PIT_SECTIONS.get(currentTurn.getPlaying());

    if (!isInSection(move, currentPlayerSection)) {
      throw new IncorrectPlayerSectionException(move);
    }
  }

  private boolean isInSection(Move move, Section playerSection) {
    return move.getStartPosition() >= playerSection.getLow()
        && move.getStartPosition() <= playerSection.getHigh();
  }
}
