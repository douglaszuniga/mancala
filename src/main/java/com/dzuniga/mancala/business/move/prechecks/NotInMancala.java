package com.dzuniga.mancala.business.move.prechecks;

import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Move;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class NotInMancala implements PreCheck {
  @Override
  public boolean test(Move move) {
    Objects.requireNonNull(move, "The move must not be null");

    return !Gameboard.MANCALAS.containsValue(move.getStartPosition());
  }
}
