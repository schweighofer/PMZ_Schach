package at.kaindorf.pmz.chess.pieces.onestepper;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.chess.Piece;
import at.kaindorf.pmz.pojos.logic.MutableInteger;

import java.util.ArrayList;
import java.util.List;

import static at.kaindorf.pmz.bl.Game.FIELD_SIZE;
import static at.kaindorf.pmz.bl.Game.LINE_SIZE;

/**
 * @Author Marcus Schweighofer
 * Created on 29.03.2023.
 * Class: Knight.java
 */

public class Knight extends Piece {
    public Knight(Boolean isWhite, Game game, int moveCount) {
        super(isWhite, game, moveCount);
    }

    @Override
    public List<Integer> obtainPossibleMoves() {
        List<Integer> possibleMoves = new ArrayList<>();
        final int position = game.getPosition(this);

        int[] options = {
                // right up
                - (2 * LINE_SIZE - 1),
                - (LINE_SIZE - 2),
                // right down
                LINE_SIZE + 2,
                2 * LINE_SIZE + 1,
                // left down
                2 * LINE_SIZE - 1,
                LINE_SIZE - 2,
                // left up
                - (LINE_SIZE + 2),
                - (2 * LINE_SIZE + 1)
        };

        // right up
        for (int i = 0; i < 2; i++) {
            MutableInteger possibleMove = new MutableInteger(position + options[i]);
            if ((possibleMove.v() >= 0) && (possibleMove.v() % LINE_SIZE != 0)) {
                handleMove(possibleMove, 0, possibleMoves);
            }
        }
        // right down
        for (int i = 2; i < 4; i++) {
            MutableInteger possibleMove = new MutableInteger(position + options[i]);
            if ((possibleMove.v() < FIELD_SIZE) && (possibleMove.v() % LINE_SIZE != 0)) {
                handleMove(possibleMove, 0, possibleMoves);
            }
        }
        // left down
        for (int i = 4; i < 6; i++) {
            MutableInteger possibleMove = new MutableInteger(position + options[i]);
            if ((possibleMove.v() < FIELD_SIZE) && (possibleMove.v() % LINE_SIZE != 7)) {
                handleMove(possibleMove, 0, possibleMoves);
            }
        }
        // right up
        for (int i = 6; i < 8; i++) {
            MutableInteger possibleMove = new MutableInteger(position + options[i]);
            if ((possibleMove.v() >= 0) && (possibleMove.v() % LINE_SIZE != 7)) {
                handleMove(possibleMove, 0, possibleMoves);
            }
        }

        return possibleMoves;
    }
}
