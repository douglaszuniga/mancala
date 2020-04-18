package com.dzuniga.mancala.business.move.postchecks.rules.consequences;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.domain.Player;

import java.util.function.BiFunction;

/**
 * Simple interface extending the {@link BiFunction} adding semantic value. It applies certain
 * actions in the board depending of the rule
 *
 * Preconditions:
 * - {@link MoveResult} must not be null - {@link Player} must not be null
 *
 * Throws:
 * - {@link NullPointerException} when either {@link MoveResult} or {@link Player} is null
 *
 * Returns:
 * - {@link RuleResult} containing all the gameboard with the actions applied,
 * the list of things that happened and the next playing player
 */
@FunctionalInterface
public interface Consequence extends BiFunction<MoveResult, Player, RuleResult> {}
