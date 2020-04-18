package com.dzuniga.mancala.business;

import com.dzuniga.mancala.domain.Turn;

import java.util.function.Supplier;

/**
 * Supplier Functional interface with semantic value responsible of providing a new game
 */
@FunctionalInterface
public interface NewGameSupplier extends Supplier<Turn> {}
