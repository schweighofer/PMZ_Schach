package at.kaindorf.pmz.chess.pieces.onestepper;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.chess.pieces.OneStepPiece;

import java.util.ArrayList;
import java.util.List;

import static at.kaindorf.pmz.chess.pieces.MoveType.PAWN;

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

        moves(position, possibleMoves, PAWN);

        return possibleMoves;
    }
}
