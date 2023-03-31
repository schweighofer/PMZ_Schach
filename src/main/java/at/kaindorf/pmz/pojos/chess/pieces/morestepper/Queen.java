package at.kaindorf.pmz.pojos.chess.pieces.morestepper;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.pojos.chess.FieldState;
import at.kaindorf.pmz.pojos.chess.Piece;
import at.kaindorf.pmz.pojos.chess.pieces.MoreStepPiece;

import java.util.ArrayList;
import java.util.List;

import static at.kaindorf.pmz.bl.Game.*;
import static at.kaindorf.pmz.bl.Game.LINE_SIZE;
import static at.kaindorf.pmz.pojos.chess.pieces.MoveType.*;

/**
 * @Author Marcus Schweighofer
 * Created on 29.03.2023.
 * Class: Queen.java
 */

public class Queen extends MoreStepPiece {
    public Queen(Boolean isBlack, Game game) {
        super(isBlack, game);
    }

    @Override
    public List<Integer> getPossibleMoves() {
        List<Integer> possibleMoves = new ArrayList<>();
        final int position = game.getPosition(this);

        step(position, possibleMoves, LEFT, RIGHT, UP, DOWN, LEFT_UP, RIGHT_UP, LEFT_DOWN, RIGHT_DOWN);

        return possibleMoves;
    }
}