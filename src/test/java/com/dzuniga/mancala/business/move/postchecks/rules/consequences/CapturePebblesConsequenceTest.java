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

public class CapturePebblesConsequenceTest {

  private CapturePebblesConsequence consequence;

  @BeforeEach
  public void SetUp() {
    consequence = new CapturePebblesConsequence();
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
  public void shouldCapturePebblesFromCurrentAndOppositePitWhenBothPitsHavePebbles() {
    Gameboard currentBoard =
        new Gameboard("id", new int[] {0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 1, 6, 0});
    int lastDropPosition = 11;
    Player playing = Player.PLAYER_TWO;

    Gameboard expectedBoard =
        new Gameboard("id", new int[] {0, 0, 7, 7, 7, 7, 1, 6, 6, 6, 6, 0, 6, 8});
    // - only the pebblesCapture event
    // - next player is the opposite of current player
    RuleResult expected =
        RuleResult.of(
            expectedBoard, List.of(GameEvent.pebblesCaptured), Player.getOppositePlayer(playing));

    RuleResult actual =
        consequence.apply(MoveResult.of(currentBoard, lastDropPosition, List.of()), playing);

    assertArrayEquals(expected.getGameboard().getGameboard(), actual.getGameboard().getGameboard());
    assertEquals(expected.getGameEvents(), actual.getGameEvents());
    assertEquals(expected.getNextPlayer(), actual.getNextPlayer());
  }

  @Test
  public void shouldCapturePebblesCombiningEventWhenMoveResultHasEvents() {
    Gameboard currentBoard =
        new Gameboard("id", new int[] {0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 1, 6, 0});
    int lastDropPosition = 11;
    Player playing = Player.PLAYER_TWO;

    Gameboard expectedBoard =
        new Gameboard("id", new int[] {0, 0, 7, 7, 7, 7, 1, 6, 6, 6, 6, 0, 6, 8});
    // - only the pebblesCapture event
    // - next player is the opposite of current player
    RuleResult expected =
        RuleResult.of(
            expectedBoard,
            List.of(GameEvent.newGameStarted, GameEvent.pebblesCaptured),
            Player.getOppositePlayer(playing));

    RuleResult actual =
        consequence.apply(
            MoveResult.of(currentBoard, lastDropPosition, List.of(GameEvent.newGameStarted)),
            playing);

    assertArrayEquals(expected.getGameboard().getGameboard(), actual.getGameboard().getGameboard());
    assertEquals(expected.getGameEvents(), actual.getGameEvents());
    assertEquals(expected.getNextPlayer(), actual.getNextPlayer());
  }
}
