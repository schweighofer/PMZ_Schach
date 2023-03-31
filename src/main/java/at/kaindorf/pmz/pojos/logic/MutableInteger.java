package at.kaindorf.pmz.pojos.logic;

/**
 * @Author Marcus Schweighofer
 * Created on 31.03.2023.
 * Class: MutableInteger.java
 */

public class MutableInteger {
    private int v;
    public MutableInteger(int v) {
        this.v = v;
    }
    public void v(int v) {
        this.v = v;
    }
    public int v() {
        return v;
    }
}
