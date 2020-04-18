package com.dzuniga.mancala.business.move.postchecks.rules.conditions;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Responsible of checking the extra turn rule condition.
 *
 * <p>Extra turn rule condition is triggered when the last pebble was dropped in the current
 * player's mancala
 */
@Component
public class ExtraTurnCondition implements Condition {

  @Override
  public boolean test(MoveResult moveResult, Player currentPlayer) {
    Objects.requireNonNull(moveResult, "The move result must not be null");
    Objects.requireNonNull(currentPlayer, "The current currentPlayer must not be null");

    return Gameboard.MANCALAS.get(currentPlayer).equals(moveResult.getLastDropPosition());
  }
}
