package com.checkers;

import android.util.Log;

import java.util.ArrayList;

public class ChessPawn extends Piece {
    public final String rank = "Pawn";

    public ChessPawn(String color, Boolean taken){
        super(color,taken);
    }

    @Override
    public Boolean isValidMove(Move m, Piece p2) {
        return (isValidDirection(m) && isValidTarget(m, p2));
    }

    private Boolean isValidJump(Move m, Piece p2){
        String otherColor;
        if(p2 != null){
            otherColor = p2.color;
        }else{
            return false;
        }

        if(isValidColumn(m) && isValidRow(m,otherColor)){
            return isValidJumpTarget(otherColor);
        }
        return false;
    }

    @Override
    public ArrayList<Move> getMoveList(int r, int c, Piece [][] pieces) {
        ArrayList<Move> valid_moves = new ArrayList<>();
        int r2;
        if(color.equals("Black")){
            r2 = r-1;
        }else{
            r2 = r+1;
        }
        Move m = new Move(r,c,r2,c,false);

        if(isValidMove(m, pieces[r2][c])){
            valid_moves.add(m);
        }

        Move j1 = new Move(r,c,r2,c+1,true);
        Move j2 = new Move(r,c,r2,c-1,true);

        if(isValidJump(j1, pieces[r2][c+1])){
            valid_moves.add(m);
        }

        if(isValidJump(j2, pieces[r2][c-1])){
            valid_moves.add(m);
        }

        return valid_moves;
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

    private Boolean isValidColumn(Move m){
        return (m.c2 == m.c1+1) || (m.c2 == m.c1-1);
    }

    private Boolean isValidRow(Move m, String otherColor){
        return ((m.r2 == m.r1-1) && color.equals("Black")) || ((m.r2 == m.r1+1) && color.equals("White"));
    }

    private Boolean isValidJumpTarget(String otherColor){
        if(color.equals("Black") && otherColor.equals("White")){
            return true;
        }else if(color.equals("White") && otherColor.equals("Black")){
            return true;
        }else{
            return false;
        }
    }
}
