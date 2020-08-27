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
            if(isValidMove(m, pieces[m.r2][m.c2])){
                if(isEnemy(pieces[m.r2][m.c2])){
                    m.isJump = true;
                    valid.add(m);
                    return valid;
                }
                valid.add(m);
            }
            if(increment){
                m2[pos]++;
            }else{
                m2[pos]--;
            }
        }

        return valid;
    }

    private Boolean isInc(String dir){
        if(dir.equals("Up") || dir.equals("Right")) {
            return true;
        }else{
            return false;
        }
    }

    private Boolean checkEdge(int pos, String dir){
        if(dir.equals("Left") || dir.equals("Down")){
            return pos>=0;
        }else if(dir.equals("Right") || dir.equals("Up")){
            return pos<=7;
        }else{
            return false;
        }
    }

    private Boolean isEnemy(Piece p){
        if(p == null){
            return false;
        }

        return !color.equals(p.color);
    }

    @Override
    public ArrayList<Move> getMoveList(int r, int c, Piece [][] pieces) {
        return null;
    }

}
