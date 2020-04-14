package com.dzuniga.mancala.business.move.postchecks.rules;

import com.dzuniga.mancala.business.move.postchecks.rules.conditions.Condition;
import com.dzuniga.mancala.business.move.postchecks.rules.consequences.Consequence;
import lombok.Data;

@Data
public class Rule {
  private final Condition condition;
  private final Consequence consequence;

  private Rule(Condition condition, Consequence consequence) {
    this.condition = condition;
    this.consequence = consequence;
  }

  public static Rule ruleOf(Condition condition, Consequence consequence) {
    return new Rule(condition, consequence);
  }
}
