package com.dzuniga.mancala.business.move.prechecks;

import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Section;
import com.dzuniga.mancala.domain.Turn;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class InPlayerSection implements PreCheck {
  @Override
  public boolean test(Move move) {
    Objects.requireNonNull(move, "The move must not be null");

    Turn currentTurn = move.getCurrentTurn();
    Section playerSection = Gameboard.PIT_SECTIONS.get(currentTurn.getPlaying());

    return playerSection.getLow() >= move.getStartPosition()
        && move.getStartPosition() <= playerSection.getHigh();
  }
}
