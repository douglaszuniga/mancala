package com.dzuniga.mancala.business.move.postchecks;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.PostCheckResult;
import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Move;

import java.util.List;

@FunctionalInterface
public interface PostCheck {
  PostCheckResult apply(Move appliedMovement, MoveResult moveResult, List<GameEvent> gameEvents);


}
