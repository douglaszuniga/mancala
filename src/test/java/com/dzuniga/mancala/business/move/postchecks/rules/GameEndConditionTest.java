package com.dzuniga.mancala.business.move.postchecks.rules;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.postchecks.rules.conditions.GameEndCondition;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameEndConditionTest {

  private GameEndCondition rule;

  @BeforeEach
  public void setUp() {
    rule = new GameEndCondition();
  }

  @Test
  public void shouldReturnFalseWhenGameHasJustStarted() {
    Gameboard gameboard = Gameboard.newGameBoard();
    boolean actual = rule.conditionMet(MoveResult.of(gameboard, 0, List.of()), Player.PLAYER_TWO);
    assertFalse(actual);
  }

  @Test
  public void shouldReturnTrueWhenGameHasOnePitSectionWithoutPebbles() {
    Gameboard gameboard = new Gameboard("id", new int[] {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1});
    boolean actual = rule.conditionMet(MoveResult.of(gameboard, 0, List.of()), Player.PLAYER_TWO);
    assertTrue(actual);
  }
}
