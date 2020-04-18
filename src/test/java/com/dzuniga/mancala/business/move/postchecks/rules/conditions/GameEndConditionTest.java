package com.dzuniga.mancala.business.move.postchecks.rules.conditions;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameEndConditionTest {

  private GameEndCondition condition;

  @BeforeEach
  public void setUp() {
    condition = new GameEndCondition();
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenMoveResultIsnull() {
    assertThrows(NullPointerException.class, () -> condition.test(null, Player.PLAYER_ONE));
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenPlayerIsnull() {
    assertThrows(
            NullPointerException.class,
            () -> condition.test(MoveResult.of(Gameboard.newGameBoard(), 0, List.of()), null));
  }

  @Test
  public void shouldReturnFalseWhenGameHasJustStarted() {
    Gameboard gameboard = Gameboard.newGameBoard();
    boolean actual =
        condition.test(MoveResult.of(gameboard, 0, List.of()), Player.PLAYER_TWO);
    assertFalse(actual);
  }

  @Test
  public void shouldReturnTrueWhenPlayerSectionHasNoPebbles() {
    Gameboard gameboard = new Gameboard("id", new int[] {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1});
    boolean actual =
        condition.test(MoveResult.of(gameboard, 0, List.of()), Player.PLAYER_TWO);
    assertTrue(actual);
  }
}
