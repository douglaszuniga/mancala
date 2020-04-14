package com.dzuniga.mancala.business.move;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.PostCheckResult;
import com.dzuniga.mancala.business.move.postchecks.PostCheck;
import com.dzuniga.mancala.business.move.prechecks.PreCheck;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.domain.Turn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class DefaultMoveHandler implements MoveHandler {

  //TODO: refactor to use a list and get check message from the precheck
  private final PreCheck inPlayerSection;
  private final PreCheck insideBoard;
  private final PreCheck notInEmptyPit;
  private final PreCheck notInMancala;

  private final DropPebblesHandler dropPebblesHandler;

  private final PostCheck postCheck;

  public DefaultMoveHandler(
      PreCheck inPlayerSection,
      PreCheck insideBoard,
      PreCheck notInEmptyPit,
      PreCheck notInMancala,
      DropPebblesHandler dropPebblesHandler,
      PostCheck postCheck) {

    this.inPlayerSection = inPlayerSection;
    this.insideBoard = insideBoard;
    this.notInEmptyPit = notInEmptyPit;
    this.notInMancala = notInMancala;
    this.dropPebblesHandler = dropPebblesHandler;
    this.postCheck = postCheck;
  }

  @Override
  public Turn apply(Move move) {
    Objects.requireNonNull(move, "The move must not be null");

    // todo: combine prechecks into a validate move
    if (!inPlayerSection.test(move)) {
      // throw Exception
    }

    if (!insideBoard.test(move)) {
      // throw Exception
    }

    if (!notInEmptyPit.test(move)) {
      // throw Exception
    }

    if (!notInMancala.test(move)) {
      // throw Exception
    }

    MoveResult moveResult = dropPebblesHandler.apply(move);

    //TODO: remove game events from post check
    PostCheckResult postCheckResult = postCheck.apply(move, moveResult, List.of());

    return Turn.builder()
            .currentBoard(postCheckResult.getGameboard())
            .events(postCheckResult.getGameEvents())
            .number(move.getCurrentTurn().getNumber() + 1)
            .playing(postCheckResult.getNextPlayer())
            .playerOne(Player.PLAYER_ONE)
            .playerTwo(Player.PLAYER_TWO)
            .build();
  }
}
