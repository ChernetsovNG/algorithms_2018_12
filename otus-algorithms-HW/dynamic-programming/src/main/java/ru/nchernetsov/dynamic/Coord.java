package ru.nchernetsov.dynamic;

public class Coord {

    private final int X;

    private final int Y;

    private Coord(int x, int y) {
        X = x;
        Y = y;
    }

    public static Coord of(int x, int y) {
        return new Coord(x, y);
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coord coord = (Coord) o;

        if (X != coord.X) return false;
        return Y == coord.Y;

    }

    @Override
    public int hashCode() {
        int result = X;
        result = 31 * result + Y;
        return result;
    }
}
