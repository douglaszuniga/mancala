package com.dzuniga.mancala.business.move.postchecks.rules;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.domain.Player;

import java.util.Objects;
import java.util.Optional;

public interface CombinableRule {

  Optional<RuleResult> runRule(MoveResult moveResult, Player currentPlayer);

  default CombinableRule orRun(CombinableRule other) {
    Objects.requireNonNull(other);
    return (MoveResult mr, Player p) -> runRule(mr, p).or(() -> other.runRule(mr, p));
  }
}
