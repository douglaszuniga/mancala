package com.dzuniga.mancala.business.move.validations;

import com.dzuniga.mancala.business.move.validations.exceptions.IncorrectPlayerSectionException;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.domain.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InCurrentPlayerSectionTest {
  private InCurrentPlayerSection validation;

  @BeforeEach
  void setUp() {
    validation = new InCurrentPlayerSection();
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenMoveIsNull() {
    assertThrows(NullPointerException.class, () -> validation.validate(null));
  }

  @Test
  public void shouldNotThrowExceptionWhenStartPositionIsFromPlayerOneSection() {
    Gameboard gameboard = Gameboard.newGameBoard();
    Turn turn =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_ONE)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();

    assertDoesNotThrow(() -> validation.validate(Move.builder().currentTurn(turn).startPosition(0).build()));
    assertDoesNotThrow(() -> validation.validate(Move.builder().currentTurn(turn).startPosition(1).build()));
    assertDoesNotThrow(() -> validation.validate(Move.builder().currentTurn(turn).startPosition(2).build()));
    assertDoesNotThrow(() -> validation.validate(Move.builder().currentTurn(turn).startPosition(3).build()));
    assertDoesNotThrow(() -> validation.validate(Move.builder().currentTurn(turn).startPosition(4).build()));
    assertDoesNotThrow(() -> validation.validate(Move.builder().currentTurn(turn).startPosition(5).build()));
    assertDoesNotThrow(() -> validation.validate(Move.builder().currentTurn(turn).startPosition(6).build()));
  }

  @Test
  public void shouldNotThrowExceptionWhenStartPositionIsFromPlayerTwoSection() {
    Gameboard gameboard = Gameboard.newGameBoard();
    Turn turn =
            Turn.builder()
                    .playerTwo(Player.PLAYER_TWO)
                    .playerOne(Player.PLAYER_ONE)
                    .playing(Player.PLAYER_TWO)
                    .number(1)
                    .currentBoard(gameboard)
                    .events(List.of())
                    .build();

    assertDoesNotThrow(() -> validation.validate(Move.builder().currentTurn(turn).startPosition(7).build()));
    assertDoesNotThrow(() -> validation.validate(Move.builder().currentTurn(turn).startPosition(8).build()));
    assertDoesNotThrow(() -> validation.validate(Move.builder().currentTurn(turn).startPosition(9).build()));
    assertDoesNotThrow(() -> validation.validate(Move.builder().currentTurn(turn).startPosition(10).build()));
    assertDoesNotThrow(() -> validation.validate(Move.builder().currentTurn(turn).startPosition(11).build()));
    assertDoesNotThrow(() -> validation.validate(Move.builder().currentTurn(turn).startPosition(12).build()));
    assertDoesNotThrow(() -> validation.validate(Move.builder().currentTurn(turn).startPosition(13).build()));
  }

  @Test
  public void shouldThrowIncorrectPlayerSectionExceptionWhenStartPositionIsFromOpponentSection() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int startPosition = 7;
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

    assertThrows(IncorrectPlayerSectionException.class, () -> validation.validate(move));
  }
}
