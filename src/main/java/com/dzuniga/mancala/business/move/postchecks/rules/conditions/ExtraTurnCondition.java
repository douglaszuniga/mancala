package com.dzuniga.mancala.business.move.postchecks.rules.conditions;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ExtraTurnCondition implements Condition {

  @Override
  public boolean conditionMet(MoveResult moveResult, Player currentPlayer) {
    Objects.requireNonNull(moveResult, "The move result must not be null");
    Objects.requireNonNull(currentPlayer, "The current player must not be null");

    return Gameboard.MANCALAS.containsValue(moveResult.getLastDropPosition());
  }
}
