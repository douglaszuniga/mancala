package com.dzuniga.mancala.domain;

import com.dzuniga.mancala.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTest {

  @Test
  public void testEquality() {
    Player alpha = Player.PLAYER_ONE;
    Player beta = Player.PLAYER_ONE;
    Player gama = Player.PLAYER_ONE;
    Player delta = Player.PLAYER_TWO;

    TestUtils.assertEqualsContract(alpha, beta, gama, delta);
  }

  @Test
  public void shouldReturnNullPointerExceptionWhenCurrentPlayerIsNull() {
    Assertions.assertThrows(NullPointerException.class, () -> Player.getOppositePlayer(null));
  }

  @Test
  public void shouldReturnPlayerOneWhenCurrentPlayerIsTwo() {
    Player expected = Player.PLAYER_ONE;

    Player actual = Player.getOppositePlayer(Player.PLAYER_TWO);

    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void shouldReturnPlayerTwoWhenCurrentPlayerIsOne() {
    Player expected = Player.PLAYER_TWO;

    Player actual = Player.getOppositePlayer(Player.PLAYER_ONE);

    Assertions.assertEquals(expected, actual);
  }
}
