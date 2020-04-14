package com.dzuniga.mancala.business.move.postchecks.rules.conditions;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.domain.Section;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CapturePebblesCondition implements Condition {

  @Override
  public boolean conditionMet(MoveResult moveResult, Player currentPlayer) {
    Objects.requireNonNull(moveResult, "The move result must not be null");
    Objects.requireNonNull(currentPlayer, "The current player must not be null");

    if (isLastPositionInAMancala(moveResult.getLastDropPosition())) {
      return false;
    }

    if (!isLastPositionInsideCurrentPlayerSection(moveResult.getLastDropPosition(), currentPlayer)) {
      return false;
    }

    return moveResult.getGameboard().getNumberOfPebblesInPit(moveResult.getLastDropPosition()) == 1;
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
