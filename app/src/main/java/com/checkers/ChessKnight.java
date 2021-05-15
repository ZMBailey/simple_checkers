package com.checkers;

import java.util.ArrayList;

public class ChessKnight extends Piece {
    public final String rank = "Knight";

    public ChessKnight(String color, Boolean taken){
        super(color,taken);
    }

    @Override
    public Boolean isValidMove(Move m, Piece p2) {

        if(p2 == null){
            return true;
        }

        return isEnemy(p2);
    }

    @Override
    public ArrayList<Move> getMoveList(int r, int c, Piece [][] pieces) {

        Piece p2;

        ArrayList<Move> valid_moves = new ArrayList<>();
        ArrayList<Move> possible_moves = new ArrayList<>();
        possible_moves.add(new Move(r,c,r+2,c+1,false));
        possible_moves.add(new Move(r,c,r+2,c-1,false));

        possible_moves.add(new Move(r,c,r-2,c-1,false));
        possible_moves.add(new Move(r,c,r-2,c-1,false));

        possible_moves.add(new Move(r,c,r+1,c-2,false));
        possible_moves.add(new Move(r,c,r+1,c+2,false));

        possible_moves.add(new Move(r,c,r-1,c-2,false));
        possible_moves.add(new Move(r,c,r-1,c+2,false));

        for(Move m: possible_moves){
            p2 = pieces[m.r2][m.c2];
            if(isValidMove(m,p2)){
                if(isEnemy(p2)){
                    m.isJump = true;
                }
                valid_moves.add(m);
            }
        }

        return valid_moves;
    }
}
