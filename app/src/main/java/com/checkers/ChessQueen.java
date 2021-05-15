package com.checkers;

import java.util.ArrayList;

public class ChessQueen extends Piece {
    public final String rank = "Queen";
    private Piece rook;
    private Piece bishop;

    public ChessQueen(String color, Boolean taken){

        super(color,taken);
        rook = new ChessRook(color,taken);
        bishop = new ChessBishop(color,taken);
    }

    @Override
    public Boolean isValidMove(Move m, Piece p2) {
        return null;
    }

    @Override
    public ArrayList<Move> getMoveList(int r, int c, Piece[][] pieces) {

        ArrayList<Move> bishop_moves = bishop.getMoveList(r,c,pieces);
        ArrayList<Move> rook_moves = rook.getMoveList(r,c,pieces);
        ArrayList<Move> valid_moves = new ArrayList<>();

        valid_moves.addAll(rook_moves);
        valid_moves.addAll(bishop_moves);
        return valid_moves;
    }
}
