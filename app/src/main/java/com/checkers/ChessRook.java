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

    private ArrayList<Move> checkDirection(int r, int c, Piece [][] pieces, String dir){

        ArrayList<Move> valid = new ArrayList<>();

        int[] m2 = new int[2];
        int pos = 0;
        Boolean increment = isInc(dir);

        switch (dir) {
            case "Left":
                if (c < 1) {
                    return valid;
                }

                m2[1] = c - 1;
                pos = 1;
                break;
            case "Right":
                if(c>6){
                    return valid;
                }

                m2[1] = c+1;
                pos = 1;
                break;
            case "Up":
                if(r>6){
                    return valid;
                }

                m2[0] = r+1;
                pos = 0;
                break;
            case "Down":
                if(r<1){
                    return valid;
                }

                m2[0] = r-1;
                pos = 0;
                break;
        }

        while(checkEdge(m2[pos],dir)){
            Move m = new Move(r,c,m2[0],m2[1],false);
            if(isEnemy(pieces[m.r2][m.c2])){
                m.isJump = true;
                valid.add(m);
                return valid;
            }
            valid.add(m);
            if(increment){
                m2[pos]++;
            }else{
                m2[pos]--;
            }
        }

        return valid;
    }

    @Override
    public ArrayList<Move> getMoveList(int r, int c, Piece [][] pieces) {

        ArrayList<Move> valid_moves = new ArrayList<>();
        ArrayList<Move> left = checkDirection(r,c,pieces,"Left");
        ArrayList<Move> right = checkDirection(r,c,pieces, "Right");
        ArrayList<Move> up = checkDirection(r,c,pieces, "Up");
        ArrayList<Move> down = checkDirection(r,c,pieces, "Down");

        valid_moves.addAll(left);
        valid_moves.addAll(right);
        valid_moves.addAll(up);
        valid_moves.addAll(down);

        return valid_moves;
    }
}
