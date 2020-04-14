package com.dzuniga.mancala.business.move.postchecks.rules.consequences;

import com.dzuniga.mancala.business.move.model.PostCheckResult;
import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CapturePebblesConsequence implements Consequence {

  @Override
  public PostCheckResult apply(
      Gameboard boardAfterMoving, int lastDropPosition, Player currentPlayer) {

    Gameboard boardAfterCapturing =
        Gameboard.captureOppositePitPebbles(boardAfterMoving, lastDropPosition, currentPlayer);

    return PostCheckResult.of(
        boardAfterCapturing,
        List.of(GameEvent.pebblesCaptured),
        Player.getOppositePlayer(currentPlayer));
  }
}
