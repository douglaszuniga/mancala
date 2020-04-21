package com.dzuniga.mancala.business.move.postchecks.rules.consequences;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.util.GameEventCombiner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Applies the capture of the pebbles from the pit + opposite pit and add them into the player's mancala
 */
@Component
public class CapturePebblesConsequence implements Consequence {

  @Override
  public RuleResult apply(MoveResult moveResult, Player currentPlayer) {
    Objects.requireNonNull(moveResult, "The move result must not be null");
    Objects.requireNonNull(currentPlayer, "The current player must not be null");

    // - delegate the capture logic to the owner of the board state
    Gameboard boardAfterCapturing =
        Gameboard.captureOppositePitPebbles(
            moveResult.getGameboard(), moveResult.getLastDropPosition(), currentPlayer);

    return RuleResult.of(
        boardAfterCapturing,
        GameEventCombiner.combine(moveResult.getGameEvents(), List.of(GameEvent.PEBBLES_CAPTURED)),
        Player.getOppositePlayer(currentPlayer));
  }
}
