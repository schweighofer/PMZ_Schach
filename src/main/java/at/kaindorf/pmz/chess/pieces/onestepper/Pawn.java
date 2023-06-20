package at.kaindorf.pmz.chess.pieces.onestepper;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.chess.FieldState;
import at.kaindorf.pmz.chess.Piece;
import at.kaindorf.pmz.chess.pieces.Empty;

import java.util.ArrayList;
import java.util.List;

import static at.kaindorf.pmz.bl.Game.*;

/**
 * @Author Marcus Schweighofer
 * Created on 29.03.2023.
 * Class: Pawn.java
 */


public class Pawn extends Piece {
    public Pawn(Boolean isWhite, Game game, int moveCount) {
        super(isWhite, game, moveCount);
    }

    @Override
    public List<Integer> obtainPossibleMoves() {
        List<Integer> possibleMoves = new ArrayList<>();
        final int position = game.getPosition(this);

        int direction = (game.getPiece(position).isWhite() ? 1 : -1);
        int assumedPosition = position + LINE_SIZE * direction;
        // Move
        if (assumedPosition >= 0 && assumedPosition < FIELD_SIZE) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
                if (((int)(assumedPosition * DIVISOR_LINE_SIZE) * LINE_SIZE + LINE_SIZE * ((game.getPiece(position).isWhite() ? -1 : 1)) == (game.getPiece(position).isWhite() ? LINE_SIZE : FIELD_SIZE - 2 * LINE_SIZE))) {
                    if (game.getFieldState(assumedPosition + LINE_SIZE * direction) == FieldState.NULL) {
                        possibleMoves.add(assumedPosition + LINE_SIZE * direction);
                    }
                }
            }
        }
        // Kill
        // left
        if ((position % LINE_SIZE != 0)) {
            int possibleMove = position + LINE_SIZE * direction - 1;
            FieldState fieldState = game.getFieldState(possibleMove);
            if (fieldState != FieldState.NULL) {
                super.pieceAhead(possibleMoves, possibleMove, fieldState);
            }
        }
        // right
        if ((position % LINE_SIZE != 7)) {
            int possibleMove = position + LINE_SIZE * direction + 1;
            FieldState fieldState = game.getFieldState(possibleMove);
            if (fieldState != FieldState.NULL) {
                super.pieceAhead(possibleMoves, possibleMove, fieldState);
            }
        }
        //enpassant rules:
        /*
              neben bauern stehen
              andere farbe
              erst ein zug gefahren
              eigener und fremder bauer auf ((position >= 16 && position >= 23) || (position >= 40 && position >= 47))
         */
        if(isWhite()){
            if(position >= 33 && position <= 39){
                if(game.getBoard().get(position-1) instanceof Pawn && !game.getBoard().get(position-1).isWhite() && game.getBoard().get(position-1).getMoveCount() == 1 && game.getBoard().get(position+7) instanceof Empty){
                    possibleMoves.add(position + 7);
                }
            }
            if(position >= 32 && position <= 38){
                if(game.getBoard().get(position+1) instanceof Pawn && !game.getBoard().get(position+1).isWhite() && game.getBoard().get(position+1).getMoveCount() == 1 && game.getBoard().get(position+9) instanceof Empty){
                    possibleMoves.add(position + 9);
                }
            }
        }
        if(!isWhite()){
            if(position >= 25 && position <= 31){
                if(game.getBoard().get(position-1) instanceof Pawn && game.getBoard().get(position-1).isWhite() && game.getBoard().get(position-1).getMoveCount() == 1 && game.getBoard().get(position-9) instanceof Empty){
                    possibleMoves.add(position - 9);
                }
            }
            if(position >= 24 && position <= 30){
                if(game.getBoard().get(position+1) instanceof Pawn && game.getBoard().get(position+1).isWhite() && game.getBoard().get(position+1).getMoveCount() == 1 && game.getBoard().get(position-7) instanceof Empty){
                    possibleMoves.add(position - 7);
                }
            }
        }

        return possibleMoves;
    }

}
