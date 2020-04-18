package com.dzuniga.mancala.business.move.postchecks.rules.conditions;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.domain.Player;

import java.util.function.BiPredicate;

/**
 * Simple interface extending the {@link BiPredicate} adding semantic value indicating when a rule condition
 * was met.
 *
 * it must check only one rule
 *
 * Preconditions:
 *  - {@link MoveResult} must not be null
 *  - {@link Player} must not be null
 * Throws:
 *  - {@link NullPointerException} when either {@link MoveResult} or {@link Player} is null
 */
@FunctionalInterface
public interface Condition extends BiPredicate<MoveResult, Player> {}
