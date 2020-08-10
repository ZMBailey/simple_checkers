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

    private Boolean isValidJump(Move m, Boolean turn, Piece p2){
        String otherColor;
        if(p2 != null){
            otherColor = p2.color;
        }else{
            return false;
        }

        if(isValidColumn(m) && isValidRow(m,otherColor) && turn){
            return isValidJumpTarget(otherColor);
        }
        return false;
    }

    @Override
    public ArrayList<Move> getMoveList(int r, int c) {
        ArrayList<Move> moveList = new ArrayList<>();
        int r2;
        if(color.equals("Black")){
            r2 = r-1;
        }else{
            r2 = r+1;
        }
        moveList.add(new Move(r,c,r2,c,false));

        return moveList;
    }

    public ArrayList<Move> getJumpList(int r, int c) {
        ArrayList<Move> moveList = new ArrayList<>();
        int r2;
        if(color.equals("Black")){
            r2 = r-1;
        }else{
            r2 = r+1;
        }
        moveList.add(new Move(r,c,r2,c+1,true));
        moveList.add(new Move(r,c,r2,c-1,true));

        return moveList;
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
