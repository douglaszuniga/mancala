package com.dzuniga.mancala.business.move.postchecks.rules.consequences;

import com.dzuniga.mancala.business.move.model.PostCheckResult;
import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class GameEndConsequence implements Consequence {

  @Override
  public PostCheckResult apply(
      Gameboard boardAfterMoving, int lastDropPosition, Player currentPlayer) {
    // - 1. collect remaining pebbles from each player pits and put them into his/her mancala
    Gameboard boardAfterCollectingRemainingPebbles =
        Gameboard.collectPebblesIntoMancalas(boardAfterMoving);
    // - 2. count and compare the amount of pebbles in each mancala getting the game result
    GameEvent endGameResult = getEndGameResult(boardAfterCollectingRemainingPebbles);
    // - 3. generate the final response of the consequence
    return PostCheckResult.of(
        boardAfterCollectingRemainingPebbles,
        List.of(GameEvent.gameEnded, endGameResult),
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
      return GameEvent.tied;
    }

    if (totalPebblesPlayerOne > totalPebblesPlayerTwo) {
      return GameEvent.playerOneWon;
    }

    return GameEvent.playerTwoWon;
  }
}
