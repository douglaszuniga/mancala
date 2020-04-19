package com.dzuniga.mancala.business.move.model;

import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Gameboard;
import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Objects;

/**
 * Internal model used to collect the information of the game after applying a {@link com.dzuniga.mancala.domain.Move}
 */
@Data
public class MoveResult {
  private final Gameboard gameboard;
  private final int lastDropPosition;
  private final List<GameEvent> gameEvents;

  private MoveResult(Gameboard gameboard, int lastDropPosition, List<GameEvent> gameEvents) {
    Objects.requireNonNull(gameboard, "The gameboard must not be null");
    Objects.requireNonNull(gameEvents, "The game event list must not be null");
    Validate.isTrue(lastDropPosition >= 0, "Last drop position must be greater than zero");
    Validate.isTrue(lastDropPosition < Gameboard.SIZE, "Last drop position must be less than board size");

    this.gameboard = gameboard;
    this.lastDropPosition = lastDropPosition;
    this.gameEvents = gameEvents;
  }

  /**
   * Factory method that returns a new MoveResult
   * @param gameboard the current gameboard
   * @param lastDropPosition last position in the board where a pebble was dropped
   * @param gameEvents list of events
   * @return MoveResult object
   */
  public static MoveResult of(Gameboard gameboard, int lastDropPosition, List<GameEvent> gameEvents) {
    return new MoveResult(gameboard, lastDropPosition, gameEvents);
  }
}
