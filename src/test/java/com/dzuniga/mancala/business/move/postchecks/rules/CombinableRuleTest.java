package com.dzuniga.mancala.business.move.postchecks.rules;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
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
import static org.mockito.Mockito.withSettings;

@ExtendWith(MockitoExtension.class)
public class CombinableRuleTest {

  private CombinableRule ruleOne;
  private CombinableRule ruleTwo;

  @BeforeEach
  public void setUp() {
    ruleOne = mock(CombinableRule.class, withSettings().lenient());
    ruleTwo = mock(CombinableRule.class, withSettings().lenient());
    when(ruleOne.orRun(any(CombinableRule.class))).thenCallRealMethod();
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenOtherRuleIsNull() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int lastDropPosition = 0;
    assertThrows(
        NullPointerException.class,
        () ->
            ruleOne
                .orRun(null)
                .runRule(MoveResult.of(gameboard, lastDropPosition, List.of()), Player.PLAYER_ONE));
  }

  @Test
  public void shouldCallBothRulesWhenOrRunIsUsed() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int lastDropPosition = 0;

    RuleResult expectedRuleResult = RuleResult.of(gameboard, List.of(), Player.PLAYER_TWO);

    when(ruleOne.runRule(any(MoveResult.class), any(Player.class))).thenReturn(Optional.empty());
    when(ruleTwo.runRule(any(MoveResult.class), any(Player.class))).thenReturn(Optional.of(expectedRuleResult));

    Optional<RuleResult> actual = ruleOne
            .orRun(ruleTwo)
            .runRule(MoveResult.of(gameboard, lastDropPosition, List.of()), Player.PLAYER_ONE);

    assertFalse(actual.isEmpty());
    assertEquals(expectedRuleResult, actual.get());
  }
}
