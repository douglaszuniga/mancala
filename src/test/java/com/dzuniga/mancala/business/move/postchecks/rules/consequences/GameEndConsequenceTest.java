package com.dzuniga.mancala.business.move.postchecks.rules.consequences;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameEndConsequenceTest {

  private GameEndConsequence consequence;

  @BeforeEach
  public void SetUp() {
    consequence = new GameEndConsequence();
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenMoveResultIsNull() {
    assertThrows(NullPointerException.class, () -> consequence.apply(null, Player.PLAYER_ONE));
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenPlayerIsNull() {
    assertThrows(
        NullPointerException.class,
        () -> consequence.apply(MoveResult.of(Gameboard.newGameBoard(), 0, List.of()), null));
  }

  @Test
  public void
      shouldReturnPitsEmptySamePlayerAndPlayerOneWonWhenOnePitSectionHasNoPebblesAndPlayerOneMancalaHasMorePebbles() {
    Gameboard currentBoard =
        new Gameboard("id", new int[] {0, 0, 0, 0, 0, 0, 37, 1, 0, 0, 0, 0, 0, 35});
    int lastDropPosition = 6;
    Player playing = Player.PLAYER_ONE;

    // - board must be same as current board
    // - next player is the current player
    Gameboard expectedBoard =
        new Gameboard("id", new int[] {0, 0, 0, 0, 0, 0, 37, 0, 0, 0, 0, 0, 0, 36});

    RuleResult expected =
        RuleResult.of(
            expectedBoard,
            List.of(GameEvent.NEW_GAME_STARTED, GameEvent.GAME_ENDED, GameEvent.PLAYER_ONE_WON),
            playing);

    RuleResult actual =
        consequence.apply(
            MoveResult.of(currentBoard, lastDropPosition, List.of(GameEvent.NEW_GAME_STARTED)),
            playing);

    assertArrayEquals(expected.getGameboard().getBoard(), actual.getGameboard().getBoard());
    assertEquals(expected.getGameEvents(), actual.getGameEvents());
    assertEquals(expected.getNextPlayer(), actual.getNextPlayer());
  }

  @Test
  public void
      shouldReturnPitsEmptySamePlayerAndPlayerTwoWonWhenOnePitSectionHasNoPebblesAndPlayerTwoMancalaHasMorePebbles() {
    Gameboard currentBoard =
        new Gameboard("id", new int[] {0, 1, 2, 0, 1, 0, 33, 0, 0, 0, 0, 0, 0, 38});
    int lastDropPosition = 1;
    Player playing = Player.PLAYER_TWO;

    // - board must be same as current board
    // - next player is the current player
    Gameboard expectedBoard =
        new Gameboard("id", new int[] {0, 0, 0, 0, 0, 0, 37, 0, 0, 0, 0, 0, 0, 38});

    RuleResult expected =
        RuleResult.of(
            expectedBoard,
            List.of(GameEvent.NEW_GAME_STARTED, GameEvent.GAME_ENDED, GameEvent.PLAYER_TWO_WON),
            playing);

    RuleResult actual =
        consequence.apply(
            MoveResult.of(currentBoard, lastDropPosition, List.of(GameEvent.NEW_GAME_STARTED)),
            playing);

    assertArrayEquals(expected.getGameboard().getBoard(), actual.getGameboard().getBoard());
    assertEquals(expected.getGameEvents(), actual.getGameEvents());
    assertEquals(expected.getNextPlayer(), actual.getNextPlayer());
  }

  @Test
  public void
      shouldReturnPitsEmptySamePlayerAndTieWhenOnePitSectionHasNoPebblesAndBothMancalaHasEqualNumberOfPebbles() {
    Gameboard currentBoard =
        new Gameboard("id", new int[] {0, 1, 2, 0, 1, 0, 33, 0, 0, 0, 0, 0, 0, 37});
    int lastDropPosition = 1;
    Player playing = Player.PLAYER_TWO;

    // - board must be with zero pebbles in the pits and pebbles in the mancalas
    // - next player is the current player
    Gameboard expectedBoard =
        new Gameboard("id", new int[] {0, 0, 0, 0, 0, 0, 37, 0, 0, 0, 0, 0, 0, 37});

    RuleResult expected =
        RuleResult.of(
            expectedBoard,
            List.of(GameEvent.NEW_GAME_STARTED, GameEvent.GAME_ENDED, GameEvent.TIED),
            playing);

    RuleResult actual =
        consequence.apply(
            MoveResult.of(currentBoard, lastDropPosition, List.of(GameEvent.NEW_GAME_STARTED)),
            playing);

    assertArrayEquals(expected.getGameboard().getBoard(), actual.getGameboard().getBoard());
    assertEquals(expected.getGameEvents(), actual.getGameEvents());
    assertEquals(expected.getNextPlayer(), actual.getNextPlayer());
  }
}
