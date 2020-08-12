package com.checkers;

import java.util.ArrayList;

public class ChessRook extends Piece {
    public final String rank = "Rook";

    public ChessRook(String color, Boolean taken){
        super(color,taken);
    }

    @Override
    public Boolean isValidMove(Move m, Piece p2) {
        return null;
    }

    @Override
    public ArrayList<Move> getMoveList(int r, int c, Piece [][] pieces) {
        return null;
    }
}
