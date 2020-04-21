package com.dzuniga.mancala.business.move.postchecks.rules.consequences;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.util.GameEventCombiner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Applies the action indicating that the next player is equals to current player and add the
 * EXTRA_TURN_GAINED event to the result not side effects in the board
 */
@Component
public class ExtraTurnConsequence implements Consequence {

  @Override
  public RuleResult apply(MoveResult moveResult, Player currentPlayer) {
    Objects.requireNonNull(moveResult, "The move result must not be null");
    Objects.requireNonNull(currentPlayer, "The current player must not be null");

    return RuleResult.of(
        moveResult.getGameboard(),
        GameEventCombiner.combine(moveResult.getGameEvents(), List.of(GameEvent.EXTRA_TURN_GAINED)),
        currentPlayer);
  }
}
