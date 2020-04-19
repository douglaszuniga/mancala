package com.dzuniga.mancala.business.move.model;

import com.dzuniga.mancala.TestUtils;
import com.dzuniga.mancala.domain.Gameboard;
import com.dzuniga.mancala.domain.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RuleResultTest {
    @Test
    public void testEquality() {
        Gameboard gameboard = Gameboard.newGameBoard();

        RuleResult alpha = RuleResult.of(gameboard, List.of(), Player.PLAYER_ONE);
        RuleResult beta = RuleResult.of(gameboard, List.of(), Player.PLAYER_ONE);
        RuleResult gama = RuleResult.of(gameboard, List.of(), Player.PLAYER_ONE);
        RuleResult delta = RuleResult.of(gameboard, List.of(), Player.PLAYER_TWO);

        TestUtils.assertEqualsContract(alpha, beta, gama, delta);
    }

    @Test
    public void shouldReturnNullPointerExceptionWhenGameboardIsNull() {
        assertThrows(NullPointerException.class, () -> RuleResult.of(null, List.of(), Player.PLAYER_ONE));
    }

    @Test
    public void shouldReturnNullPointerExceptionWhenListOfEventsIsNull() {
        assertThrows(NullPointerException.class, () -> RuleResult.of(Gameboard.newGameBoard(), null, Player.PLAYER_ONE));
    }

    @Test
    public void shouldReturnNullPointerExceptionWhenPlayerIsNull() {
        assertThrows(NullPointerException.class, () -> RuleResult.of(Gameboard.newGameBoard(), List.of(), null));
    }
}