package com.dzuniga.mancala.business.move.postchecks.rules.conditions;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.domain.Player;

@FunctionalInterface
public interface Condition {
  boolean conditionMet(MoveResult moveResult, Player currentPlayer);
}
