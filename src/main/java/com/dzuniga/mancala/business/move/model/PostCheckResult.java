package com.dzuniga.mancala.business.move.model;

import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class PostCheckResult {

  private final Gameboard gameboard;
  private final List<GameEvent> gameEvents;
  private final Player nextPlayer;

  private PostCheckResult(Gameboard gameboard, List<GameEvent> gameEvents, Player nextPlayer) {

    Objects.requireNonNull(gameboard, "The gameboard must be null");
    Objects.requireNonNull(gameEvents, "The game events list must be null");
    Objects.requireNonNull(nextPlayer, "The next player must be null");

    this.gameboard = gameboard;
    this.gameEvents = gameEvents;
    this.nextPlayer = nextPlayer;
  }

  public static PostCheckResult of(
      Gameboard gameboard, List<GameEvent> gameEvents, Player nextPlayer) {
    return new PostCheckResult(gameboard, gameEvents, nextPlayer);
  }
}
