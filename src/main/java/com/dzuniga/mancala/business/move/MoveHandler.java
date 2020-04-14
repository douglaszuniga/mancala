package com.dzuniga.mancala.business.move;

import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Turn;

import java.util.function.Function;

@FunctionalInterface
public interface MoveHandler extends Function<Move, Turn> { }
