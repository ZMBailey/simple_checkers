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

    private ArrayList<Move> checkLeft(int r, int c, Piece [][] pieces){

        ArrayList<Move> valid = new ArrayList<>();
        if(c<1){
            return valid;
        }

        int c2 = c-1;

        while(c2>=0){
            Move m = new Move(r,c,r,c2,false);
            if(isValidMove(m, pieces[m.r2][m.c2])){
                if(isEnemy(pieces[m.r2][m.c2])){
                    m.isJump = true;
                    valid.add(m);
                    return valid;
                }
                valid.add(m);
            }
            c2--;
        }

        return valid;
    }

    private ArrayList<Move> checkRight(int r, int c, Piece [][] pieces){

        ArrayList<Move> valid = new ArrayList<>();
        if(c>6){
            return valid;
        }

        int c2 = c+1;

        while(c2<=7){
            Move m = new Move(r,c,r,c2,false);
            if(isValidMove(m, pieces[m.r2][m.c2])){
                if(isEnemy(pieces[m.r2][m.c2])){
                    m.isJump = true;
                    valid.add(m);
                    return valid;
                }
                valid.add(m);
            }
            c2++;
        }

        return valid;
    }

    private ArrayList<Move> checkUp(int r, int c, Piece [][] pieces){

        ArrayList<Move> valid = new ArrayList<>();
        if(r>6){
            return valid;
        }

        int r2 = r+1;

        while(r2>=0){
            Move m = new Move(r,c,r2,c,false);
            if(isValidMove(m, pieces[m.r2][m.c2])){
                if(isEnemy(pieces[m.r2][m.c2])){
                    m.isJump = true;
                    valid.add(m);
                    return valid;
                }
                valid.add(m);
            }
            r2++;
        }

        return valid;
    }

    private ArrayList<Move> checkDown(int r, int c, Piece [][] pieces){

        ArrayList<Move> valid = new ArrayList<>();
        if(r<1){
            return valid;
        }

        int r2 = r-1;

        while(r2>=0){
            Move m = new Move(r,c,r2,c,false);
            if(isValidMove(m, pieces[m.r2][m.c2])){
                if(isEnemy(pieces[m.r2][m.c2])){
                    m.isJump = true;
                    valid.add(m);
                    return valid;
                }
                valid.add(m);
            }
            r2--;
        }

        return valid;
    }

    private Boolean isEnemy(Piece p){
        if(p == null){
            return false;
        }

        return !color.equals(p.color);
    }

    @Override
    public ArrayList<Move> getMoveList(int r, int c, Piece [][] pieces) {

        ArrayList<Move> valid_moves = new ArrayList<>();
        ArrayList<Move> left = checkLeft(r,c,pieces);
        ArrayList<Move> right = checkRight(r,c,pieces);
        ArrayList<Move> up = checkUp(r,c,pieces);
        ArrayList<Move> down = checkDown(r,c,pieces);

        valid_moves.addAll(left);
        valid_moves.addAll(right);
        valid_moves.addAll(up);
        valid_moves.addAll(down);

        return valid_moves;
    }
}
