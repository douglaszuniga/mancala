package com.dzuniga.mancala.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

@Data
public class Player {

  public static final Player PLAYER_ONE = Player.getPlayerFrom(Boolean.TRUE);
  public static final Player PLAYER_TWO = Player.getPlayerFrom(Boolean.FALSE);

  private final Boolean id;

  @JsonCreator
  public Player(@JsonProperty("id") Boolean id) {
    Objects.requireNonNull(id, "Player id must not be null");

    this.id = id;
  }

  public static Player getOppositePlayer(Player currentPlayer) {
    return Player.getPlayerFrom(!currentPlayer.getId());
  }

  private static Player getPlayerFrom(Boolean id) {
    return new Player(id);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", id)
            .toString();
  }
}
