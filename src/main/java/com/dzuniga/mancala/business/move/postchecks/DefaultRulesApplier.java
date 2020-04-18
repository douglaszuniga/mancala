package com.dzuniga.mancala.business.move.postchecks;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.business.move.postchecks.rules.Rule;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class DefaultRulesApplier implements RulesApplier {

  private final Rule extraTurnRule;
  private final Rule capturePebblesRule;
  private final Rule gameEndRule;

  public DefaultRulesApplier(Rule extraTurnRule, Rule capturePebblesRule, Rule gameEndRule) {
    Objects.requireNonNull(extraTurnRule, "The extra turn rule must not be null");
    Objects.requireNonNull(capturePebblesRule, "The capture pebbles rule must not be null");
    Objects.requireNonNull(gameEndRule, "The game end rule must not be null");

    this.extraTurnRule = extraTurnRule;
    this.capturePebblesRule = capturePebblesRule;
    this.gameEndRule = gameEndRule;
  }

  @Override
  public RuleResult apply(Move appliedMove, MoveResult moveResult) {
    Objects.requireNonNull(appliedMove, "The applied move must not be null");
    Objects.requireNonNull(moveResult, "The move result must not be null");

    Player currentPlayer = appliedMove.getCurrentTurn().getPlaying();
    Gameboard boardAfterMoving = moveResult.getGameboard();

    // - extra turn and capture pebbles rules are exclusive
    // - if the first ran then the second would not run
    Optional<RuleResult> extraTurnOrCaptureResult =
        extraTurnRule.orRun(capturePebblesRule).runRule(moveResult, currentPlayer);

    // -- if the extraTurnOrCaptureResult had any result then
    // -- the board should be the one produced after applying those rules
    // -- otherwise use the original after dropping the pebbles
    MoveResult moveResultFromRuleOrDefault =
        extraTurnOrCaptureResult
            .map(
                r ->
                    MoveResult.of(
                        r.getGameboard(), moveResult.getLastDropPosition(), r.getGameEvents()))
            .orElse(moveResult);

    // -- try to apply the game end rule, using the result produced until this point
    Optional<RuleResult> gameEndResult =
        gameEndRule.runRule(moveResultFromRuleOrDefault, currentPlayer);

    // -- finally, if the end game rule produced something then return that
    // -- otherwise if the extraTurnOrCaptureRule produced something then use that
    // -- otherwise return just switch current player and the board after dropping pebbles
    return gameEndResult
        .or(() -> extraTurnOrCaptureResult)
        .orElse(
            RuleResult.of(boardAfterMoving, List.of(), Player.getOppositePlayer(currentPlayer)));
  }
}
