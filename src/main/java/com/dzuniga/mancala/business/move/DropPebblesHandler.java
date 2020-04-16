package com.dzuniga.mancala.business.move;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.domain.Move;

import java.util.function.Function;

/**
 * Interface that apply the {@code Move}, dropping the pebbles in the gameboard
 * In this case we take advantage of the functional interface {@code java.util.function.Function} by giving
 * a semantic meaning for easier understanding
 */
@FunctionalInterface
public interface DropPebblesHandler extends Function<Move, MoveResult> {}
