package com.dzuniga.mancala.domain;

/** Possible events generated during the game */
public enum GameEvent {
  NEW_GAME_STARTED,
  EXTRA_TURN_GAINED,
  PEBBLES_CAPTURED,
  GAME_ENDED,
  PLAYER_ONE_WON,
  PLAYER_TWO_WON,
  TIED
}
