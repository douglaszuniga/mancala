package com.dzuniga.mancala.business.move.postchecks;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.domain.Move;

@FunctionalInterface
public interface RulesApplier {
  RuleResult apply(Move appliedMovement, MoveResult moveResult);
}
