package com.checkers;

public class Piece {
    public String color;
    public String rank;
    public int col;
    public int row;
    public boolean isTaken;

    public Piece(String color, String rank, int r, int c, boolean taken){
            this.color = color;
            this.rank = rank;
            this.row = r;
            this.col = c;
            this.isTaken = taken;
        }

    public void setLocation(int r, int c){
        row = r;
        col = c;
    }

    public void setTaken(){
        isTaken = true;
        row = -1;
        col = -1;
    }
}
