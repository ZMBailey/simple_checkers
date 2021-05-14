package com.checkers;

import java.util.ArrayList;

public class ChessBishop extends Piece {
    public final String rank = "Bishop";

    public ChessBishop(String color, Boolean taken){
        super(color,taken);
    }

    @Override
    public Boolean isValidMove(Move m, Piece p2) {
        return null;
    }

    private ArrayList<Move> checkDirection(int r, int c, Piece [][] pieces, String dir1, String dir2){

        ArrayList<Move> valid = new ArrayList<>();

        int[] m2 = new int[2];
        int pos = 0;
        int r2 = r;
        int c2 = c;
        Boolean inc_col = isInc(dir1);
        Boolean inc_row = isInc(dir2);

        if(dir1.equals("Left") && dir2.equals("Up")){
            if (c < 1 && r > 6) {
                return valid;
            }

            c2 = c - 1;
            r2 = r + 1;
            pos = 1;
        }else if(dir1.equals("Left") && dir2.equals("Down")){
            if (c < 1 && r < 1) {
                return valid;
            }

            c2 = c - 1;
            r2 = r - 1;
            pos = 1;
        }else if(dir1.equals("Right") && dir2.equals("Up")) {
            if (c > 6 && r > 6) {
                return valid;
            }

            c2 = c + 1;
            r2 = r + 1;
        }else if(dir1.equals("Right") && dir2.equals("Down")) {
            if (c > 6 && r < 1) {
                return valid;
            }

            c2 = c + 1;
            r2 = r - 1;
        }

        while(checkEdge(c2,dir1) && checkEdge(r2,dir2)){
            Move m = new Move(r,c,r2,c2,false);
            if(isEnemy(pieces[m.r2][m.c2])){
                m.isJump = true;
                valid.add(m);
                return valid;
            }
            valid.add(m);
            r2 = nextSpace(r2,inc_row);
            c2 = nextSpace(c2,inc_col);
        }

        return valid;
    }

    private int nextSpace(int a,Boolean inc){
        if(inc){
            return a+1;
        }else{
            return a-1;
        }
    }

    @Override
    public ArrayList<Move> getMoveList(int r, int c, Piece [][] pieces) {
        ArrayList<Move> valid_moves = new ArrayList<>();
        ArrayList<Move> left_down = checkDirection(r,c,pieces,"Left", "Down");
        ArrayList<Move> left_up = checkDirection(r,c,pieces, "Left", "UP");
        ArrayList<Move> right_down = checkDirection(r,c,pieces, "Right", "Down");
        ArrayList<Move> right_up = checkDirection(r,c,pieces, "Right", "Up");

        valid_moves.addAll(left_down);
        valid_moves.addAll(left_up);
        valid_moves.addAll(right_down);
        valid_moves.addAll(right_up);

        return valid_moves;
    }

}
