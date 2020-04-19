package com.dzuniga.mancala.controller;

import com.dzuniga.mancala.business.NewGameSupplier;
import com.dzuniga.mancala.business.move.MoveHandler;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Turn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controls and handles the game request to the system
 */
@RestController
@RequestMapping("/v1/mancala")
@CrossOrigin
public class GameController {

  private final NewGameSupplier newGameSupplier;
  private final MoveHandler moveHandler;

  public GameController(NewGameSupplier newGameSupplier, MoveHandler moveHandler) {
    this.newGameSupplier = newGameSupplier;
    this.moveHandler = moveHandler;
  }

  /**
   * handles the action to processing a move from the user
   * @param move {@link Move} object containing the move intention of the user
   * @return {@link Turn} indicating the state of the game after applying the {@link Move}
   */
  @PutMapping(
      value = "/game/move",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Turn handleProcessMove(@RequestBody Move move) {
    return moveHandler.apply(move);
  }

  /**
   * handles the action of creating a new game
   * @return {@link Turn} the initial turn
   */
  @PostMapping(value = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Turn handleProcessNewGame() {
    return newGameSupplier.get();
  }
}
