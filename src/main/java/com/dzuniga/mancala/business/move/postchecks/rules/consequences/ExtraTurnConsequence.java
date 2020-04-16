package com.dzuniga.mancala.business.move.postchecks.rules.consequences;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.util.GameEventCombiner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExtraTurnConsequence implements Consequence {

  @Override
  public RuleResult apply(MoveResult moveResult, Player currentPlayer) {

    return RuleResult.of(
        moveResult.getGameboard(),
        GameEventCombiner.combine(moveResult.getGameEvents(), List.of(GameEvent.extraTurnGained)),
        currentPlayer);
  }
}
