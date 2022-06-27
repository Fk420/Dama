package com.polishdroughts;

public class Pawn {
    private String pawnColor;
    private boolean isCrowned;
    Coordinates coordinates;

    public Pawn(String color, int x, int y) {
        pawnColor = color;
        isCrowned = false;
        coordinates = new Coordinates(x, y);
    }

    public String getColor() {
        return this.pawnColor;
    }

    @Override
    public String toString() {
        return this.getColor();
    }
}
