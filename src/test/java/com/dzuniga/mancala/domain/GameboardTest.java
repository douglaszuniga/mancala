package com.dzuniga.mancala.domain;

import com.dzuniga.mancala.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameboardTest {

  @Test
  public void testEquality() {
    Gameboard board = Gameboard.newGameBoard();

    Gameboard alpha = board;
    Gameboard beta = board;
    Gameboard gama = board;
    Gameboard delta = Gameboard.newGameBoard();

    TestUtils.assertEqualsContract(alpha, beta, gama, delta);
  }

  @Test
  public void shouldReturnNullPointerExceptionWhenGameBoardIsNull() {
    assertThrows(NullPointerException.class, () -> new Gameboard("id", null));
  }

  @Test
  public void shouldReturnIllegalArgumentExceptionWhenIdIsBlank() {
    assertThrows(IllegalArgumentException.class, () -> new Gameboard("   ", new int[] {0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 1, 6, 0}));
  }

  @Test
  public void shouldReturnIllegalArgumentExceptionWhenGameboardSizeIsGreaterThan14() {
    assertThrows(IllegalArgumentException.class, () -> new Gameboard("id", new int[] {0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 1, 6, 0, 1, 6, 0}));
  }

  /* isPitSectionEmpty TESTs */
  @Test
  public void isPitSectionEmptyShouldReturnFalseWhenPlayablePitSectionIsNOTEmpty() {
    Section section = Gameboard.PIT_SECTIONS.get(Player.PLAYER_ONE);
    Gameboard gameboard = Gameboard.newGameBoard();
    boolean actual = gameboard.isPitSectionEmpty(section);

    assertFalse(actual);
  }

  @Test
  public void isPitSectionEmptyShouldReturnTrueWhenPlayablePitSectionIsEmpty() {
    Section section = Gameboard.PIT_SECTIONS.get(Player.PLAYER_TWO);

    Gameboard gameboard = new Gameboard("id", new int[] {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1});
    boolean actual = gameboard.isPitSectionEmpty(section);

    assertTrue(actual);
  }

  @Test
  public void isPitSectionEmptyShouldThrowNullPointerExceptionWhenSectionIsNull() {
    assertThrows(
        NullPointerException.class, () -> Gameboard.newGameBoard().isPitSectionEmpty(null));
  }

  @Test
  public void getNumberOfPebblesInPitShouldReturnIllegalArgumentExceptionWhenPitPositionIsOutTheBoard() {
    Gameboard gameboard = Gameboard.newGameBoard();
    assertThrows(IllegalArgumentException.class, () -> gameboard.getNumberOfPebblesInPit(Gameboard.SIZE));
    assertThrows(IllegalArgumentException.class, () -> gameboard.getNumberOfPebblesInPit(-1));
  }

  @Test
  public void getNumberOfPebblesInPitShouldReturnPebblesWhenPitHasPebbles() {
    Gameboard gameboard = Gameboard.newGameBoard();
    assertEquals(6, gameboard.getNumberOfPebblesInPit(0));
    assertEquals(6, gameboard.getNumberOfPebblesInPit(3));
    assertEquals(6, gameboard.getNumberOfPebblesInPit(7));
    assertEquals(6, gameboard.getNumberOfPebblesInPit(11));
    assertEquals(0, gameboard.getNumberOfPebblesInPit(6));
    assertEquals(0, gameboard.getNumberOfPebblesInPit(13));
  }

  /* captureOppositePitPebbles TESTs */
  @Test
  public void captureOppositePitPebblesShouldCapturePebblesFromCurrentAndOppositePitWhenBothPitsHavePebbles() {
    Gameboard currentBoard =
        new Gameboard("id", new int[] {0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 1, 6, 0});
    int lastDropPosition = 11;
    Player playing = Player.PLAYER_TWO;

    Gameboard expected = new Gameboard("id", new int[] {0, 0, 7, 7, 7, 7, 1, 6, 6, 6, 6, 0, 6, 8});

    Gameboard actual = Gameboard.captureOppositePitPebbles(currentBoard, lastDropPosition, playing);

    assertArrayEquals(expected.getGameboard(), actual.getGameboard());
  }

  @Test
  public void captureOppositePitPebblesShouldThrowNullPointerExceptionWhenCurrentBoardIsNull() {
    assertThrows(NullPointerException.class, () -> Gameboard.captureOppositePitPebbles(null, 0, Player.PLAYER_ONE));
  }

  @Test
  public void captureOppositePitPebblesShouldThrowNullPointerExceptionWhenCurrentPlayerIsNull() {
    assertThrows(NullPointerException.class, () -> Gameboard.captureOppositePitPebbles(Gameboard.newGameBoard(), 0, null));
  }

  @Test
  public void captureOppositePitPebblesShouldThrowIllegalArgumentExceptionWhenLastDropPositionIsBelowZero() {
    assertThrows(IllegalArgumentException.class, () -> Gameboard.captureOppositePitPebbles(Gameboard.newGameBoard(), -1, Player.PLAYER_ONE));
  }

  @Test
  public void captureOppositePitPebblesShouldThrowIllegalArgumentExceptionWhenLastDropPositionIsAboveGameboardSize() {
    assertThrows(IllegalArgumentException.class, () -> Gameboard.captureOppositePitPebbles(Gameboard.newGameBoard(), Gameboard.SIZE, Player.PLAYER_ONE));
  }

  /*
   * collectPebblesIntoMancalas tests
   */
  @Test
  public void collectPebblesIntoMancalasShouldCollectAllPebblesFromEachSectionIntoMancalas() {
    Gameboard currentBoard =
            new Gameboard("id", new int[] {0, 1, 1, 1, 1, 1, 2, 2, 2, 0, 2, 2, 2, 1});

    Gameboard expected = new Gameboard("id", new int[] {0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 11});

    Gameboard actual = Gameboard.collectPebblesIntoMancalas(currentBoard);

    assertArrayEquals(expected.getGameboard(), actual.getGameboard());
  }

  @Test
  public void collectPebblesIntoMancalasShouldThrowNullPointerExceptionWhenCurrentBoardIsNull() {
    assertThrows(NullPointerException.class, () -> Gameboard.collectPebblesIntoMancalas(null));
  }


}
