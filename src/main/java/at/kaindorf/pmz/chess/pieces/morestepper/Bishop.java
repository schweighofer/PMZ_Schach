package at.kaindorf.pmz.chess.pieces.morestepper;

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
 * Class: Bishop.java
 */

public class Bishop extends Piece {
    public Bishop(Boolean isBlack, Game game, int moveCount) {
        super(isBlack, game, moveCount);
    }

    @Override
    public List<Integer> obtainPossibleMoves() {
        List<Integer> possibleMoves = new ArrayList<>();
        final int position = game.getPosition(this);

        MutableInteger assumedPosition = new MutableInteger(position - LINE_SIZE - 1);
        while (((assumedPosition.v() + LINE_SIZE + 1) % LINE_SIZE != 0) && (assumedPosition.v() >= 0)) {
            if (handleMove(assumedPosition, -(LINE_SIZE + 1), possibleMoves)) {
                break;
            }
        }

        assumedPosition.v(position - LINE_SIZE + 1);
        while (((assumedPosition.v() + LINE_SIZE - 1) % LINE_SIZE != LINE_SIZE - 1) && (assumedPosition.v() >= 0)) {
            if (handleMove(assumedPosition, -(LINE_SIZE - 1), possibleMoves)) {
                break;
            }
        }

        assumedPosition.v(position + LINE_SIZE - 1);
        while (((assumedPosition.v() - LINE_SIZE + 1) % LINE_SIZE != 0) && (assumedPosition.v() < FIELD_SIZE)) {
            if (handleMove(assumedPosition, LINE_SIZE - 1, possibleMoves)) {
                break;
            }
        }

        assumedPosition.v(position + LINE_SIZE + 1);
        while (((assumedPosition.v() - LINE_SIZE - 1) % LINE_SIZE != LINE_SIZE - 1) && (assumedPosition.v() < FIELD_SIZE)) {
            if (handleMove(assumedPosition, LINE_SIZE + 1, possibleMoves)) {
                break;
            }
        }

        return possibleMoves;
    }
}
