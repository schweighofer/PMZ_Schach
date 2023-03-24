package at.kaindorf.pmz.pojos;

/**
 * @Author Marcus Schweighofer
 * Created on 24.03.2023.
 * Class: Piece.java
 */

public abstract class Piece {
    protected Boolean isWhite;
    protected Integer position;

    protected abstract Integer getPossibleMoves();
}
