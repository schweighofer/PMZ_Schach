package at.kaindorf.pmz.pojos.chess.pieces.onestepper;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.pojos.chess.FieldState;
import at.kaindorf.pmz.pojos.chess.Piece;
import at.kaindorf.pmz.pojos.chess.pieces.OneStepPiece;

import java.util.ArrayList;
import java.util.List;

import static at.kaindorf.pmz.bl.Game.*;
import static at.kaindorf.pmz.pojos.chess.pieces.MoveType.PAWN;

/**
 * @Author Marcus Schweighofer
 * Created on 29.03.2023.
 * Class: Pawn.java
 */


public class Pawn extends OneStepPiece {
    public Pawn(Boolean isBlack, Game game) {
        super(isBlack, game);
    }

    @Override
    public List<Integer> getPossibleMoves() {
        List<Integer> possibleMoves = new ArrayList<>();
        final int position = game.getPosition(this);

        step(position, possibleMoves, PAWN);

        return possibleMoves;
    }
}
