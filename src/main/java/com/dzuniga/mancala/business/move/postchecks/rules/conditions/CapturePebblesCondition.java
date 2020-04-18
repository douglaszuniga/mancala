package com.dzuniga.mancala.business.move.postchecks.rules.conditions;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.domain.Section;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Responsible of checking the capture pebbles rule condition.
 *
 * <p>Capture pebbles rule condition is triggered when the last dropped pebble was in a empty pit
 * inside current player section
 */
@Component
public class CapturePebblesCondition implements Condition {

  @Override
  public boolean test(MoveResult moveResult, Player currentPlayer) {
    Objects.requireNonNull(moveResult, "The move result must not be null");
    Objects.requireNonNull(currentPlayer, "The current currentPlayer must not be null");

    if (isLastPositionInAMancala(moveResult.getLastDropPosition())) {
      return false;
    }

    if (!isLastPositionInsideCurrentPlayerSection(moveResult.getLastDropPosition(), currentPlayer)) {
      return false;
    }

    return isNumberPebblesInLastDropPitEqualsToOne(
        moveResult.getGameboard(), moveResult.getLastDropPosition());
  }

  private boolean isNumberPebblesInLastDropPitEqualsToOne(
      Gameboard gameboard, int lastDropPosition) {
    return gameboard.getNumberOfPebblesInPit(lastDropPosition) == 1;
  }

  private boolean isLastPositionInsideCurrentPlayerSection(
      int lastDropPosition, Player currentPlayer) {
    Section currentPlayerSection = Gameboard.PIT_SECTIONS.get(currentPlayer);
    return currentPlayerSection.getLow() <= lastDropPosition
        && lastDropPosition <= currentPlayerSection.getHigh();
  }

  private boolean isLastPositionInAMancala(int lastDropPosition) {
    return Gameboard.MANCALAS.containsValue(lastDropPosition);
  }
}
