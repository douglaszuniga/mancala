package com.dzuniga.mancala.business.move.validations;

import com.dzuniga.mancala.business.move.validations.exceptions.NoPebblesInPitException;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.domain.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PitHasPebblesTest {
  private PitHasPebbles validation;

  @BeforeEach
  void setUp() {
    validation = new PitHasPebbles();
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenMoveIsNull() {
    assertThrows(NullPointerException.class, () -> validation.validate(null));
  }

  @Test
  public void shouldNotThrowExceptionWhenStartPositionIsFromPitThatHasAtLeastOnePebble() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int startPosition = 0;
    Turn turn =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_ONE)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();
    Move move = Move.builder().currentTurn(turn).startPosition(startPosition).build();

    assertDoesNotThrow(() -> validation.validate(move));
  }

  @Test
  public void shouldThrowONoPebblesInPitExceptionWhenStartPositionIsFromPitThatHasNoPebbles() {
    Gameboard gameboard = new Gameboard("id", new int[] {0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 1, 6, 0});
    int startPosition = 0;
    Turn turn =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_ONE)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();
    Move move = Move.builder().currentTurn(turn).startPosition(startPosition).build();

    assertThrows(NoPebblesInPitException.class, () -> validation.validate(move));
  }
}
