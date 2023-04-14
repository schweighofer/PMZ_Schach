package at.kaindorf.pmz.chess.pieces.onestepper;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.chess.pieces.OneStepPiece;

import java.util.ArrayList;
import java.util.List;

import static at.kaindorf.pmz.chess.pieces.MoveType.KNIGHT;

/**
 * @Author Marcus Schweighofer
 * Created on 29.03.2023.
 * Class: Knight.java
 */

public class Knight extends OneStepPiece {
    public Knight(Boolean isBlack, Game game) {
        super(isBlack, game);
    }

    @Override
    public List<Integer> obtainPossibleMoves() {
        List<Integer> possibleMoves = new ArrayList<>();
        final int position = game.getPosition(this);

        moves(position, possibleMoves, KNIGHT);

        return possibleMoves;
    }
}
