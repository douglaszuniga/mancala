package com.dzuniga.mancala.business.move.postchecks.rules.conditions;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.postchecks.rules.conditions.CapturePebblesCondition;
import com.dzuniga.mancala.business.move.postchecks.rules.conditions.Condition;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CapturePebblesConditionTest {

  private CapturePebblesCondition condition;

  @BeforeEach
  public void setUp() {
    condition = new CapturePebblesCondition();
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenMoveResultIsNull() {
    assertThrows(NullPointerException.class, () -> condition.test(null, Player.PLAYER_ONE));
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenCurrentPlayerIsNull() {
    assertThrows(
        NullPointerException.class,
        () -> condition.test(MoveResult.of(Gameboard.newGameBoard(), 0, List.of()), null));
  }

  @Test
  public void shouldReturnFalseWhenNumberOfPebblesInLastDropPositionIsGreaterThanOne() {
    Gameboard gameboard = Gameboard.newGameBoard();
    boolean actual =
        condition.test(MoveResult.of(gameboard, 0, List.of()), Player.PLAYER_TWO);
    assertFalse(actual);
  }

  @Test
  public void shouldReturnFalseWhenLastDropPositionIsAMancala() {
    Gameboard gameboard = Gameboard.newGameBoard();
    boolean actual =
        condition.test(
            MoveResult.of(gameboard, Gameboard.MANCALAS.get(Player.PLAYER_ONE), List.of()),
            Player.PLAYER_TWO);
    assertFalse(actual);
  }

  @Test
  public void shouldReturnFalseWhenLastDropPositionIsNotInsideCurrentPlayerSection() {
    Gameboard gameboard = new Gameboard("id", new int[] {1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1});
    boolean actual =
        condition.test(MoveResult.of(gameboard, 0, List.of()), Player.PLAYER_TWO);
    assertFalse(actual);
  }

  @Test
  public void shouldReturnTrueWhenNumberOfPebblesInLastDropPositionIsOne() {
    Gameboard gameboard = new Gameboard("id", new int[] {1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1});
    boolean actual =
        condition.test(MoveResult.of(gameboard, 0, List.of()), Player.PLAYER_ONE);
    assertTrue(actual);
  }
}
