package com.dzuniga.mancala.business.move.postchecks.rules.consequences;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.util.GameEventCombiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Applies the following actions: 1. collect the remaining pebbles from each section into its
 * mancalas 2. determine the game result, player one won, player two won or it was a tie
 */
@Component
@Slf4j
public class GameEndConsequence implements Consequence {

  @Override
  public RuleResult apply(MoveResult moveResult, Player currentPlayer) {
    Objects.requireNonNull(moveResult, "The move result must not be null");
    Objects.requireNonNull(currentPlayer, "The current player must not be null");

    // - 1. collect remaining pebbles from each player pits and put them into his/her mancala
    Gameboard boardAfterCollectingRemainingPebbles =
        Gameboard.collectPebblesIntoMancalas(moveResult.getGameboard());
    // - 2. count and compare the amount of pebbles in each mancala getting the game result
    GameEvent endGameResult = getEndGameResult(boardAfterCollectingRemainingPebbles);
    // - 3. generate the final response of the consequence
    return RuleResult.of(
        boardAfterCollectingRemainingPebbles,
        GameEventCombiner.combine(
            moveResult.getGameEvents(), List.of(GameEvent.GAME_ENDED, endGameResult)),
        currentPlayer);
  }

  private GameEvent getEndGameResult(Gameboard gameboard) {
    int totalPebblesPlayerOne =
        gameboard.getNumberOfPebblesInPit(Gameboard.MANCALAS.get(Player.PLAYER_ONE));
    int totalPebblesPlayerTwo =
        gameboard.getNumberOfPebblesInPit(Gameboard.MANCALAS.get(Player.PLAYER_TWO));

    log.debug(
        "Total number of pebbles collected playerOne:[{}], playerTwo:[{}]",
        totalPebblesPlayerOne,
        totalPebblesPlayerTwo);

    if (totalPebblesPlayerOne == totalPebblesPlayerTwo) {
      return GameEvent.TIED;
    }

    if (totalPebblesPlayerOne > totalPebblesPlayerTwo) {
      return GameEvent.PLAYER_ONE_WON;
    }

    return GameEvent.PLAYER_TWO_WON;
  }
}
