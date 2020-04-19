package com.dzuniga.mancala.controller;

import com.dzuniga.mancala.business.NewGameSupplier;
import com.dzuniga.mancala.business.move.MoveHandler;
import com.dzuniga.mancala.domain.Move;
import com.dzuniga.mancala.domain.Turn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/mancala")
public class GameController {

  private final NewGameSupplier newGameSupplier;
  private final MoveHandler moveHandler;

  public GameController(NewGameSupplier newGameSupplier, MoveHandler moveHandler) {
    this.newGameSupplier = newGameSupplier;
    this.moveHandler = moveHandler;
  }

  @PutMapping(
      value = "/game/move",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Turn handleProcessMove(@RequestBody Move move) {
    return moveHandler.apply(move);
  }

  @PostMapping(value = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Turn handleProcessNewGame() {
    return newGameSupplier.get();
  }
}
