package com.dzuniga.mancala.domain;

/** Possible events generated during the game */
public enum GameEvent {
  newGameStarted,
  extraTurnGained,
  pebblesCaptured,
  gameEnded,
  playerOneWon,
  playerTwoWon,
  tied
}
