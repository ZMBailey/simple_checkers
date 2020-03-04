package com.checkers;
import java.util.ArrayList;

public class Game {
    public Piece [][] pieces = new Piece[8][8];
    public ArrayList<Piece> red = new ArrayList<>();
    public ArrayList<Piece> blue = new ArrayList<>();

    public void initializeGame(){
        for(int r=0;r<9;r++){
            for(int c=0;c<9;c++){
                if(!isWhiteSpace(r,c)){
                    if(r<3){
                        Piece pawn = new Piece("Red","Pawn",r,c,false);
                        red.add(pawn);
                        pieces[r][c] = pawn;
                    }else if(r>4) {
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

    public Boolean isWhiteSpace(int r,int c){
        return (r % 2 == 0)==(c % 2 == 0);
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

    private Boolean isValidMove(int r_start, int c_start, int r_end, int c_end){

        return true;
    }

    private Piece makeKing(Piece p){
        p.rank = "King";
        return p;
    }
}
