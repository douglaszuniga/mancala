package com.dzuniga.mancala.controller;

import com.dzuniga.mancala.FileLoader;
import com.dzuniga.mancala.domain.GameEvent;
import com.dzuniga.mancala.domain.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GameControllerTest implements FileLoader {

  @Autowired private MockMvc mockMvc;

  @Test
  public void shouldReturnCreatedStatusWhenCallingForANewGame() throws Exception {
    final int pebblesInPit = 6;
    final int pebblesInMancala = 0;
    final int initialTurnNumber = 0;

    mockMvc
        .perform(
            post("/v1/mancala/game")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.number").isNumber())
        .andExpect(jsonPath("$.number").value(initialTurnNumber))
        .andExpect(jsonPath("$.events[0]").value(GameEvent.NEW_GAME_STARTED.toString()))
        .andExpect(jsonPath("$.playing.id").isBoolean())
        .andExpect(jsonPath("$.playing.id").value(Player.PLAYER_ONE.getId()))
        .andExpect(jsonPath("$.playerOne.id").isBoolean())
        .andExpect(jsonPath("$.playerOne.id").value(Player.PLAYER_ONE.getId()))
        .andExpect(jsonPath("$.playerTwo.id").isBoolean())
        .andExpect(jsonPath("$.playerTwo.id").value(Player.PLAYER_TWO.getId()))
        .andExpect(jsonPath("$.currentBoard.id").isString())
        .andExpect(jsonPath("$.currentBoard.gameboard").isArray())
        .andExpect(jsonPath("$.currentBoard.gameboard[0]").value(pebblesInPit))
        .andExpect(jsonPath("$.currentBoard.gameboard[1]").value(pebblesInPit))
        .andExpect(jsonPath("$.currentBoard.gameboard[2]").value(pebblesInPit))
        .andExpect(jsonPath("$.currentBoard.gameboard[3]").value(pebblesInPit))
        .andExpect(jsonPath("$.currentBoard.gameboard[4]").value(pebblesInPit))
        .andExpect(jsonPath("$.currentBoard.gameboard[5]").value(pebblesInPit))
        .andExpect(jsonPath("$.currentBoard.gameboard[6]").value(pebblesInMancala))
        .andExpect(jsonPath("$.currentBoard.gameboard[7]").value(pebblesInPit))
        .andExpect(jsonPath("$.currentBoard.gameboard[8]").value(pebblesInPit))
        .andExpect(jsonPath("$.currentBoard.gameboard[9]").value(pebblesInPit))
        .andExpect(jsonPath("$.currentBoard.gameboard[10]").value(pebblesInPit))
        .andExpect(jsonPath("$.currentBoard.gameboard[11]").value(pebblesInPit))
        .andExpect(jsonPath("$.currentBoard.gameboard[12]").value(pebblesInPit))
        .andExpect(jsonPath("$.currentBoard.gameboard[13]").value(pebblesInMancala));
  }

  @Test
  public void
      shouldReturnBadRequestWhenCurrentPlayerIsOneAndTriesToStartMovingFromPlayerTwoSection()
          throws Exception {
    String badRequestInput =
        loadJsonFromFile(
            "move/requests/player-one-tries-start-location-in-player-two-section.json");

    String expectedResponse =
        loadJsonFromFile(
            "move/responses/player-one-tries-start-location-in-player-two-section.json");
    mockMvc
        .perform(
            put("/v1/mancala/game/move")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(badRequestInput))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse));
  }

  @Test
  public void shouldReturnBadRequestWhenCurrentPlayerTriesToPlayOutsideTheBoard() throws Exception {
    String badRequestInput =
        loadJsonFromFile("move/requests/player-one-tries-start-location-outside-board.json");

    String expectedResponse =
        loadJsonFromFile("move/responses/player-one-tries-start-location-outside-board.json");
    mockMvc
        .perform(
            put("/v1/mancala/game/move")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(badRequestInput))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse));
  }

  @Test
  public void shouldReturnBadRequestWhenPlayerOneTriesToPlayFromAMancala() throws Exception {
    String badRequestInput =
        loadJsonFromFile("move/requests/player-one-tries-playing-from-a-mancala.json");

    String expectedResponse =
        loadJsonFromFile("move/responses/player-one-tries-playing-from-a-mancala.json");
    mockMvc
        .perform(
            put("/v1/mancala/game/move")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(badRequestInput))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse));
  }

  @Test
  public void shouldReturnBadRequestWhenPlayerTwoTriesToPlayFromAMancala() throws Exception {
    String badRequestInput =
        loadJsonFromFile("move/requests/player-two-tries-playing-from-a-mancala.json");

    String expectedResponse =
        loadJsonFromFile("move/responses/player-two-tries-playing-from-a-mancala.json");
    mockMvc
        .perform(
            put("/v1/mancala/game/move")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(badRequestInput))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse));
  }

  @Test
  public void shouldReturnBadRequestWhenCurrentPlayerTriesToPlayFromPitThatDoesNotHavePebbles()
      throws Exception {
    String badRequestInput =
        loadJsonFromFile("move/requests/player-one-tries-playing-from-empty-pit.json");

    String expectedResponse =
        loadJsonFromFile("move/responses/player-one-tries-playing-from-empty-pit.json");
    mockMvc
        .perform(
            put("/v1/mancala/game/move")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(badRequestInput))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse));
  }

  @Test
  public void shouldReturnBadRequestWhenRequestBodyIsInvalid() throws Exception {
    String badRequestInput =
        loadJsonFromFile("move/requests/player-one-tries-playing-with-invalid-move-request.json");

    mockMvc
        .perform(
            put("/v1/mancala/game/move")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(badRequestInput))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnAcceptedWhenPlayerOneDoesAValidMove() throws Exception {
    String badRequestInput =
        loadJsonFromFile("move/requests/player-one-tries-playing-with-valid-move.json");

    String expectedResponse =
        loadJsonFromFile("move/responses/player-one-tries-playing-with-valid-move.json");
    mockMvc
        .perform(
            put("/v1/mancala/game/move")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(badRequestInput))
        .andDo(print())
        .andExpect(status().isAccepted())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse));
  }

  @Test
  public void shouldReturnAcceptedWhenPlayerTwoDoesAValidMove() throws Exception {
    String badRequestInput =
        loadJsonFromFile("move/requests/player-two-tries-playing-with-valid-move.json");

    String expectedResponse =
        loadJsonFromFile("move/responses/player-two-tries-playing-with-valid-move.json");
    mockMvc
        .perform(
            put("/v1/mancala/game/move")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(badRequestInput))
        .andDo(print())
        .andExpect(status().isAccepted())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse));
  }

  @Test
  public void shouldReturnAcceptedWhenPlayerTwoCapturesPebbles() throws Exception {
    String badRequestInput = loadJsonFromFile("move/requests/player-two-capture-pebbles.json");

    String expectedResponse = loadJsonFromFile("move/responses/player-two-capture-pebbles.json");
    mockMvc
        .perform(
            put("/v1/mancala/game/move")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(badRequestInput))
        .andDo(print())
        .andExpect(status().isAccepted())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse));
  }

  @Test
  public void shouldReturnAcceptedWhenPlayerOneGetsAnExtraTurn() throws Exception {
    String badRequestInput = loadJsonFromFile("move/requests/player-one-extra-turn.json");

    String expectedResponse = loadJsonFromFile("move/responses/player-one-extra-turn.json");
    mockMvc
        .perform(
            put("/v1/mancala/game/move")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(badRequestInput))
        .andDo(print())
        .andExpect(status().isAccepted())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse));
  }

  @Test
  public void shouldReturnAcceptedWhenGameEndAndPlayerTwoWon() throws Exception {
    String badRequestInput = loadJsonFromFile("move/requests/game-end-player-two-won.json");

    String expectedResponse = loadJsonFromFile("move/responses/game-end-player-two-won.json");
    mockMvc
        .perform(
            put("/v1/mancala/game/move")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(badRequestInput))
        .andDo(print())
        .andExpect(status().isAccepted())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse));
  }

  @Test
  public void shouldReturnAcceptedWhenGameEndAndPlayerOneWon() throws Exception {
    String badRequestInput = loadJsonFromFile("move/requests/game-end-player-one-won.json");

    String expectedResponse = loadJsonFromFile("move/responses/game-end-player-one-won.json");
    mockMvc
        .perform(
            put("/v1/mancala/game/move")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(badRequestInput))
        .andDo(print())
        .andExpect(status().isAccepted())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse));
  }

  @Test
  public void shouldReturnAcceptedWhenGameEndAndWasATie() throws Exception {
    String badRequestInput = loadJsonFromFile("move/requests/game-end-in-tie.json");

    String expectedResponse = loadJsonFromFile("move/responses/game-end-in-tie.json");
    mockMvc
        .perform(
            put("/v1/mancala/game/move")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(badRequestInput))
        .andDo(print())
        .andExpect(status().isAccepted())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse));
  }
}
