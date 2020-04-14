package com.dzuniga.mancala.business.move.postchecks.rules;

import com.dzuniga.mancala.business.move.postchecks.rules.conditions.ExtraTurnCondition;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultPostCheckConditionTest {

  private ExtraTurnCondition rule;

  @BeforeEach
  public void setUp() {
    rule = new ExtraTurnCondition();
  }

  @Test
  public void shouldReturnFalseWhenLastDropPositionIsNotAMancala() {
    int lastDropPosition = 1;

    boolean actual =
        rule.conditionMet(Gameboard.newGameBoard(), lastDropPosition, Player.PLAYER_ONE);

    assertFalse(actual);
  }

  @Test
  public void shouldReturnTrueWhenLastDropPositionIsAMancala() {
    int lastDropPosition = Gameboard.MANCALAS.getOrDefault(Player.PLAYER_ONE, 13);

    boolean actual =
            rule.conditionMet(Gameboard.newGameBoard(), lastDropPosition, Player.PLAYER_ONE);

    assertTrue(actual);
  }
}
