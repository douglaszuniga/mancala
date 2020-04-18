package com.dzuniga.mancala.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Data
@Slf4j
public class Gameboard {

  public static final Map<Player, Section> PIT_SECTIONS =
      Map.of(
          Player.PLAYER_ONE, Section.sectionOf(0, 6), Player.PLAYER_TWO, Section.sectionOf(7, 13));
  public static final Map<Player, Integer> MANCALAS =
      Map.of(Player.PLAYER_ONE, 6, Player.PLAYER_TWO, 13);
  // a more elegant way would be calculating the opposite pit based on the current pit position
  public static final Map<Integer, Integer> OPPOSITE_PIT =
      Map.ofEntries(
          Map.entry(0, 12),
          Map.entry(1, 11),
          Map.entry(2, 10),
          Map.entry(3, 9),
          Map.entry(4, 8),
          Map.entry(5, 7),
          Map.entry(12, 0),
          Map.entry(11, 1),
          Map.entry(10, 2),
          Map.entry(9, 3),
          Map.entry(8, 4),
          Map.entry(7, 5));

  public static final int SIZE = 14;
  private static final int NUMBER_OF_PEBBLES_PER_PIT = 6;
  private static final int NUMBER_OF_PEBBLES_PER_MANCALA = 0;

  private final String id;
  private final int[] gameboard;

  @JsonCreator
  public Gameboard(@JsonProperty("id") String id, @JsonProperty("gameboard") int[] gameboard) {
    Validate.isTrue(StringUtils.isNotBlank(id), "Gameboard Id must not be null or blank");
    Objects.requireNonNull(gameboard, "The gameboard must not be null");
    Validate.isTrue(
        Gameboard.SIZE == gameboard.length,
        "The Gameboard size must contain 12 pits and two mancalas");

    this.id = id;
    this.gameboard = gameboard;
  }

  private Gameboard() {
    id = UUID.randomUUID().toString();
    this.gameboard = new int[Gameboard.SIZE];

    init();
  }

  private void init() {
    int counter = 0;
    while (counter < Gameboard.SIZE) {
      if (MANCALAS.containsValue(counter)) {
        gameboard[counter] = Gameboard.NUMBER_OF_PEBBLES_PER_MANCALA;
      } else {
        gameboard[counter] = Gameboard.NUMBER_OF_PEBBLES_PER_PIT;
      }
      counter++;
    }
  }

  public int getNumberOfPebblesInPit(int pitPosition) {
    Validate.isTrue(pitPosition >= 0, "Pit position must be greater equals to zero");
    Validate.isTrue(pitPosition < SIZE, "Pit position must be less than board size");

    return gameboard[pitPosition];
  }

  public boolean isPitSectionEmpty(Section section) {
    Objects.requireNonNull(section, "Section must not be null");

    int[] boardSection = Arrays.copyOfRange(gameboard, section.getLow(), section.getHigh());

    return Arrays.stream(boardSection).sum() == 0;
  }

  public static Gameboard newGameBoard() {
    return new Gameboard();
  }

  public static Gameboard collectPebblesIntoMancalas(Gameboard currentBoard) {
    Objects.requireNonNull(currentBoard, "Input gameboard must not be null");

    log.debug("BEFORE collecting the pebbles, gameboard:[{}]", currentBoard.gameboard);

    int[] output = Arrays.copyOf(currentBoard.getGameboard(), Gameboard.SIZE);
    int sumBySection = 0;
    for (int i = 0; i < SIZE; i++) {
      if (Gameboard.MANCALAS.containsValue(i)) {
        output[i] += sumBySection;
        sumBySection = 0;
      } else {
        sumBySection += output[i];
        output[i] = 0;
      }
    }

    log.debug("AFTER collecting the pebbles, gameboard:[{}]", output);
    return new Gameboard(currentBoard.getId(), output);
  }

  /**
   * Collects the current pit pebbles plus the opposite pit pebbles into the current player mancala
   *
   * @param currentBoard contains the board state so far
   * @param pitPosition pit were the last pebble was dropped, used to get the opposite pit
   * @param currentPlayer player currently playing
   * @return a new gameboard with the pebbles captured
   */
  public static Gameboard captureOppositePitPebbles(Gameboard currentBoard, int pitPosition, Player currentPlayer) {
    Objects.requireNonNull(currentBoard, "Input gameboard must not be null");
    Objects.requireNonNull(currentPlayer, "Current player must not be null");
    Validate.isTrue(pitPosition >= 0, "Pit position must be greater or equals to zero");
    Validate.isTrue(pitPosition < Gameboard.SIZE, "Pit position must less than board size");

    log.debug("BEFORE capturing the pebbles, gameboard:[{}]", currentBoard.gameboard);

    int[] output = Arrays.copyOf(currentBoard.getGameboard(), Gameboard.SIZE);
    // - capture pit and opposite pit pebbles into the player's mancala
    output[Gameboard.MANCALAS.get(currentPlayer)] += output[pitPosition] + output[OPPOSITE_PIT.get(pitPosition)];
    // - updating the pebble number in each pit involved down to zero
    output[pitPosition] = 0;
    output[OPPOSITE_PIT.get(pitPosition)] = 0;

    log.debug("AFTER capturing the pebbles, gameboard:[{}]", output);

    return new Gameboard(currentBoard.getId(), output);
  }
}
