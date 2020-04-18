package com.dzuniga.mancala.business.move.postchecks.rules.conditions;

import com.dzuniga.mancala.business.move.model.MoveResult;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.domain.Section;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Responsible of checking the game end rule condition.
 *
 * <p>game end rule condition is triggered when a player section without including the mancala has
 * zero pebbles
 */
@Component
public class GameEndCondition implements Condition {

  @Override
  public boolean test(MoveResult moveResult, Player currentPlayer) {
    Objects.requireNonNull(moveResult, "The move result must not be null");
    Objects.requireNonNull(currentPlayer, "The current currentPlayer must not be null");

    Section player1Section = Gameboard.PIT_SECTIONS.get(Player.PLAYER_ONE);
    Section player2Section = Gameboard.PIT_SECTIONS.get(Player.PLAYER_TWO);

    return moveResult.getGameboard().isPitSectionEmpty(player1Section)
        || moveResult.getGameboard().isPitSectionEmpty(player2Section);
  }
}
