package com.dzuniga.mancala.domain;

import com.dzuniga.mancala.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TurnTest {
  @Test
  public void testEquality() {
    Gameboard gameboard = Gameboard.newGameBoard();

    Turn alpha =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_ONE)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();
    Turn beta =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_ONE)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();
    Turn gama =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_ONE)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();
    Turn delta =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_TWO)
            .number(2)
            .currentBoard(gameboard)
            .events(List.of())
            .build();

    TestUtils.assertEqualsContract(alpha, beta, gama, delta);
  }

  @Test
  public void shouldReturnNullPointerExceptionWhenEventListIsNull() {
    Assertions.assertThrows(
        NullPointerException.class,
        () ->
            Turn.builder()
                .playerTwo(Player.PLAYER_TWO)
                .playerOne(Player.PLAYER_ONE)
                .playing(Player.PLAYER_TWO)
                .number(2)
                .currentBoard(Gameboard.newGameBoard())
                .events(null)
                .build());
  }

  @Test
  public void shouldReturnIllegalArgumentExceptionWhenNumberIsNull() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            Turn.builder()
                .playerTwo(Player.PLAYER_TWO)
                .playerOne(Player.PLAYER_ONE)
                .playing(Player.PLAYER_TWO)
                .number(-1)
                .currentBoard(Gameboard.newGameBoard())
                .events(List.of())
                .build());
  }

  @Test
  public void shouldReturnNullPointerExceptionWhenBoardIsNull() {
    Assertions.assertThrows(
        NullPointerException.class,
        () ->
            Turn.builder()
                .playerTwo(Player.PLAYER_TWO)
                .playerOne(Player.PLAYER_ONE)
                .playing(Player.PLAYER_TWO)
                .number(2)
                .currentBoard(null)
                .events(List.of())
                .build());
  }

  @Test
  public void shouldReturnNullPointerExceptionWhenPlayingIsNull() {
    Assertions.assertThrows(
        NullPointerException.class,
        () ->
            Turn.builder()
                .playerTwo(Player.PLAYER_TWO)
                .playerOne(Player.PLAYER_ONE)
                .playing(null)
                .number(2)
                .currentBoard(Gameboard.newGameBoard())
                .events(List.of())
                .build());
  }

  @Test
  public void shouldReturnNullPointerExceptionWhenPlayerTwoIsNull() {
    Assertions.assertThrows(
        NullPointerException.class,
        () ->
            Turn.builder()
                .playerTwo(null)
                .playerOne(Player.PLAYER_ONE)
                .playing(Player.PLAYER_TWO)
                .number(2)
                .currentBoard(Gameboard.newGameBoard())
                .events(List.of())
                .build());
  }

  @Test
  public void shouldReturnNullPointerExceptionWhenPlayerOneIsNull() {
    Assertions.assertThrows(
        NullPointerException.class,
        () ->
            Turn.builder()
                .playerTwo(Player.PLAYER_TWO)
                .playerOne(null)
                .playing(Player.PLAYER_TWO)
                .number(2)
                .currentBoard(Gameboard.newGameBoard())
                .events(List.of())
                .build());
  }
}
