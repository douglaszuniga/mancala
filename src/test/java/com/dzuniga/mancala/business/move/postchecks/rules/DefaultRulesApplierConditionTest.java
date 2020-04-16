package com.dzuniga.mancala.business.move.postchecks.rules;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.postchecks.rules.conditions.ExtraTurnCondition;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultRulesApplierConditionTest {

  private ExtraTurnCondition rule;

  @BeforeEach
  public void setUp() {
    rule = new ExtraTurnCondition();
  }

  @Test
  public void shouldReturnFalseWhenLastDropPositionIsNotAMancala() {
    int lastDropPosition = 1;

    boolean actual =
        rule.conditionMet(
            MoveResult.of(Gameboard.newGameBoard(), lastDropPosition, List.of()), Player.PLAYER_ONE);

    assertFalse(actual);
  }

  @Test
  public void shouldReturnTrueWhenLastDropPositionIsAMancala() {
    int lastDropPosition = Gameboard.MANCALAS.getOrDefault(Player.PLAYER_ONE, 13);

    boolean actual =
        rule.conditionMet(
            MoveResult.of(Gameboard.newGameBoard(), lastDropPosition, List.of()), Player.PLAYER_ONE);

    assertTrue(actual);
  }
}
