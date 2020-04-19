package com.dzuniga.mancala.business.move.model;

import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * Internal model used to collect the information after applying a {@link com.dzuniga.mancala.business.move.postchecks.rules.Rule}
 */
@Data
public class RuleResult {

  private final Gameboard gameboard;
  private final List<GameEvent> gameEvents;
  private final Player nextPlayer;

  private RuleResult(Gameboard gameboard, List<GameEvent> gameEvents, Player nextPlayer) {

    Objects.requireNonNull(gameboard, "The gameboard must be null");
    Objects.requireNonNull(gameEvents, "The game events list must be null");
    Objects.requireNonNull(nextPlayer, "The next player must be null");

    this.gameboard = gameboard;
    this.gameEvents = gameEvents;
    this.nextPlayer = nextPlayer;
  }

  /**
   * Factory method to get a new RuleResult
   * @param gameboard the current gameboard state
   * @param gameEvents list of events
   * @param nextPlayer Next playing player
   * @return RuleResult object
   */
  public static RuleResult of(Gameboard gameboard, List<GameEvent> gameEvents, Player nextPlayer) {
    return new RuleResult(gameboard, gameEvents, nextPlayer);
  }
}
