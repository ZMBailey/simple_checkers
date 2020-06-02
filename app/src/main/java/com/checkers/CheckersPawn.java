package com.checkers;

import java.util.ArrayList;

import com.checkers.Piece;

public class CheckersPawn extends Piece {

    public String rank;

    public CheckersPawn(String color, String rank, Boolean taken){
        super(color,taken);
        this.rank = rank;
    }

    @Override
    public Boolean isValidMove(Move m, Boolean turn, Piece p2) {
        //Check valid target space
        //Check empty target space
        //Check direction
        return (isValidDistance(m) && isValidTarget(m, p2) && turn && isValidDirection(m));
    }

    @Override
    public ArrayList<Move> getMoveList(int r, int c) {
        ArrayList<Move> moves = new ArrayList<>();

        moves.add(new Move(r,c,r-1,c-1,false));
        moves.add(new Move(r,c,r-1,c+1,false));
        moves.add(new Move(r,c,r+1,c-1,false));
        moves.add(new Move(r,c,r+1,c+1,false));

        return moves;
    }

    public ArrayList<Move> getJumpList(int r, int c) {
        ArrayList<Move> moves = new ArrayList<>();

        moves.add(new Move(r,c,r-2,c-2,true));
        moves.add(new Move(r,c,r-2,c+2,true));
        moves.add(new Move(r,c,r+2,c-2,true));
        moves.add(new Move(r,c,r+2,c+2,true));

        return moves;
    }

    //check if the selected space is one or two spaces diagonally, depending on
    //whether or not the move is a jump.
    private Boolean isValidDistance(Move m){
        int d = 1;
        if(m.isJump){
            d = 2;
        }
        return ((m.r2 == m.r1+d || m.r2 == m.r1-d) && (m.c2 == m.c1+d || m.c2 == m.c1-d));
    }

    //If the piece is not a king then check if the move is forward, depending on the color.
    private Boolean isValidDirection(Move m){

        if(!rank.equals("King")){
            if(color.equals("Red") && m.r1>=m.r2){
                return false;
            }else{
                return !(color.equals("Blue") && m.r1<=m.r2);
            }
        }

        return true;
    }

    //returns true if the selected space is empty, also checks to make sure the space is on the board.
    private Boolean isValidTarget(Move m, Piece p2){
        if(m.r2 < 0 || m.c2 < 0 || m.r2 > 7 || m.c2 > 7){
            return false;
        }

        return p2 == null;
    }

    //checks if there is a piece to be captured, and makes sure it is of the opposing color.
    private Boolean isJumpable(Move m, CheckersPawn p3){
        if(m.isJump){
            if(p3 == null || (p3.color.equals(color))) {
                return false;
            }
        }

        return true;
    }

    /* check if the input move is a valid move:
     * if the target space is the correct distance away.
     * if the target space is empty, and on the board.
     * if the target space is forward(if the piece is not a king)
     */

    //calls isValidMove() and also checks if there is a piece that can be jumped.
    public Boolean isValidJump(Move m, Boolean turn, CheckersPawn p2, CheckersPawn p3){
        return (isValidMove(m, turn, p2) && isJumpable(m, p3));
    }

}
