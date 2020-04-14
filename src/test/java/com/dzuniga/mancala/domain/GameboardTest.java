package com.dzuniga.mancala.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameboardTest {

  @Test
  void shouldReturnFalseWhenPlayablePitSectionIsNOTEmpty() {
    Section section = Gameboard.PIT_SECTIONS.get(Player.PLAYER_ONE);
    Gameboard gameboard = Gameboard.newGameBoard();
    boolean actual = gameboard.isPitSectionEmpty(section);

    assertFalse(actual);
  }

  @Test
  void shouldReturnTrueWhenPlayablePitSectionIsEmpty() {
    Section section = Gameboard.PIT_SECTIONS.get(Player.PLAYER_TWO);

    Gameboard gameboard = new Gameboard("id", new int[] {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1});
    boolean actual = gameboard.isPitSectionEmpty(section);

    assertTrue(actual);
  }
}
