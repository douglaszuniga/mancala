package com.dzuniga.mancala.business;

import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import com.dzuniga.mancala.domain.Turn;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultNewGameSupplier implements NewGameSupplier {

  @Override
  public Turn get() {
    return Turn.builder()
        .number(0)
        .currentBoard(Gameboard.newGameBoard())
        .playerOne(Player.PLAYER_ONE)
        .playerOne(Player.PLAYER_TWO)
        .events(List.of(GameEvent.newGameStarted))
        .playing(Player.PLAYER_ONE)
        .build();
  }
}
