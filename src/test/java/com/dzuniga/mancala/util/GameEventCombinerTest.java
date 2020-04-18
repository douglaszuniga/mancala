package com.dzuniga.mancala.util;

import com.dzuniga.mancala.domain.GameEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GameEventCombinerTest {

  @Test
  public void shouldReturnNullPointerExceptionWhenFirstListIsNull() {
    Assertions.assertThrows(
        NullPointerException.class, () -> GameEventCombiner.combine(null, List.of()));
  }

  @Test
  public void shouldReturnNullPointerExceptionWhenSecondListIsNull() {
    Assertions.assertThrows(
        NullPointerException.class, () -> GameEventCombiner.combine(List.of(), null));
  }

  @Test
  public void shouldReturnNewListWhenBothListAreProvided() {
    List<GameEvent> actual =
        GameEventCombiner.combine(
            List.of(GameEvent.newGameStarted, GameEvent.extraTurnGained),
            List.of(GameEvent.gameEnded, GameEvent.tied));

    Assertions.assertEquals(
        List.of(
            GameEvent.newGameStarted,
            GameEvent.extraTurnGained,
            GameEvent.gameEnded,
            GameEvent.tied),
        actual);
  }
}
