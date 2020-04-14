package com.dzuniga.mancala.business.move;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.domain.Move;

import java.util.function.Function;

@FunctionalInterface
public interface DropPebblesHandler extends Function<Move, MoveResult> {}
