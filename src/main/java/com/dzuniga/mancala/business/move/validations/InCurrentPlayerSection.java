package com.dzuniga.mancala.business.move.validations;

import com.dzuniga.mancala.business.exceptions.IncorrectPlayerSectionException;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.domain.Section;
import com.dzuniga.mancala.domain.Turn;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class InCurrentPlayerSection implements MoveValidation {

  @Override
  public void validate(Move move) {
    Objects.requireNonNull(move, "The move must not be null");

    Turn currentTurn = move.getCurrentTurn();

    Section playerSection = Gameboard.PIT_SECTIONS.get(Player.getOppositePlayer(currentTurn.getPlaying()));

    if (playerSection.getLow() >= move.getStartPosition() && move.getStartPosition() <= playerSection.getHigh()) {
      throw new IncorrectPlayerSectionException(move);
    }
  }
}
