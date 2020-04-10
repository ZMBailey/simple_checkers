package com.checkers;
import java.util.ArrayList;
import java.util.HashMap;
import android.util.Log;

/*
    Game Class for Checkers application. Contains primary game logic
    for a checkers game, implementing standard Checkers rules.
 */

public class Game {
    public Piece [][] pieces = new Piece[8][8];
    private String turn = "Red";
    private HashMap<String,Integer> blue;
    private HashMap<String,Integer> red;

    //Create new game, places pieces in starting positions
    public void initializeGame(){
        //set up board
        //rows
        red = new HashMap<>();
        blue = new HashMap();
        red.put("Pawn",11);
        blue.put("Pawn",11);
        red.put("King",0);
        blue.put("King",0);

        for(int r=0;r<8;r++){
            //columns
            for(int c=0;c<8;c++){
                //check for black space
                if(isBlackSpace(r,c)){
                    if(r<3){
                        //red side of board
                        Piece pawn = new Piece("Red","Pawn",r,c,false);
                        pieces[r][c] = pawn;
                    }else if(r>4) {
                        //blue side of board
                        Piece pawn = new Piece("Blue","Pawn",r,c,false);
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
            red.put(type,red.get(type) - 1);
        } else {
            blue.put(type,blue.get(type) - 1);
        }

    }

    //add one king
    public void addKing(String team){
        removePiece(team,"Pawn");
        if(team.equals("Red")){
            red.put("King",red.get("King") + 1);
        } else {
            blue.put("King",blue.get("King") + 1);
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
        removePiece(pieces[r3][c3].color, pieces[r3][c3].rank);
        pieces[r3][c3].setTaken();
        pieces[r3][c3] = null;
    }

    //return if the selected piece belongs to the current player.
    private Boolean isTurn(Move m){
        return pieces[m.r1][m.c1].color.equals(turn);
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
        return (isValidDistance(m) && isValidTarget(m) && isTurn(m) && isValidDirection(m));

    }

    //calls isValidMove() and also checks if there is a piece that can be jumped.
    public Boolean isValidJump(Move m){
        return (isValidMove(m) && isJumpable(m));
    }

    //Promotes a piece to a king.
    private Piece makeKing(Piece p){
        p.rank = "King";
        addKing(p.color);
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

    //check for valid jumps from a certain space.
    public ArrayList<Move> checkForJumps(int r, int c){
        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<Move> valid_moves = new ArrayList<>();

        moves.add(new Move(r,c,r-2,c-2,true));
        moves.add(new Move(r,c,r-2,c+2,true));
        moves.add(new Move(r,c,r+2,c-2,true));
        moves.add(new Move(r,c,r+2,c+2,true));

        for(Move m : moves){
            if(isValidJump(m)){
                valid_moves.add(m);
            }
        }

        return valid_moves;
    }

    //checks if the end space of a move is at the far edge of the board
    //(depends on the piece's color) and if so promotes the piece to a king.
    public void isKingSpace(Move m){
        Piece p = pieces[m.r1][m.c1];
        if((p.color.equals("Blue") && m.r2 == 0) ||
                (p.color.equals("Red") && m.r2 == 7)){
            makeKing(p);
        }
    }
}
