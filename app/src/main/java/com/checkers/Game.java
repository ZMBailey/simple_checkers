package com.checkers;

public class Game {
    public String [][] pieces = new String[8][8];

    public void initializeGame(){
        for(int r=0;r<9;r++){
            for(int c=0;c<9;c++){
                if(!isWhiteSpace(r,c)){
                    if(r<3){
                        pieces[r][c] = "Red Pawn";
                    }else if(r>4) {
                        pieces[r][c] = "Blue Pawn";
                    }else{
                        pieces[r][c] = "Empty";
                    }
                }else{
                    pieces[r][c] = "Empty";
                }
            }
        }
    }

    public Boolean isWhiteSpace(int r,int c){
        return (r % 2 == 0)==(c % 2 == 0);
    }

    public void move(int r_start, int c_start, int r_end, int c_end){
        String temp = pieces[r_end][c_end];
        pieces[r_end][c_end] = pieces[r_start][c_start];
        pieces[r_start][c_start] = "Empty";
    }

    public void takePiece(int r_start, int c_start, int r_end, int c_end){
        String temp = pieces[r_end][c_end];
        pieces[r_end][c_end] = pieces[r_start][c_start];
        pieces[r_start][c_start] = "Empty";
    }

    private Boolean isValidMove(int r_start, int c_start, int r_end, int c_end){

        return true;
    }
}
