package com.checkers;

import java.util.ArrayList;

public class ChessBishop extends Piece {

    public ChessBishop(String color, Boolean taken){
        super(color,taken);
    }

    @Override
    public Boolean isValidMove(Move m, Boolean turn, Piece p2) {
        return null;
    }

    @Override
    public ArrayList<Move> getMoveList(int r, int c) {
        return null;
    }
}
