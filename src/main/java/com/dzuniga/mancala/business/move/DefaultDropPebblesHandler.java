package com.dzuniga.mancala.business.move;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.domain.Turn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This handler will try to apply the move following these steps:
 * - take all the pebbles from the start position pit
 * - start dropping one pebble counter clockwise in the board
 * - check the rules after moving
 *  - extra turn
 *  - pebbles capture
 *  - game end
 */
@Slf4j
@Component
public class DefaultDropPebblesHandler implements DropPebblesHandler {

  @Override
  public MoveResult apply(Move move) {
    Objects.requireNonNull(move);

    /*
     * Improvement:
     * move the logic related to adding and removing pebbles to the GameBoard class
     */

    Turn currentTurn = move.getCurrentTurn();
    Gameboard board = currentTurn.getCurrentBoard();
    int startPosition = move.getStartPosition();
    Player currentPlayer = currentTurn.getPlaying();
    Player oppositePlayer = Player.getOppositePlayer(currentPlayer);
    // - number of pebbles in the start position
    // - it will indicate number of pits + mancala where we have to drop a pebble
    int pebblesInPit = board.getNumberOfPebblesInPit(startPosition);
    int[] newBoard = Arrays.copyOf(board.getBoard(), Gameboard.SIZE);

    int pebblesDropped = 1;
    // - take out all the pebbles from the pit
    newBoard[startPosition] = 0;
    int nextPit = startPosition;

    //FIX LOOP WHEN MANCALA
    while (pebblesDropped <= pebblesInPit) {
      nextPit = getNextPositionInboard(nextPit);

      // - if the pit is not a mancala
      if (nextPit != Gameboard.MANCALAS.get(oppositePlayer)) {
        // - we can drop a pebble in there
        newBoard[nextPit] += 1;
        pebblesDropped++;
      }
    }

    return MoveResult.of(new Gameboard(board.getId(), newBoard), nextPit, List.of());
  }

  /**
   * calculates the next pit
   * @param currentPit current pit
   * @return next position of the pit
   */
  private int getNextPositionInboard(int currentPit) {
    return (currentPit + 1) % Gameboard.SIZE;
  }
}
