package com.dzuniga.mancala.business.exceptions;

import com.dzuniga.mancala.domain.Move;

/**
 * Move validation exception with semantic value indicating that the move
 * must be from the current player section of the board
 */
public class IncorrectPlayerSectionException extends MoveValidationException {
    public IncorrectPlayerSectionException(Move move) {
        super(move);
    }

    @Override
    public String getMessage() {
        return "The move is invalid, the start pit must be inside the current player section of the board.";
    }
}
