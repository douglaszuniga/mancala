package com.dzuniga.mancala.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * Data class that contains the player information and id
 */
@Data
public class Player {

  public static final Player PLAYER_ONE = Player.from(Boolean.TRUE);
  public static final Player PLAYER_TWO = Player.from(Boolean.FALSE);

  private final Boolean id;

  @JsonCreator
  public Player(@JsonProperty("id") Boolean id) {
    Objects.requireNonNull(id, "Player id must not be null");

    this.id = id;
  }

  /**
   * handy function that will get the opposite player based on current player
   * @param currentPlayer current player
   * @return opposite player
   */
  public static Player getOppositePlayer(Player currentPlayer) {
    Objects.requireNonNull(currentPlayer, "The current player must not be null");

    return Player.from(!currentPlayer.getId());
  }

  /**
   * Factory method to get a new player
   * @param id id of the player
   * @return Player object
   */
  private static Player from(Boolean id) {
    return new Player(id);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", id)
            .toString();
  }
}
