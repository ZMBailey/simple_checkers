package com.checkers;

import java.util.ArrayList;

public abstract class Piece {
    public String color;
    public boolean isTaken;
    public String rank;

    public Piece(String color, boolean taken){
            this.color = color;
            this.isTaken = taken;
        }

    public void setTaken(){
        isTaken = true;
    }

    public abstract Boolean isValidMove(Move m, Piece p2);

    public abstract ArrayList<Move> getMoveList(int r, int c, Piece [][] pieces);

    public Boolean isEnemy(Piece p){
        if(p == null){
            return false;
        }

        return !color.equals(p.color);
    }

    public Boolean isInc(String dir){
        return (dir.equals("Up") || dir.equals("Right"));
    }

    public Boolean checkEdge(int pos, String dir){
        if(dir.equals("Left") || dir.equals("Down")){
            return pos>=0;
        }else if(dir.equals("Right") || dir.equals("Up")){
            return pos<=7;
        }else{
            return false;
        }
    }
}
