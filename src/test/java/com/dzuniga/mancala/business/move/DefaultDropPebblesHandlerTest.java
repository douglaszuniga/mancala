package com.dzuniga.mancala.business.move;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.domain.Turn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultDropPebblesHandlerTest {

  private DefaultDropPebblesHandler handler;

  @BeforeEach
  public void setUp() {
    handler = new DefaultDropPebblesHandler();
  }

  @Test
  public void shouldReturnNullPointerExceptionWhenMoveIsNull() {
    Assertions.assertThrows(NullPointerException.class, () -> handler.apply(null));
  }

  @Test
  public void shouldNotDropAnyPebbleWhenStartPositionIsEmpty() {
    Gameboard gameboard =
        new Gameboard(
            "id",
            new int[] {
              0, 7, 7, 7, 7, 7, 1,
              6, 6, 6, 6, 6, 6, 0
            });
    Turn turn =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_ONE)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();

    MoveResult result = handler.apply(Move.builder().startPosition(0).currentTurn(turn).build());

    int[] expectedBoard = gameboard.getGameboard();
    int expectedLastPosition = 0;

    assertArrayEquals(expectedBoard, result.getGameboard().getGameboard());
    assertEquals(expectedLastPosition, result.getLastDropPosition(), "incorrect lastPosition");
    assertTrue(result.getGameEvents().isEmpty());
  }

  @Test
  public void shouldNotDropPebbleInOpponentMancalaWhenMovingFromOpponentSectionToCurrentPlayerSection() {
    Gameboard gameboard =
        new Gameboard(
            "id",
            new int[] {
              0, 7, 7, 7, 7, 10, 1,
              6, 6, 6, 6, 6, 3, 0
            });
    Turn turn =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_ONE)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();

    int startPosition = 5;

    MoveResult result = handler.apply(Move.builder().startPosition(startPosition).currentTurn(turn).build());

    int[] expectedBoard =
        new int[] {
          1, 8, 8, 7, 7, 0, 2,
          7, 7, 7, 7, 7, 4, 0
        };
    int expectedLastPosition = 2;

    assertEquals(
            result.getGameboard().getGameboard()[startPosition],
            0,
            "Start pit should be empty");
    assertEquals(
        result.getGameboard().getGameboard()[Gameboard.MANCALAS.get(turn.getPlaying())],
        2,
        "Current Player's Mancala should contain 2 pebble");
    assertEquals(
        result.getGameboard()
            .getGameboard()[Gameboard.MANCALAS.get(Player.getOppositePlayer(turn.getPlaying()))],
        0,
        "Opposite Player's Mancala should be empty");
    assertArrayEquals(expectedBoard, result.getGameboard().getGameboard());
    assertEquals(expectedLastPosition, result.getLastDropPosition(), "incorrect lastPosition");
    assertTrue(result.getGameEvents().isEmpty());
  }

  @Test
  public void shouldDropPebblesAndFinishInPit4WhenPlayerTwoIsPlayingAndStartPositionIs12() {
    Gameboard gameboard =
        new Gameboard(
            "id",
            new int[] {
              0, 7, 7, 7, 7, 7, 1,
              6, 6, 6, 6, 6, 6, 0
            });
    Turn turn =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_TWO)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();

    MoveResult result = handler.apply(Move.builder().startPosition(12).currentTurn(turn).build());

    int[] expectedBoard =
        new int[] {
          1, 8, 8, 8, 8, 7, 1,
          6, 6, 6, 6, 6, 0, 1
        };
    int expectedLastPosition = 4;

    assertArrayEquals(expectedBoard, result.getGameboard().getGameboard());
    assertEquals(expectedLastPosition, result.getLastDropPosition(), "incorrect lastPosition");
    assertTrue(result.getGameEvents().isEmpty());
  }
}
