package com.dzuniga.mancala.business.move.postchecks;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.domain.Move;

import java.util.function.BiFunction;

/**
 * Responsible of going thought all the rules after moving and finishing the current turn checking
 * if the rule was triggered and applying the consequence in the board.
 *
 * <p>Extending {@link BiFunction} to add semantic value
 *
 * <p>Preconditions: - parameters {@link Move} and {@link MoveResult} must not be null
 *
 * <p>Produces a {@link RuleResult} with all the information regarding board, player and what
 * happened
 *
 * <p>Expected Exceptions: - throws {@link NullPointerException} when either {@link Move} or {@link
 * MoveResult} is null - throws {@link NullPointerException} when any of the rules are null
 */
@FunctionalInterface
public interface RulesApplier extends BiFunction<Move, MoveResult, RuleResult> {}
