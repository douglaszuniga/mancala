package com.dzuniga.mancala.business.move.postchecks.rules;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.business.move.postchecks.rules.conditions.Condition;
import com.dzuniga.mancala.business.move.postchecks.rules.consequences.Consequence;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RuleTest {

  private Condition condition;
  private Consequence consequence;

  private Rule rule;

  @BeforeEach
  public void setUp() {
    condition = mock(Condition.class);
    consequence = mock(Consequence.class);
    rule = Rule.of(condition, consequence);
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenMoveResultIsNull() {
    assertThrows(NullPointerException.class, () -> rule.runRule(null, Player.PLAYER_ONE));
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenPlayerIsNull() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int lastDropPosition = 0;

    assertThrows(
        NullPointerException.class,
        () -> rule.runRule(MoveResult.of(gameboard, lastDropPosition, List.of()), null));
  }

  @Test
  public void shouldReturnEmptyOptionalWhenConditionWasNotMet() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int lastDropPosition = 0;

    when(condition.test(any(MoveResult.class), any(Player.class))).thenReturn(false);

    Optional<RuleResult> expected =
        rule.runRule(MoveResult.of(gameboard, lastDropPosition, List.of()), Player.PLAYER_ONE);

    assertTrue(expected.isEmpty());
  }

  @Test
  public void shouldReturnMoveResultWhenConditionWasMet() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int lastDropPosition = 0;

    MoveResult moveResult = MoveResult.of(gameboard, lastDropPosition, List.of());

    RuleResult expectedRuleResult = RuleResult.of(gameboard, List.of(), Player.PLAYER_TWO);

    when(condition.test(any(MoveResult.class), any(Player.class))).thenReturn(true);
    when(consequence.apply(any(MoveResult.class), any(Player.class))).thenReturn(expectedRuleResult);

    Optional<RuleResult> actual = rule.runRule(moveResult, Player.PLAYER_ONE);

    assertFalse(actual.isEmpty());
    assertEquals(expectedRuleResult, actual.get());
  }
}
