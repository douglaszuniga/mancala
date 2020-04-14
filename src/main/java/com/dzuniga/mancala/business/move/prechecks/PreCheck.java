package com.dzuniga.mancala.business.move.prechecks;

import com.dzuniga.mancala.domain.Move;

import java.util.function.Predicate;

@FunctionalInterface
public interface PreCheck extends Predicate<Move> {}
