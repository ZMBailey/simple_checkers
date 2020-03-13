package com.checkers;

import androidx.appcompat.app.AppCompatActivity;
//import android.content.DialogInterface;
import android.widget.ImageButton;
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

//import java.util.Arrays;
//import java.util.Locale;

import static android.R.color.holo_blue_light;

public class MainActivity extends AppCompatActivity {

    public ImageButton spaces[][];
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

    protected void makeBoard(){

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float padding = 40 * getResources().getDisplayMetrics().density;
        float w = 1;
        Configuration config = getResources( ).getConfiguration( );

        try {
            //ColorButtonHandler bh = new ColorButtonHandler();
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
               return R.drawable.blue_pawn;
           }else{
               return R.drawable.red_pawn;
           }
        }

        return -1;
    }

    //configures buttons for a square layout
    private void setButtons_Square(GridLayout grid, int w){

        for(int row=0; row< side; row++){
            for(int col=0; col<side; col++) {
                String id = Integer.toString(row)+Integer.toString(col);
                spaces[row][col] = new ImageButton(this);
                //spaces[row][col].setOnClickListener(bh);
                spaces[row][col].setId(View.generateViewId());
                if(findColor(row,col) != -1) {
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
}
