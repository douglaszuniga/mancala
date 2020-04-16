package com.dzuniga.mancala.business.move;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.domain.Turn;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * This handler will try to apply the move following these steps:
 * - take all the pebbles from the start position pit
 * - start dropping one pebble counter clockwise in the board
 * - check the rules after moving
 *  - extra turn
 *  - pebbles capture
 *  - game end
 */
@Component
public class DefaultDropPebblesHandler implements DropPebblesHandler {

  @Override
  public MoveResult apply(Move move) {
    Turn currentTurn = move.getCurrentTurn();
    Gameboard board = currentTurn.getCurrentBoard();
    int startPosition = move.getStartPosition();
    Player currentPlayer = currentTurn.getPlaying();
    // - number of pebbles in the start position
    // - it will indicate number of pits + mancala where we have to drop a pebble
    int pebblesInPit = board.getNumberOfPebblesInPit(startPosition);
    int[] newBoard = Arrays.copyOf(board.getGameboard(), Gameboard.SIZE);

    int pebblesDropped = 1;
    // - take out all the pebbles from the pit
    newBoard[startPosition] = 0;
    int nextPit = 0;

    //FIX LOOP WHEN MANCALA
    while (pebblesDropped <= pebblesInPit) {
      nextPit = (startPosition + pebblesDropped) % Gameboard.SIZE;
      // - cannot drop pebbles into the opposite player mancala
      if (nextPit != Gameboard.MANCALAS.get(Player.getOppositePlayer(currentPlayer))) {
        newBoard[nextPit] += 1;
        pebblesDropped++;
      }
    }

    return MoveResult.of(new Gameboard(board.getId(), newBoard), nextPit, List.of());
  }
}
