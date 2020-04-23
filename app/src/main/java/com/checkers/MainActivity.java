package com.checkers;

import androidx.appcompat.app.AppCompatActivity;
//import android.content.DialogInterface;
import android.content.DialogInterface;
import android.os.Handler;
import android.app.AlertDialog;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ImageView;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.graphics.Point;
import android.util.DisplayMetrics;
//import android.support.v7.app.AppCompatActivity;
//import android.app.AlertDialog;
//import android.view.Gravity;
import android.view.View;
//import android.view.ViewGroup;
import androidx.gridlayout.widget.GridLayout;
//import android.widget.RelativeLayout;

import java.util.ArrayList;
//import java.util.Locale;

import static android.R.color.holo_blue_light;

public class MainActivity extends AppCompatActivity {

    public ImageButton[][] spaces;
    public int side = 8;
    private Game mGame = new Game();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spaces = new ImageButton[side][side];

        setContentView(R.layout.activity_main);
        mGame.initializeGame();
        makeBoard();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePieces();
    }

    /*
    update piece counters
    set handlers for reset button(dialog)
    show turn textbox
     */

    //Create board with initial setup, creates gridlayout and sets up buttons
    protected void makeBoard(){

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float padding = 40 * getResources().getDisplayMetrics().density;
        float w = 1;
        Configuration config = getResources( ).getConfiguration( );

        try {
            NewGameHandler nh = new NewGameHandler();
            Button newGame = (Button) findViewById(R.id.reset_button);
            newGame.setOnClickListener(nh);

            //TextView user = (TextView) findViewById(R.id.player);
            //user.setText(Player.current.getName());

            //BackHandler gb = new BackHandler();
            //Button back = (Button) findViewById(R.id.back);
            //back.setOnClickListener(gb);
            GridLayout grid = (GridLayout) findViewById(R.id.board);
            if( config.orientation == Configuration.ORIENTATION_LANDSCAPE ) {
                w = (dm.widthPixels - padding)/ side;
            }else if( config.orientation == Configuration.ORIENTATION_PORTRAIT ) {
                w = (dm.widthPixels - padding)/ side;
            }

            grid.setColumnCount(side);
            grid.setRowCount(side);

//            if(Game.getType()<7){
            setButtons_Square(grid,(int)w);
//            }
//            else {
//                setButtons_Dmnd(bh, w);
//            }

        }catch(NullPointerException e){
            //showAlert(e);
        }
    }

    private int findColor(int r, int c){
        Piece p = mGame.pieces[r][c];
        if(p != null){
           if(p.color.equals("Blue")){
               if(p.rank.equals("Pawn")) {
                   return R.drawable.blue_pawn;
               } else {
                   return R.drawable.blue_king;
               }
           }else{
               if(p.rank.equals("Pawn")) {
                   return R.drawable.red_pawn;
               } else {
                   return R.drawable.red_king;
               }
           }
        }

        return -1;
    }

    //configures the spaces and places pieces
    private void setButtons_Square(GridLayout grid, int w){
        SelectPieceHandler sh = new SelectPieceHandler();

        for(int row=0; row< side; row++){
            for(int col=0; col<side; col++) {
                String id = Integer.toString(row)+Integer.toString(col);
                spaces[row][col] = new ImageButton(this);
                spaces[row][col].setId(View.generateViewId());
                if(findColor(row,col) != -1) {
                    if(mGame.pieces[row][col].color.equals(mGame.getTurn())){
                        spaces[row][col].setOnClickListener(sh);
                    }
                    spaces[row][col].setImageDrawable(getResources().getDrawable(findColor(row,col), null));
                    spaces[row][col].setAdjustViewBounds(true);
                    spaces[row][col].setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
                if (!mGame.isBlackSpace(row, col)) {
                        //place white space
                    spaces[row][col].setBackground(getResources().getDrawable(android.R.color.white, null));
                } else {
                        //place black space
                    spaces[row][col].setBackground(getResources().getDrawable(android.R.color.black, null));
                        //check if space is occupied
                }
                //spaces[row][col].setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_blue_light));
                spaces[row][col].setId(Integer.parseInt(id));
                grid.addView(spaces[row][col],w,w);
            }
        }
    }

    public void updatePieces(){
        for(int row=0; row< side; row++) {
            for (int col = 0; col < side; col++) {
                if(findColor(row,col) != -1) {
                    spaces[row][col].setImageDrawable(getResources().getDrawable(findColor(row,col), null));
                    spaces[row][col].setAdjustViewBounds(true);
                    spaces[row][col].setScaleType(ImageView.ScaleType.FIT_CENTER);
                }else {
                    spaces[row][col].setImageDrawable(null);
                }
            }
        }
    }

    private void resetHandlers(){
        SelectPieceHandler sh = new SelectPieceHandler();

        for(int r=0; r< side; r++){
            for(int c=0; c<side; c++) {
                if(findColor(r,c) != -1) {
                    if(mGame.pieces[r][c].color.equals(mGame.getTurn())){
                        spaces[r][c].setOnClickListener(sh);
                    }
                }
                if (mGame.isBlackSpace(r, c)) {
                    spaces[r][c].setBackground(getResources().getDrawable(android.R.color.black, null));
                }
            }
        }
    }

    //shows a message dialog
    private void showWinMsg(String m){
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Game Over");
        dialog.setMessage(m);
        RestartHandler rh = new RestartHandler();
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Start New Game", rh);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", rh);
        dialog.show();
    }

    private void gameOver(){
        String winner = mGame.checkForWin();
        if(!winner.equals("None")){
            showWinMsg(winner + " has won! Play again?");
            //show winning/game over dialog
        }
    }

    private Boolean selectSpace(View v, Boolean isMove){
        int id = v.getId();
        int row;
        int col;
        if(id<10){
            row = 0;
            col = id;
        }else {
            row = Character.getNumericValue(Integer.toString(id).charAt(0));
            col = Character.getNumericValue(Integer.toString(id).charAt(1));
        }

        Log.i("origin: ", row + ", " + col);
        for(int r=0; r< side; r++){
            for(int c=0; c<side; c++) {
                spaces[r][c].setOnClickListener(null);
            }
        }

        ArrayList<Move> moves = mGame.checkForJumps(row,col);

        if(moves.size() == 0 && isMove){
            moves = mGame.checkForMoves(row,col);
        }

        for(Move m: moves){
            if(m.isJump){
                spaces[m.r2][m.c2].setOnClickListener(new JumpHighlightHandler(m));
            } else {
                spaces[m.r2][m.c2].setOnClickListener(new MoveHighlightHandler(m));
            }
            Log.i("Highlighting Move: ", m.r2 + ", " + m.c2);
            spaces[m.r2][m.c2].setBackground(getResources().getDrawable(android.R.color.holo_green_light, null));
        }

        v.setBackground(getResources().getDrawable(holo_blue_light, null));
        if(moves.size() > 0 && isMove) {
            v.setOnClickListener(new UnSelectHandler());
        }

        return moves.size() == 0;
    }

    private class SelectPieceHandler implements View.OnClickListener {
        private Boolean selected = false;
        private Boolean highlighted = false;

        public void onClick(View v) {
            selectSpace(v,true);
        }
    }

    private class UnSelectHandler implements View.OnClickListener {

        public void onClick(View v){
            resetHandlers();
        }
    }

    private class MoveHighlightHandler implements View.OnClickListener {
        private Move m;

        private MoveHighlightHandler(Move m){
            this.m = m;
        }

        @Override
        public void onClick(View v) {
            mGame.move(m);
            mGame.newTurn();
            updatePieces();
            resetHandlers();
        }
    }

    private class JumpHighlightHandler implements View.OnClickListener {
        private Move m;

        private JumpHighlightHandler(Move m){
            this.m = m;
        }

        @Override
        public void onClick(View v) {
            mGame.jump(m);
            updatePieces();
            resetHandlers();
            Boolean turnOver = selectSpace(v, false);
            if(turnOver) {
                gameOver();
                mGame.newTurn();
                updatePieces();
                resetHandlers();
            }
        }
    }

    private class NewGameHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            showWinMsg("Your current game will be lost./n" +
                    "Would you like to restart?");
        }
    }

    private class RestartHandler implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //restart
                    mGame.initializeGame();
                    updatePieces();
                    resetHandlers();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    //close
                    break;
            }

        }
    }

}
