package com.dzuniga.mancala.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/** Data class that contains the information regarding a move */
@Data
public class Move {
  private final Turn currentTurn;
  private final int startPosition;

  @JsonCreator
  @Builder
  public Move(
      @JsonProperty("currentTurn") Turn currentTurn,
      @JsonProperty("startPosition") int startPosition) {

    Objects.requireNonNull(currentTurn, "The current turn must not be null");

    this.currentTurn = currentTurn;
    this.startPosition = startPosition;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("currentTurn", currentTurn)
        .append("startPosition", startPosition)
        .toString();
  }
}
