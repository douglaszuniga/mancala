package com.dzuniga.mancala.business.move;

import com.dzuniga.mancala.business.move.validations.exceptions.MoveValidationException;
import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.business.move.postchecks.RulesApplier;
import com.dzuniga.mancala.business.move.validations.MoveValidation;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.domain.Turn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class DefaultMoveHandler implements MoveHandler {

  /**
   * all the validations that need to run BEFORE applying the move
   */
  private final MoveValidation inCurrentPlayerSection;
  private final MoveValidation insideBoard;
  private final MoveValidation pitHasPebbles;
  private final MoveValidation fromAPit;

  /**
   * handler that knows how to apply the move in the gameboard
   */
  private final DropPebblesHandler dropPebblesHandler;

  /**
   * check all the game rules and apply the actions based on those rules
   */
  private final RulesApplier rulesApplier;

  public DefaultMoveHandler(
      MoveValidation inCurrentPlayerSection,
      MoveValidation insideBoard,
      MoveValidation pitHasPebbles,
      MoveValidation fromAPit,
      DropPebblesHandler dropPebblesHandler,
      RulesApplier rulesApplier) {

    Objects.requireNonNull(inCurrentPlayerSection, "The inCurrentPlayerSection validation must not be null");
    Objects.requireNonNull(insideBoard, "The insideBoard validation must not be null");
    Objects.requireNonNull(pitHasPebbles, "The pitHasPebbles validation must not be null");
    Objects.requireNonNull(fromAPit, "The fromAPit validation must not be null");
    Objects.requireNonNull(dropPebblesHandler, "The dropPebblesHandler validation must not be null");
    Objects.requireNonNull(rulesApplier, "The rulesApplier validation must not be null");

    this.inCurrentPlayerSection = inCurrentPlayerSection;
    this.insideBoard = insideBoard;
    this.pitHasPebbles = pitHasPebbles;
    this.fromAPit = fromAPit;
    this.dropPebblesHandler = dropPebblesHandler;
    this.rulesApplier = rulesApplier;
  }

  @Override
  public Turn apply(Move move) throws MoveValidationException {
    Objects.requireNonNull(move, "The move must not be null");

    log.debug("Applying move: [{}]", move);

    // -- first check that move is valid
    validateBeforeMoving(move);
    // -- then start dropping the pebbles
    MoveResult moveResult = dropPebblesHandler.apply(move);
    log.debug("move result: [{}]", move);
    // -- then check what is the game status and all the possible action after applying the move
    RuleResult ruleResult = rulesApplier.apply(move, moveResult);
    log.debug("rule result: [{}]", ruleResult);

    // -- finally, return the next turn information
    return Turn.builder()
        .currentBoard(ruleResult.getGameboard())
        .events(ruleResult.getGameEvents())
        .number(move.getCurrentTurn().getNumber() + 1)
        .playing(ruleResult.getNextPlayer())
        .playerOne(Player.PLAYER_ONE)
        .playerTwo(Player.PLAYER_TWO)
        .build();
  }

  /**
   * encapsulates all the validations required before the move
   * @param move object containing the information regarding the move
   * @throws MoveValidationException exception in case it didn't pass a validation
   */
  private void validateBeforeMoving(Move move) throws MoveValidationException {
    insideBoard
        .andThen(inCurrentPlayerSection)
        .andThen(fromAPit)
        .andThen(pitHasPebbles)
        .validate(move);
  }
}
