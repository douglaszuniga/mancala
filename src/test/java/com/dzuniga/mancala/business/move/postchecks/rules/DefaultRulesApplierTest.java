package com.dzuniga.mancala.business.move.postchecks.rules;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.business.move.postchecks.DefaultRulesApplier;
import com.dzuniga.mancala.business.move.postchecks.RulesApplier;
import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.domain.Turn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

@ExtendWith(MockitoExtension.class)
public class DefaultRulesApplierTest {

  private Rule extraTurnRule;
  private Rule capturePebblesRule;
  private Rule gameEndRule;

  private RulesApplier applier;

  @BeforeEach
  public void setUp() {

    extraTurnRule = mock(Rule.class, withSettings().lenient());
    when(extraTurnRule.orRun(any(Rule.class))).thenCallRealMethod();
    capturePebblesRule = mock(Rule.class, withSettings().lenient());
    gameEndRule = mock(Rule.class, withSettings().lenient());

    applyDefaultBehaviourToRule(extraTurnRule);
    applyDefaultBehaviourToRule(capturePebblesRule);
    applyDefaultBehaviourToRule(gameEndRule);

    applier = new DefaultRulesApplier(extraTurnRule, capturePebblesRule, gameEndRule);
  }

  private void applyDefaultBehaviourToRule(Rule rule) {
    applyDefaultBehaviourToRule(rule, null);
  }

  private void applyDefaultBehaviourToRule(Rule rule, RuleResult returnValue) {
    when(rule.runRule(any(MoveResult.class), any(Player.class)))
        .thenReturn(Optional.ofNullable(returnValue));
  }

  @Test
  public void shouldReturnDefaultRuleResultWhenNoRuleWasTriggered() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int lastDropPosition = 0;
    Turn turn =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_ONE)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();

    RuleResult actual =
        applier.apply(
            Move.builder().startPosition(lastDropPosition).currentTurn(turn).build(),
            MoveResult.of(gameboard, lastDropPosition, List.of()));

    // default actual means nothing changed
    Assertions.assertArrayEquals(gameboard.getGameboard(), actual.getGameboard().getGameboard());
    Assertions.assertTrue(actual.getGameEvents().isEmpty());
    Assertions.assertEquals(Player.getOppositePlayer(turn.getPlaying()), actual.getNextPlayer());

    verify(extraTurnRule).runRule(any(MoveResult.class), any(Player.class));
  }

  @Test
  public void shouldReturnExtraTurnResultWhenCapturePebblesAndEndGameWhereNotTriggered() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int lastDropPosition = 0;
    Turn turn =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_ONE)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();

    RuleResult expected =
        RuleResult.of(gameboard, List.of(GameEvent.extraTurnGained), turn.getPlaying());

    applyDefaultBehaviourToRule(extraTurnRule, expected);

    RuleResult actual =
        applier.apply(
            Move.builder().startPosition(lastDropPosition).currentTurn(turn).build(),
            MoveResult.of(gameboard, lastDropPosition, List.of()));

    Assertions.assertEquals(expected, actual);

    verify(extraTurnRule).runRule(any(MoveResult.class), any(Player.class));
    verify(capturePebblesRule, never()).runRule(any(MoveResult.class), any(Player.class));
    verify(gameEndRule).runRule(any(MoveResult.class), any(Player.class));
  }

  @Test
  public void shouldReturnCapturePebblesResultWhenExtraTurnAndGameEndRulesWereNotTriggered() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int lastDropPosition = 0;
    Turn turn =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_ONE)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();

    RuleResult expected =
        RuleResult.of(
            gameboard,
            List.of(GameEvent.pebblesCaptured),
            Player.getOppositePlayer(turn.getPlaying()));

    applyDefaultBehaviourToRule(capturePebblesRule, expected);

    RuleResult actual =
        applier.apply(
            Move.builder().startPosition(lastDropPosition).currentTurn(turn).build(),
            MoveResult.of(gameboard, lastDropPosition, List.of()));

    Assertions.assertEquals(expected, actual);

    verify(extraTurnRule).runRule(any(MoveResult.class), any(Player.class));
    verify(capturePebblesRule).runRule(any(MoveResult.class), any(Player.class));
    verify(gameEndRule).runRule(any(MoveResult.class), any(Player.class));
  }

  @Test
  public void shouldReturnGameEndResultWhenExtraTurnAndCapturePebblesRulesWereNotTriggered() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int lastDropPosition = 0;
    Turn turn =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_ONE)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();

    RuleResult expected =
        RuleResult.of(gameboard, List.of(GameEvent.gameEnded, GameEvent.tied), turn.getPlaying());

    applyDefaultBehaviourToRule(gameEndRule, expected);

    RuleResult actual =
        applier.apply(
            Move.builder().startPosition(lastDropPosition).currentTurn(turn).build(),
            MoveResult.of(gameboard, lastDropPosition, List.of()));

    Assertions.assertEquals(expected, actual);

    verify(extraTurnRule).runRule(any(MoveResult.class), any(Player.class));
    verify(capturePebblesRule).runRule(any(MoveResult.class), any(Player.class));
    verify(gameEndRule).runRule(any(MoveResult.class), any(Player.class));
  }

  @Test
  public void shouldReturnGameEndResultWhenCapturePebblesRuleWasTriggered() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int lastDropPosition = 0;
    Turn turn =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_ONE)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();

    RuleResult capturePebblesResult =
        RuleResult.of(gameboard, List.of(GameEvent.pebblesCaptured), turn.getPlaying());

    applyDefaultBehaviourToRule(capturePebblesRule, capturePebblesResult);

    RuleResult expected =
        RuleResult.of(
            gameboard,
            List.of(GameEvent.pebblesCaptured, GameEvent.gameEnded, GameEvent.tied),
            turn.getPlaying());

    applyDefaultBehaviourToRule(gameEndRule, expected);

    RuleResult actual =
        applier.apply(
            Move.builder().startPosition(lastDropPosition).currentTurn(turn).build(),
            MoveResult.of(gameboard, lastDropPosition, List.of()));

    Assertions.assertEquals(expected, actual);

    verify(extraTurnRule).runRule(any(MoveResult.class), any(Player.class));
    verify(capturePebblesRule).runRule(any(MoveResult.class), any(Player.class));
    verify(gameEndRule).runRule(any(MoveResult.class), any(Player.class));
  }
}
