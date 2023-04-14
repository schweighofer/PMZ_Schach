package at.kaindorf.pmz.chess.pieces.morestepper;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.chess.pieces.MoreStepPiece;

import java.util.ArrayList;
import java.util.List;

import static at.kaindorf.pmz.chess.pieces.MoveType.*;

/**
 * @Author Marcus Schweighofer
 * Created on 29.03.2023.
 * Class: Bishop.java
 */

public class Bishop extends MoreStepPiece {
    public Bishop(Boolean isBlack, Game game) {
        super(isBlack, game);
    }

    @Override
    public List<Integer> obtainPossibleMoves() {
        List<Integer> possibleMoves = new ArrayList<>();
        final int position = game.getPosition(this);

        moves(position, possibleMoves, LEFT_UP, RIGHT_UP, LEFT_DOWN, RIGHT_DOWN);

        return possibleMoves;
    }
}
