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
            List.of(GameEvent.NEW_GAME_STARTED, GameEvent.EXTRA_TURN_GAINED),
            List.of(GameEvent.GAME_ENDED, GameEvent.TIED));

    Assertions.assertEquals(
        List.of(
            GameEvent.NEW_GAME_STARTED,
            GameEvent.EXTRA_TURN_GAINED,
            GameEvent.GAME_ENDED,
            GameEvent.TIED),
        actual);
  }
}
