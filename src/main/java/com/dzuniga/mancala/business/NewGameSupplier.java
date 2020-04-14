package com.dzuniga.mancala.business;

import com.dzuniga.mancala.domain.Turn;

import java.util.function.Supplier;

@FunctionalInterface
public interface NewGameSupplier extends Supplier<Turn> {}
