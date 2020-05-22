package com.checkers;

import java.util.ArrayList;

import Piece;

public class CheckersPawn extends Piece {

    public String rank;

    public CheckersPawn(String color, String rank, Boolean taken){
        super(color,taken);
        this.rank = rank;
    }

    @Override
    public Boolean isValidMove(Move m) {
        return null;
    }

    @Override
    public ArrayList<Move> getMoveList(int r, int c) {
        return null;
    }
}
