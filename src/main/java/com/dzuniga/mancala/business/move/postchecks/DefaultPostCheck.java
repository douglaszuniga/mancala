package com.dzuniga.mancala.business.move.postchecks;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.PostCheckResult;
import com.dzuniga.mancala.business.move.postchecks.rules.Rule;
import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Player;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DefaultPostCheck implements PostCheck {

  private final Rule extraTurnRule;
  private final Rule capturePebblesRule;
  private final Rule gameEndRule;

  public DefaultPostCheck(Rule extraTurnRule, Rule capturePebblesRule, Rule gameEndRule) {
    this.extraTurnRule = extraTurnRule;
    this.capturePebblesRule = capturePebblesRule;
    this.gameEndRule = gameEndRule;
  }

  @Override
  public PostCheckResult apply(
      Move appliedMove, MoveResult moveResult, List<GameEvent> gameEvents) {

    Player currentPlayer = appliedMove.getCurrentTurn().getPlaying();
    Gameboard boardAfterMoving = moveResult.getGameboard();

    // - basic result, not rule was triggered
    PostCheckResult result =
        PostCheckResult.of(boardAfterMoving, List.of(), Player.getOppositePlayer(currentPlayer));

    // - extra turn and capture pebbles are mutually exclusive rules
    // - if any of those two are triggered then apply its consequence
    if (wasRuleTriggered(extraTurnRule, moveResult, currentPlayer)) {
      result = applyConsequence(extraTurnRule, moveResult, currentPlayer, boardAfterMoving);
    } else if (wasRuleTriggered(capturePebblesRule, moveResult, currentPlayer)) {
      result = applyConsequence(capturePebblesRule, moveResult, currentPlayer, boardAfterMoving);
    }
    // - finally, check if the game end rule was triggered
    if (wasRuleTriggered(gameEndRule, moveResult, currentPlayer)) {
      PostCheckResult gameEndResult =
          applyConsequence(capturePebblesRule, moveResult, currentPlayer, boardAfterMoving);
      // - take the gameEndResult and combine the game events with the other rules
      return PostCheckResult.of(
          gameEndResult.getGameboard(),
          combineGameEvents(result, gameEndResult), // order is important
          result.getNextPlayer());
    }

    return result;
  }

  private List<GameEvent> combineGameEvents(PostCheckResult result, PostCheckResult gameEndResult) {
    return Stream.concat(result.getGameEvents().stream(), gameEndResult.getGameEvents().stream())
        .collect(Collectors.toUnmodifiableList());
  }

  private PostCheckResult applyConsequence(
      Rule rule, MoveResult moveResult, Player currentPlayer, Gameboard boardAfterMoving) {
    return rule.getConsequence()
        .apply(boardAfterMoving, moveResult.getLastDropPosition(), currentPlayer);
  }

  private boolean wasRuleTriggered(Rule rule, MoveResult moveResult, Player currentPlayer) {
    return rule.getCondition().conditionMet(moveResult, currentPlayer);
  }
}
