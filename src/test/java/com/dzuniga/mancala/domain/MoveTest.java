package com.dzuniga.mancala.domain;

import com.dzuniga.mancala.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MoveTest {
  @Test
  public void testEquality() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int lastDropPosition = 0;
    Turn turn =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_ONE)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();

    Move alpha = Move.builder().startPosition(lastDropPosition).currentTurn(turn).build();
    Move beta = Move.builder().startPosition(lastDropPosition).currentTurn(turn).build();
    Move gama = Move.builder().startPosition(lastDropPosition).currentTurn(turn).build();
    Move delta = Move.builder().startPosition(lastDropPosition + 1).currentTurn(turn).build();

    TestUtils.assertEqualsContract(alpha, beta, gama, delta);
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenTurnIsNull() {
    int lastDropPosition = 0;

    Assertions.assertThrows(
        NullPointerException.class,
        () -> Move.builder().startPosition(lastDropPosition).currentTurn(null).build());
  }
}
