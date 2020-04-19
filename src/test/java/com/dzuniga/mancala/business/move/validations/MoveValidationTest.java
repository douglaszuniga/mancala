package com.dzuniga.mancala.business.move.validations;

import com.dzuniga.mancala.business.move.validations.exceptions.MoveValidationException;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.domain.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MoveValidationTest {

  private MoveValidation fromAPit;
  private MoveValidation insideBoard;

  @BeforeEach
  public void setUp() {
    fromAPit = mock(MoveValidation.class);
    insideBoard = mock(MoveValidation.class);
    when(fromAPit.andThen(any(MoveValidation.class))).thenCallRealMethod();
  }

  @Test
  public void shouldCallBothValidationsWhenAndThenIsUsed() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int startPosition = 0;
    Turn turn =
        Turn.builder()
            .playerTwo(Player.PLAYER_TWO)
            .playerOne(Player.PLAYER_ONE)
            .playing(Player.PLAYER_ONE)
            .number(1)
            .currentBoard(gameboard)
            .events(List.of())
            .build();
    Move move = Move.builder().currentTurn(turn).startPosition(startPosition).build();

    assertDoesNotThrow(() -> fromAPit.andThen(insideBoard).validate(move));
    verify(fromAPit).validate(any(Move.class));
    verify(insideBoard).validate(any(Move.class));
  }

  @Test
  public void shouldCallFirstValidationsWhenAndThenIsUsedAndFirstValidationThrowsException() {
    Gameboard gameboard = Gameboard.newGameBoard();
    int startPosition = 0;
    Turn turn =
            Turn.builder()
                    .playerTwo(Player.PLAYER_TWO)
                    .playerOne(Player.PLAYER_ONE)
                    .playing(Player.PLAYER_ONE)
                    .number(1)
                    .currentBoard(gameboard)
                    .events(List.of())
                    .build();
    Move move = Move.builder().currentTurn(turn).startPosition(startPosition).build();
    doThrow(new MoveValidationException(move)).when(fromAPit).validate(any(Move.class));
    assertThrows(MoveValidationException.class, () -> fromAPit.andThen(insideBoard).validate(move));
    verify(fromAPit).validate(any(Move.class));
    verify(insideBoard, never()).validate(any(Move.class));
  }
}
