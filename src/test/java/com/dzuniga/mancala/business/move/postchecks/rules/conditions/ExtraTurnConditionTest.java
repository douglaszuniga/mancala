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

public class ExtraTurnConditionTest {
  private ExtraTurnCondition condition;

  @BeforeEach
  public void setUp() {
    condition = new ExtraTurnCondition();
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
  public void shouldReturnFalseWhenLastDropPositionIsNotCurrentPlayerMancala() {
    int lastDropPosition = Gameboard.MANCALAS.getOrDefault(Player.PLAYER_TWO, 6);

    boolean actual =
        condition.test(
            MoveResult.of(Gameboard.newGameBoard(), lastDropPosition, List.of()),
            Player.PLAYER_ONE);

    assertFalse(actual);
  }

  @Test
  public void shouldReturnFalseWhenLastDropPositionIsAPit() {
    int lastDropPosition = 1;

    boolean actual =
        condition.test(
            MoveResult.of(Gameboard.newGameBoard(), lastDropPosition, List.of()),
            Player.PLAYER_ONE);

    assertFalse(actual);
  }

  @Test
  public void shouldReturnTrueWhenLastDropPositionWasInCurrentPlayerMancala() {
    int lastDropPosition = Gameboard.MANCALAS.getOrDefault(Player.PLAYER_TWO, 13);

    boolean actual =
        condition.test(
            MoveResult.of(Gameboard.newGameBoard(), lastDropPosition, List.of()),
            Player.PLAYER_TWO);

    assertTrue(actual);
  }
}
