package com.dzuniga.mancala.business.move.postchecks.rules;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.business.move.postchecks.rules.conditions.Condition;
import com.dzuniga.mancala.business.move.postchecks.rules.consequences.Consequence;
import com.dzuniga.mancala.domain.Player;
import lombok.Data;

import java.util.Objects;
import java.util.Optional;

@Data
public class Rule implements CombinableRule {
  private final Condition condition;
  private final Consequence consequence;

  private Rule(Condition condition, Consequence consequence) {
    Objects.requireNonNull(condition, "The rule condition must not be null");
    Objects.requireNonNull(consequence, "The rule consequence must not be null");

    this.condition = condition;
    this.consequence = consequence;
  }

  public Optional<RuleResult> runRule(MoveResult moveResult, Player currentPlayer) {
    if (condition.conditionMet(moveResult, currentPlayer)) {
      return Optional.of(consequence.apply(moveResult, currentPlayer));
    }
    return Optional.empty();
  }

  public static Rule ruleOf(Condition condition, Consequence consequence) {
    return new Rule(condition, consequence);
  }
}
