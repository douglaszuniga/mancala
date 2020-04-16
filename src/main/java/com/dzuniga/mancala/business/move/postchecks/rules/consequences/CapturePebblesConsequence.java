package com.dzuniga.mancala.business.move.postchecks.rules.consequences;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.util.GameEventCombiner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CapturePebblesConsequence implements Consequence {

  @Override
  public RuleResult apply(MoveResult moveResult, Player currentPlayer) {

    Gameboard boardAfterCapturing =
        Gameboard.captureOppositePitPebbles(
            moveResult.getGameboard(), moveResult.getLastDropPosition(), currentPlayer);

    return RuleResult.of(
        boardAfterCapturing,
        GameEventCombiner.combine(moveResult.getGameEvents(), List.of(GameEvent.pebblesCaptured)),
        Player.getOppositePlayer(currentPlayer));
  }
}
