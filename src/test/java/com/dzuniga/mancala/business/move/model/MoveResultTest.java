package com.dzuniga.mancala.business.move.model;

import com.dzuniga.mancala.TestUtils;
import com.dzuniga.mancala.domain.Gameboard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MoveResultTest {
  @Test
  public void testEquality() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int lastDropPosition = 0;

    MoveResult alpha = MoveResult.of(gameboard, lastDropPosition, List.of());
    MoveResult beta = MoveResult.of(gameboard, lastDropPosition, List.of());
    MoveResult gama = MoveResult.of(gameboard, lastDropPosition, List.of());
    MoveResult delta = MoveResult.of(Gameboard.newGameBoard(), lastDropPosition, List.of());

    TestUtils.assertEqualsContract(alpha, beta, gama, delta);
  }

  @Test
  public void shouldNullPointerExceptionWhenGameboardIsNull() {
    Assertions.assertThrows(NullPointerException.class, () -> MoveResult.of(null, 0, List.of()));
  }

  @Test
  public void shouldNullPointerExceptionWhenEventListIsNull() {
    Assertions.assertThrows(
        NullPointerException.class, () -> MoveResult.of(Gameboard.newGameBoard(), 0, null));
  }

  @Test
  public void shouldIllegalArgumentExceptionWhenLastDropPositionIsOutsideTheBoard() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> MoveResult.of(Gameboard.newGameBoard(), -1, List.of()));
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> MoveResult.of(Gameboard.newGameBoard(), Gameboard.SIZE, List.of()));
  }
}
