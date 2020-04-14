package com.dzuniga.mancala.business.move.postchecks.rules.consequences;

import com.dzuniga.mancala.business.move.model.PostCheckResult;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;

@FunctionalInterface
public interface Consequence {
  PostCheckResult apply(Gameboard boardAfterMoving, int lastDropPosition, Player currentPlayer);
}
