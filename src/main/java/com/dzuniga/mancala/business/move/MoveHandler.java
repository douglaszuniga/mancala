package com.dzuniga.mancala.business.move;

import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Turn;

import java.util.function.Function;

/**
 * Functional Interface with semantic value responsible of orchestrating the application of a move
 * in the game
 *
 * <p>Preconditions {@link Move} must not be null
 *
 * <p>Throws {@link NullPointerException} when {@link Move} is null and {@link
 * com.dzuniga.mancala.business.exceptions.MoveValidationException} when a {@link
 * com.dzuniga.mancala.business.move.validations.MoveValidation} didn't pass
 */
@FunctionalInterface
public interface MoveHandler extends Function<Move, Turn> {}
