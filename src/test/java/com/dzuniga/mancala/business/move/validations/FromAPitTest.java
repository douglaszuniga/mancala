package com.dzuniga.mancala.business.move.validations;

import com.dzuniga.mancala.business.exceptions.IncorrectPitException;
import com.dzuniga.mancala.business.move.postchecks.rules.consequences.GameEndConsequence;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.domain.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FromAPitTest {
  private FromAPit validation;

  @BeforeEach
  void setUp() {
    validation = new FromAPit();
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenMoveIsNull() {
    assertThrows(NullPointerException.class, () -> validation.validate(null));
  }

  @Test
  public void shouldNotThrowExceptionWhenStartPositionIsFromAPit() {
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
  public void shouldThrowIncorrectPitExceptionWhenStartPositionIsFromAMancala() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int startPosition = Gameboard.MANCALAS.get(Player.PLAYER_ONE);
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

    assertThrows(IncorrectPitException.class, () -> validation.validate(move));
  }
}