package com.dzuniga.mancala.business.move.postchecks.rules;

import com.dzuniga.mancala.business.move.postchecks.rules.conditions.CapturePebblesCondition;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CapturePebblesConditionTest {

  private CapturePebblesCondition rule;

  @BeforeEach
  public void setUp() {
    rule = new CapturePebblesCondition();
  }

  @Test
  public void shouldReturnFalseWhenNumberOfPebblesInLastDropPositionIsGreaterThanOne() {
    Gameboard gameboard = Gameboard.newGameBoard();
    boolean actual = rule.conditionMet(gameboard, 0, Player.PLAYER_TWO);
    assertFalse(actual);
  }

  @Test
  public void shouldReturnFalseWhenLastDropPositionIsAMancala() {
    Gameboard gameboard = Gameboard.newGameBoard();
    boolean actual = rule.conditionMet(gameboard, Gameboard.MANCALAS.get(Player.PLAYER_ONE), Player.PLAYER_TWO);
    assertFalse(actual);
  }

  @Test
  public void shouldReturnFalseWhenLastDropPositionIsNotInsideCurrentPlayerSection() {
    Gameboard gameboard = new Gameboard("id", new int[] {1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1});
    boolean actual = rule.conditionMet(gameboard, 0, Player.PLAYER_TWO);
    assertFalse(actual);
  }

  @Test
  public void shouldReturnTrueWhenNumberOfPebblesInLastDropPositionIsOne() {
    Gameboard gameboard = new Gameboard("id", new int[] {1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1});
    boolean actual = rule.conditionMet(gameboard, 0, Player.PLAYER_ONE);
    assertTrue(actual);
  }
}