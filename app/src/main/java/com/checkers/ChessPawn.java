package com.checkers;

import java.util.ArrayList;

public class ChessPawn extends Piece {
    public final String rank = "Pawn";

    public ChessPawn(String color, Boolean taken){
        super(color,taken);
    }

    @Override
    public Boolean isValidMove(Move m, Boolean turn, Piece p2) {
        return (isValidDirection(m) && isValidTarget(m, p2) && turn);
    }

    @Override
    public ArrayList<Move> getMoveList(int r, int c) {
        return null;
    }

    private Boolean isValidDirection(Move m){

        if(color.equals("Black") || m.r2 == m.r1 - 1){
            return true;
        }else if(color.equals("White") || m.r2 == m.r1 + 1){
            return true;
        }else{
            return false;
        }

    }

    //returns true if the selected space is empty, also checks to make sure the space is on the board.
    private Boolean isValidTarget(Move m, Piece p2){
        if(m.r2 < 0 || m.c2 < 0 || m.r2 > 7 || m.c2 > 7){
            return false;
        }

        return p2 == null;
    }
}
