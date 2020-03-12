package com.checkers;
import java.util.ArrayList;

/*
    Game Class for Checkers application. Contains primary game logic
    for a checkers game, implementing standard Checkers rules.
 */

public class Game {
    public Piece [][] pieces = new Piece[8][8];
    public ArrayList<Piece> red = new ArrayList<>();
    public ArrayList<Piece> blue = new ArrayList<>();

    public void initializeGame(){
        //set up board
        //rows
        for(int r=0;r<9;r++){
            //columns
            for(int c=0;c<9;c++){
                //check for black space
                if(isBlackSpace(r,c)){
                    if(r<3){
                        //red side of board
                        Piece pawn = new Piece("Red","Pawn",r,c,false);
                        red.add(pawn);
                        pieces[r][c] = pawn;
                    }else if(r>4) {
                        //blue side of board
                        Piece pawn = new Piece("Blue","Pawn",r,c,false);
                        blue.add(pawn);
                        pieces[r][c] = pawn;
                    }else{
                        pieces[r][c] = null;
                    }
                }else{
                    pieces[r][c] = null;
                }
            }
        }
    }

    public Boolean isBlackSpace(int r,int c){
        return (r % 2 == 0)^(c % 2 == 0);
    }

    public void move(Move m){
        Piece temp = pieces[m.r2][m.c2];
        pieces[m.r2][m.c2] = pieces[m.r1][m.c1];
        pieces[m.r1][m.c1] = temp;
    }

    public void jump(Move m){
        Piece temp = pieces[m.r2][m.c2];
        pieces[m.r2][m.c2] = pieces[m.r1][m.c1];
        int r3 = Math.max(m.r2, m.r1) - 1;
        int c3 = Math.max(m.c2, m.c1) - 1;
        pieces[r3][c3].isTaken = true;
        pieces[r3][c3] = null;
        pieces[m.r1][m.c1] = null;
    }

    private Boolean isValidDistance(Move m){
        return ((m.r2 == m.r1+1 || m.r2 == m.r1-1) && (m.c2 == m.c1+1 || m.c2 == m.c1-1));
    }

    private Boolean isValidDirection(Move m){
        Piece p = pieces[m.r1][m.c1];

        if(!p.rank.equals("King")){
            if(p.color.equals("Red") && m.r1>=m.r2){
                return false;
            }else{
                return !(p.color.equals("Blue") && m.r1<=m.r2);
            }
        }

        return true;
    }

    private Boolean isValidTarget(Move m){
        return pieces[m.r2][m.c2] == null;
    }

    private Boolean isJumpable(Move m){
        if(m.isJump){
            int r3 = Math.max(m.r2, m.r1) - 1;
            int c3 = Math.max(m.c2, m.c1) - 1;
            if(pieces[r3][c3] == null || (pieces[r3][c3].color.equals(pieces[m.r1][m.c1].color))) {
                return false;
            }
        }

        return true;
    }

    public Boolean isValidMove(Move m){
        //Check valid target space
        //Check empty target space
        //Check direction
        return (isValidDistance(m) && isValidTarget(m) && isValidDirection(m));

    }

    public Boolean isValidJump(Move m){
        return (isValidMove(m) && isJumpable(m));
    }

    private Piece makeKing(Piece p){
        p.rank = "King";
        return p;
    }
}
