package com.dzuniga.mancala.business.move.postchecks.rules.consequences;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExtraTurnConsequenceTest {
  private ExtraTurnConsequence consequence;

  @BeforeEach
  public void SetUp() {
    consequence = new ExtraTurnConsequence();
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
  public void shouldKeepCurrentPlayerAsNextPlayerAndCombineEventsWhenConsequenceApplied() {
    Gameboard currentBoard =
            new Gameboard("id", new int[] {0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 1, 6, 0});
    int lastDropPosition = 13;
    Player playing = Player.PLAYER_TWO;

    // - board must be same as current board
    // - next player is the current player
    RuleResult expected =
            RuleResult.of(
                    currentBoard, List.of(GameEvent.NEW_GAME_STARTED, GameEvent.EXTRA_TURN_GAINED), playing);

    RuleResult actual =
            consequence.apply(MoveResult.of(currentBoard, lastDropPosition, List.of(GameEvent.NEW_GAME_STARTED)), playing);

    assertEquals(expected, actual);
  }
}