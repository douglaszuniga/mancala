package com.dzuniga.mancala.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Objects;

/** Data class containing the information regarding a turn */
@Data
public class Turn {

  private final int number;
  private final List<GameEvent> events;
  private final Gameboard currentBoard;
  private final Player playing;
  private final Player playerOne;
  private final Player playerTwo;

  @Builder
  @JsonCreator
  public Turn(
      @JsonProperty("number") int number,
      @JsonProperty("events") List<GameEvent> events,
      @JsonProperty("currentBoard") Gameboard currentBoard,
      @JsonProperty("playing") Player playing,
      @JsonProperty("playerOne") Player playerOne,
      @JsonProperty("playerTwo") Player playerTwo) {

    Validate.isTrue(number >= 0, "Turn number must be greater or equals to zero");
    Objects.requireNonNull(playing, "The current player must not be null");
    Objects.requireNonNull(playerOne, "Player One must not be null");
    Objects.requireNonNull(playerTwo, "Player Two must not be null");
    Objects.requireNonNull(events, "Events must not be null");
    Objects.requireNonNull(currentBoard, "Gameboard must not be null");

    this.number = number;
    this.events = events;
    this.currentBoard = currentBoard;
    this.playing = playing;
    this.playerOne = playerOne;
    this.playerTwo = playerTwo;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("number", number)
        .append("events", events)
        .append("currentBoard", currentBoard)
        .append("playing", playing)
        .append("playerOne", playerOne)
        .append("playerOne", playerTwo)
        .toString();
  }
}
