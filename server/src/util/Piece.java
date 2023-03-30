package util;

public class Piece {
    private String src;
    private double x,y;

    public Piece(String src, double x, double y) {
        this.src = src;
        this.x = x;
        this.y = y;
    }

    public String getSrc() {
        return src;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
