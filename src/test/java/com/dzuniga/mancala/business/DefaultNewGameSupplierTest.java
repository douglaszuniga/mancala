package com.dzuniga.mancala.business;

import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.domain.Turn;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultNewGameSupplierTest {

  private DefaultNewGameSupplier supplier = new DefaultNewGameSupplier();

  @Test
  public void shouldReturnANewGameWhenSupplierIsCalled() {
    Turn expected =
        Turn.builder()
            .number(0)
            .currentBoard(Gameboard.newGameBoard())
            .playerOne(Player.PLAYER_ONE)
            .playerTwo(Player.PLAYER_TWO)
            .events(List.of(GameEvent.newGameStarted))
            .playing(Player.PLAYER_ONE)
            .build();

    Turn actual = supplier.get();

    assertEquals(expected.getNumber(), actual.getNumber());
    assertEquals(expected.getPlaying(), actual.getPlaying());
    assertEquals(expected.getPlayerOne(), actual.getPlayerOne());
    assertEquals(expected.getPlayerTwo(), actual.getPlayerTwo());
    assertEquals(expected.getEvents(), actual.getEvents());
    assertArrayEquals(
        expected.getCurrentBoard().getGameboard(), actual.getCurrentBoard().getGameboard());
  }
}
