package com.dzuniga.mancala.business.move.postchecks.rules;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.domain.Player;

import java.util.Objects;
import java.util.Optional;

/** Interface used to check and run a set of game rules. */
@FunctionalInterface
public interface CombinableRule {

  /**
   * Runs a set of rules one by one Each rule is contains a pair of {@link
   * com.dzuniga.mancala.business.move.postchecks.rules.conditions.Condition} and {@link
   * com.dzuniga.mancala.business.move.postchecks.rules.consequences.Consequence}
   *
   * Preconditions:
   * - {@link MoveResult} must not be null
   * - {@link Player} must not be null
   *
   * Throws
   * - {@link NullPointerException} when either the MoveResult
   *
   * @param moveResult result of applying one move into the game
   * @param currentPlayer player currently playing
   * @return either {@code Optional.empty()} or an optional of {@link RuleResult}, where RuleResult
   *     contains the status of the game after applying a consequence
   */
  Optional<RuleResult> runRule(MoveResult moveResult, Player currentPlayer);

  /**
   * Handy function used to combine multiple EXCLUSIVE rules like ExtraTurnRule and
   * CapturePebblesRule
   *
   * @param other the other rule that will be ran after this
   * @return combined rule
   */
  default CombinableRule orRun(CombinableRule other) {
    Objects.requireNonNull(other);
    return (MoveResult mr, Player p) -> runRule(mr, p).or(() -> other.runRule(mr, p));
  }
}
