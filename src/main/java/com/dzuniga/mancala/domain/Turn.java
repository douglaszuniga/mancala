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

@Data
public class Turn {

  private final Integer number;
  private final List<GameEvent> events;
  private final Gameboard currentBoard;
  private final Player playing;
  private final Player playerOne;
  private final Player playerTwo;

  @Builder
  @JsonCreator
  public Turn(
          @JsonProperty("number") Integer number,
          @JsonProperty("events") List<GameEvent> events,
          @JsonProperty("board") Gameboard currentBoard,
          @JsonProperty("playing") Player playing,
          @JsonProperty("playerOne") Player playerOne,
          @JsonProperty("playerTwo") Player playerTwo
  ) {

    Objects.requireNonNull(number, "Turn number must not be null");
    Validate.isTrue(number >= 0, "Turn number must be greater or equals to zero");
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
