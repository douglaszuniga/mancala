package com.dzuniga.mancala.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTest {

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
