package com.checkers;
import java.util.ArrayList;
import java.util.HashMap;
import android.util.Log;

/*
    Game Class for Checkers application. Contains primary game logic
    for a checkers game, implementing standard Checkers rules.
 */

public class CheckersGame {
    public CheckersPawn [][] pieces = new CheckersPawn[8][8];
    private String turn = "Red";
    private HashMap<String,Integer> blue;
    private HashMap<String,Integer> red;

    //Create new game, places pieces in starting positions
    public void initializeGame(){
        //set up board
        //rows
        turn = "Red";
        red = new HashMap<>();
        blue = new HashMap();
        red.put("Pawn",0);
        blue.put("Pawn",0);
        red.put("King",0);
        blue.put("King",0);

        for(int r=0;r<8;r++){
            //columns
            for(int c=0;c<8;c++){
                //check for black space
                if(isBlackSpace(r,c)){
                    if(r<3){
                        //red side of board
                        CheckersPawn pawn = new CheckersPawn("Red","Pawn",false);
                        pieces[r][c] = pawn;
                    }else if(r>4) {
                        //blue side of board
                        CheckersPawn pawn = new CheckersPawn("Blue","Pawn",false);
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

    //returns the current turn
    public String getTurn(){
        return turn;
    }

    //subtract one piece
    public void removePiece(String team,String type){
        HashMap<String,Integer> score;
        if(team.equals("Red")){
            red.put(type,red.get(type) + 1);
        } else {
            blue.put(type,blue.get(type) + 1);
        }

    }

    //Check if space at input coordinates is a black space.
    public Boolean isBlackSpace(int r,int c){
        return (r % 2 == 0)^(c % 2 == 0);
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
        CheckersPawn temp = pieces[m.r2][m.c2];
        pieces[m.r2][m.c2] = pieces[m.r1][m.c1];
        pieces[m.r1][m.c1] = temp;
    }

    //jump a over an enemy piece and capture it.
    public void jump(Move m){
        move(m);
        int r3 = Math.max(m.r2, m.r1) - 1;
        int c3 = Math.max(m.c2, m.c1) - 1;
        removePiece(pieces[r3][c3].color, pieces[r3][c3].rank);
        pieces[r3][c3].setTaken();
        pieces[r3][c3] = null;
    }

    //return if the selected piece belongs to the current player.
    private Boolean isTurn(Move m){
        return pieces[m.r1][m.c1].color.equals(turn);
    }


    //Promotes a piece to a king.
    private Piece makeKing(CheckersPawn p){
        p.rank = "King";
        return p;
    }

    //check for valid moves from a single space.
    public ArrayList<Move> checkForMoves(int r, int c){

        ArrayList<Move> valid_moves = new ArrayList<>();
        ArrayList<Move> moves = pieces[r][c].getMoveList(r,c);

        for(Move m : moves){
            CheckersPawn p = pieces[m.r1][m.c1];
            if(p.isValidMove(m, isTurn(m), pieces[m.r2][m.c2])){
                valid_moves.add(m);
                Log.i("move", m.r2 + ", " + m.c2);
            }
        }

        return valid_moves;
    }

    //check for valid jumps from a certain space.
    public ArrayList<Move> checkForJumps(int r, int c){

        ArrayList<Move> valid_moves = new ArrayList<>();
        ArrayList<Move> moves = pieces[r][c].getMoveList(r,c);

        for(Move m : moves){
            CheckersPawn p = pieces[m.r1][m.c1];
            int r3 = Math.max(m.r2, m.r1) - 1;
            int c3 = Math.max(m.c2, m.c1) - 1;
            if(p.isValidJump(m, isTurn(m), pieces[m.r2][m.c2], pieces[r3][c3])){
                valid_moves.add(m);
            }
        }

        return valid_moves;
    }

    //checks if the end space of a move is at the far edge of the board
    //(depends on the piece's color) and if so promotes the piece to a king.
    public void isKingSpace(Move m){
        CheckersPawn p = pieces[m.r1][m.c1];
        if((p.color.equals("Blue") && m.r2 == 0) ||
                (p.color.equals("Red") && m.r2 == 7)){
            makeKing(p);
        }
    }

    public String checkForWin(){
        if(blue.get("Pawn") + blue.get("King") == 12){
            return "Red";
        } else if(red.get("Pawn") + red.get("King") == 12){
            return "Blue";
        } else {
            return "None";
        }
    }
}
