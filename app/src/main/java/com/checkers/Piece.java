package com.checkers;

import java.util.ArrayList;

public abstract class Piece {
    public String color;
    public boolean isTaken;

    public Piece(String color, boolean taken){
            this.color = color;
            this.isTaken = taken;
        }

    public void setTaken(){
        isTaken = true;
    }

    public abstract Boolean isValidMove(Move m);

    public abstract ArrayList<Move> getMoveList(int r, int c);
}
