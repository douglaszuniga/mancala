package com.dzuniga.mancala.business.move;

import com.dzuniga.mancala.business.move.validations.exceptions.MoveValidationException;
import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.business.move.model.RuleResult;
import com.dzuniga.mancala.business.move.postchecks.RulesApplier;
import com.dzuniga.mancala.business.move.validations.MoveValidation;
import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.domain.Turn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultMoveHandlerTest {

  @Mock private MoveValidation moveValidation;

  @Mock private DropPebblesHandler dropPebblesHandler;
  @Mock private RulesApplier rulesApplier;

  private DefaultMoveHandler handler;

  @BeforeEach
  public void setUp() {
    handler =
        new DefaultMoveHandler(
            moveValidation,
            moveValidation,
            moveValidation,
            moveValidation,
            dropPebblesHandler,
            rulesApplier);
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenMoveIsNull() {
    Assertions.assertThrows(NullPointerException.class, () -> handler.apply(null));
  }

  @Test
  public void shouldReturnTurnWhenMovePassAllValidation() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int lastDropPosition = 0;
    Turn turn =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_ONE)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();

    Move move = Move.builder().startPosition(lastDropPosition).currentTurn(turn).build();

    MoveResult moveResult = MoveResult.of(gameboard, 0, List.of());
    RuleResult ruleResult =
        RuleResult.of(gameboard, List.of(GameEvent.newGameStarted), Player.PLAYER_ONE);

    when(moveValidation.andThen(any(MoveValidation.class))).thenCallRealMethod();

    when(dropPebblesHandler.apply(any(Move.class))).thenReturn(moveResult);
    when(rulesApplier.apply(any(Move.class), any(MoveResult.class))).thenReturn(ruleResult);

    Turn actual = handler.apply(move);

    Assertions.assertEquals(ruleResult.getGameEvents(), actual.getEvents());
    Assertions.assertEquals(ruleResult.getNextPlayer(), turn.getPlaying());
    Assertions.assertEquals(turn.getNumber() + 1, actual.getNumber());
    Assertions.assertArrayEquals(
        ruleResult.getGameboard().getGameboard(), turn.getCurrentBoard().getGameboard());

    verify(moveValidation, times(4)).validate(any(Move.class));
    verify(dropPebblesHandler).apply(any(Move.class));
    verify(rulesApplier).apply(any(Move.class), any(MoveResult.class));
  }

  @Test
  public void shouldThrowMoveValidationExceptionWhenAValidationDidntPass() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int lastDropPosition = 0;
    Turn turn =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_ONE)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();

    Move move = Move.builder().startPosition(lastDropPosition).currentTurn(turn).build();

    RuleResult ruleResult =
        RuleResult.of(gameboard, List.of(GameEvent.newGameStarted), Player.PLAYER_ONE);

    when(moveValidation.andThen(any(MoveValidation.class))).thenCallRealMethod();
    doThrow(new MoveValidationException(move)).when(moveValidation).validate(any(Move.class));

    Assertions.assertThrows(MoveValidationException.class, () -> handler.apply(move));

    verify(moveValidation, atLeastOnce()).validate(any(Move.class));
    verify(dropPebblesHandler, never()).apply(any(Move.class));
    verify(rulesApplier, never()).apply(any(Move.class), any(MoveResult.class));
  }
}
