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

/**
 * Data class that contains the state of the board and the pebbles This implementation uses an int
 * array of size 14 from position 0 to 6 is the section for player 1 from position 7 to 13 is the
 * section for player 2 6 and 13 are the mancalas
 */
@Data
@Slf4j
public class Gameboard {

  /** Contains the section of the board per player */
  public static final Map<Player, Section> PIT_SECTIONS =
      Map.of(
          Player.PLAYER_ONE, Section.of(0, 6), Player.PLAYER_TWO, Section.of(7, 13));
  /**
   * Contains the position in the board where each player mancala (big house or big pit) is placed
   */
  public static final Map<Player, Integer> MANCALAS =
      Map.of(Player.PLAYER_ONE, 6, Player.PLAYER_TWO, 13);

  /** contains a one to one relation indicating the opposite pit in the board */
  // a more elegant way would be calculating the opposite pit based on the current pit position
  private static final Map<Integer, Integer> OPPOSITE_PIT =
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

  /** the Board size */
  public static final int SIZE = 14;
  /** Number of pebbles per mancala for a new game */
  private static final int NUMBER_OF_PEBBLES_PER_MANCALA = 0;
  /** Number of pebbles per pit for a new game */
  private static final int NUMBER_OF_PEBBLES_PER_PIT = 6;

  /** Just an id to check in the logs */
  private final String id;
  /** Game state */
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

  /** Initializes the board for a new game */
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

  /**
   * Util function used to get the number of pebbles from a particular position in the board
   *
   * @param pitPosition pit index
   * @return number of pebbles
   */
  public int getNumberOfPebblesInPit(int pitPosition) {
    Validate.isTrue(pitPosition >= 0, "Pit position must be greater equals to zero");
    Validate.isTrue(pitPosition < SIZE, "Pit position must be less than board size");

    return gameboard[pitPosition];
  }

  /**
   * Util function to check is a section of the board (without the mancala) is empty
   *
   * @param section range in the board
   * @return true if the section is empty otherwise false
   */
  public boolean isPitSectionEmpty(Section section) {
    Objects.requireNonNull(section, "Section must not be null");

    int[] boardSection = Arrays.copyOfRange(gameboard, section.getLow(), section.getHigh());

    return Arrays.stream(boardSection).sum() == 0;
  }

  /**
   * Factory Method to get a new GameBoard initialized
   *
   * @return initialized gameboard
   */
  public static Gameboard newGameBoard() {
    return new Gameboard();
  }

  /**
   * Functions that will collect the pebbles of each section (without the mancala) and will add them
   * into its respective mancala
   *
   * <p>Preconditions: {@link Gameboard} must not be null
   *
   * <p>Throws: {@link NullPointerException} when the currentBoard is null
   *
   * @param currentBoard board game
   * @return a new board game, input board is not modified
   */
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
   * <p>Preconditions: {@link Gameboard} and {@link Player} must not be null {@code pitPosition}
   * must be grater or equals than 0 and less than the board size
   *
   * <p>Throws: {@link NullPointerException} when currentBoard or currentPlayer are null {@link
   * IllegalArgumentException} when pitPosition is not inside the board range
   *
   * @param currentBoard contains the board state so far
   * @param pitPosition pit were the last pebble was dropped, used to get the opposite pit
   * @param currentPlayer player currently playing
   * @return a new gameboard with the pebbles captured
   */
  public static Gameboard captureOppositePitPebbles(
      Gameboard currentBoard, int pitPosition, Player currentPlayer) {
    Objects.requireNonNull(currentBoard, "Input gameboard must not be null");
    Objects.requireNonNull(currentPlayer, "Current player must not be null");
    Validate.isTrue(pitPosition >= 0, "Pit position must be greater or equals to zero");
    Validate.isTrue(pitPosition < Gameboard.SIZE, "Pit position must less than board size");

    log.debug("BEFORE capturing the pebbles, gameboard:[{}]", currentBoard.gameboard);

    int[] output = Arrays.copyOf(currentBoard.getGameboard(), Gameboard.SIZE);
    // - capture pit and opposite pit pebbles into the player's mancala
    output[Gameboard.MANCALAS.get(currentPlayer)] +=
        output[pitPosition] + output[OPPOSITE_PIT.get(pitPosition)];
    // - updating the pebble number in each pit involved down to zero
    output[pitPosition] = 0;
    output[OPPOSITE_PIT.get(pitPosition)] = 0;

    log.debug("AFTER capturing the pebbles, gameboard:[{}]", output);

    return new Gameboard(currentBoard.getId(), output);
  }
}
