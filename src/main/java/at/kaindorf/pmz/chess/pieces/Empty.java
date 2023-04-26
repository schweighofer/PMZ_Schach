package at.kaindorf.pmz.chess.pieces;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.chess.Piece;

import java.util.List;

/**
 * @Author Marcus Schweighofer
 * Created on 26.04.2023.
 * Class: Empty.java
 */

public class Empty extends Piece {

    public Empty(Game game) {
        super(true, game);
    }

    @Override
    public List<Integer> obtainPossibleMoves() {
        return null;
    }

    @Override
    protected void left(List<Integer> possibleMoves, int position) {

    }

    @Override
    protected void right(List<Integer> possibleMoves, int position) {

    }

    @Override
    protected void up(List<Integer> possibleMoves, int position) {

    }

    @Override
    protected void down(List<Integer> possibleMoves, int position) {

    }

    @Override
    protected void left_up(List<Integer> possibleMoves, int position) {

    }

    @Override
    protected void right_up(List<Integer> possibleMoves, int position) {

    }

    @Override
    protected void left_down(List<Integer> possibleMoves, int position) {

    }

    @Override
    protected void right_down(List<Integer> possibleMoves, int position) {

    }

    @Override
    protected void pawn(List<Integer> possibleMoves, int position) {

    }

    @Override
    protected void knight(List<Integer> possibleMoves, int position) {

    }
}
