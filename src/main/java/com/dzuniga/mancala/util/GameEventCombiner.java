package com.dzuniga.mancala.util;

import com.dzuniga.mancala.domain.GameEvent;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class GameEventCombiner {

  public static List<GameEvent> combine(List<GameEvent> accumulatedSoFar, List<GameEvent> justProduced) {

    Objects.requireNonNull(
        accumulatedSoFar, "The list of events accumulated so far must not be null");
    Objects.requireNonNull(justProduced, "The list of events just produced must not be null");

    return Stream.concat(accumulatedSoFar.stream(), justProduced.stream())
        .collect(Collectors.toUnmodifiableList());
  }
}
