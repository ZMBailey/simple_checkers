package com.checkers;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/*
    Game Class for Checkers application. Contains primary game logic
    for a checkers game, implementing standard Checkers rules.
 */

public class ChessGame {
    public Piece [][] pieces = new Piece[8][8];
    private String turn = "Red";

    //Create new game, places pieces in starting positions
    public void initializeGame(){
        //set up board
        //rows
        turn = "Red";

        for(int r=0;r<8;r++){
            //columns
            for(int c=0;c<8;c++){
                //check for black space
                pieces[r][c] = findPiece(r,c);
            }
        }
    }

    //returns the current turn
    public String getTurn(){
        return turn;
    }


    //Check if space at input coordinates is a black space.
    public Piece findPiece(int r,int c){
        String color = "Black";
        String rank;
        if(r<2){
            color = "Black";
        }else if(r>5){
            color = "White";
        }else{
            return null;
        }

        if(r == 0 || r == 7){
            rank = findRank(c);
        }else{
            rank = "Pawn";
        }

        return new Piece(color,rank,r,c,false);
    }

    public String findRank(int c){
        if(c == 0 || c == 7){
            return "Rook";
        }else if(c == 1 || c == 6){
            return "Knight";
        }else if(c == 2 || c == 5){
            return "Bishop";
        }else if(c == 3){
            return "Queen";
        }else{
            return "King";
        }
    }

    //Switch turns between red and blue.
    public String newTurn(){
        if(turn.equals("Red")){
            turn = "Blue";
        } else {
            turn = "Red";
        }

        return turn;
    }

    //move a piece between two spaces.
    public void move(Move m){
        isKingSpace(m);
        Piece temp = pieces[m.r2][m.c2];
        pieces[m.r2][m.c2] = pieces[m.r1][m.c1];
        pieces[m.r1][m.c1] = temp;
        pieces[m.r2][m.c2].setLocation(m.r2,m.c2);
    }

    //jump a over an enemy piece and capture it.
    public void jump(Move m){
        move(m);
        int r3 = Math.max(m.r2, m.r1) - 1;
        int c3 = Math.max(m.c2, m.c1) - 1;
        pieces[r3][c3].setTaken();
        pieces[r3][c3] = null;
    }

    //return if the selected piece belongs to the current player.
    private Boolean isTurn(Move m){
        return pieces[m.r1][m.c1].color.equals(turn);
    }

    //moves for:
    //Pawn
    //Rook
    //Knight
    //Bishop
    //Queen
    //King

    //------------------------------------Pawn-----------------------------------
    //check if the selected space is one or two spaces diagonally, depending on
    //whether or not the move is a jump.
    private Boolean checkPawn(Move m, String color){

        if(color.equals("Black") || m.r2 == m.r1 - 1){
            return true;
        }else if(color.equals("White") || m.r2 == m.r1 + 1){
            return true;
        }else{
            return false;
        }

    }

    //------------------------------------Rook------------------------------------
    private Boolean checkRook(Move m){

        if(m.r2 == m.r1 || m.c2 == m.c1){
            return true;
        }else{
            return false;
        }

    }

    //------------------------------------Rook------------------------------------
    private Boolean checkKnight(Move m){

        if((m.r2 == m.r1 + 1 && m.c2 == m.c1 + 2) ||
                (m.r2 == m.r1 + 1 && m.c2 == m.c1 - 2) ||
                (m.r2 == m.r1 - 1 && m.c2 == m.c1 + 2) ||
                (m.r2 == m.r1 - 1 && m.c2 == m.c1 - 2) ||
                (m.r2 == m.r1 + 2 && m.c2 == m.c1 + 1) ||
                (m.r2 == m.r1 - 2 && m.c2 == m.c1 + 1) ||
                (m.r2 == m.r1 + 2 && m.c2 == m.c1 - 1) ||
                (m.r2 == m.r1 - 2 && m.c2 == m.c1 - 1)){
            return true;
        }else{
            return false;
        }

    }

    //returns true if the selected space is empty, also checks to make sure the space is on the board.
    private Boolean isValidTarget(Move m){
        if(m.r2 < 0 || m.c2 < 0 || m.r2 > 7 || m.c2 > 7){
            return false;
        }

        return pieces[m.r2][m.c2] == null;
    }

    //checks if there is a piece to be captured, and makes sure it is of the opposing color.
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

    /* check if the input move is a valid move:
    * if the target space is the correct distance away.
    * if the target space is empty, and on the board.
    * if the target space is forward(if the piece is not a king)
    */
    public Boolean isValidMove(Move m){
        //Check valid target space
        //Check empty target space
        //Check direction
        Piece p = pieces[m.r1][m.c1];

        return (checkPawn(m, p.color) && isValidTarget(m) && isTurn(m));

    }


    //----------------------------------------------------------------

//    //calls isValidMove() and also checks if there is a piece that can be jumped.
//    public Boolean isValidJump(Move m){
//        return (isValidMove(m) && isJumpable(m));
//    }

    //Promotes a piece to a queen.
    private Piece makeQueen(Piece p){
        p.rank = "Queen";
        return p;
    }

    //check for valid moves from a single space.
    public ArrayList<Move> checkForMoves(int r, int c){
        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<Move> valid_moves = new ArrayList<>();

        moves.add(new Move(r,c,r-1,c-1,false));
        moves.add(new Move(r,c,r-1,c+1,false));
        moves.add(new Move(r,c,r+1,c-1,false));
        moves.add(new Move(r,c,r+1,c+1,false));

        for(Move m : moves){
            if(isValidMove(m)){
                valid_moves.add(m);
                Log.i("move", m.r2 + ", " + m.c2);
            }
        }

        return valid_moves;
    }

//    //check for valid jumps from a certain space.
//    public ArrayList<Move> checkForJumps(int r, int c){
//        ArrayList<Move> moves = new ArrayList<>();
//        ArrayList<Move> valid_moves = new ArrayList<>();
//
//        moves.add(new Move(r,c,r-2,c-2,true));
//        moves.add(new Move(r,c,r-2,c+2,true));
//        moves.add(new Move(r,c,r+2,c-2,true));
//        moves.add(new Move(r,c,r+2,c+2,true));
//
//        for(Move m : moves){
//            if(isValidJump(m)){
//                valid_moves.add(m);
//            }
//        }
//
//        return valid_moves;
//    }

    //checks if the end space of a move is at the far edge of the board
    //(depends on the piece's color) and if so promotes the piece to a king.
    public void isKingSpace(Move m){
        Piece p = pieces[m.r1][m.c1];
        if((p.color.equals("Blue") && m.r2 == 0) ||
                (p.color.equals("Red") && m.r2 == 7)){
            makeQueen(p);
        }
    }

    public String checkForWin(){
        return null;
    }
}
