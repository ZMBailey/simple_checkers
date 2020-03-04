package com.checkers;

public class Move {
    int r1;
    int c1;
    int r2;
    int c2;
    boolean isJump;

    public Move(int r1, int c1, int r2, int c2, boolean jump){
        this.r1 = r1;
        this.c1 = c1;
        this.r2 = r2;
        this.c2 = c2;
        this.isJump = jump;
    }
}
