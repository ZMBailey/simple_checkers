package com.checkers;

import androidx.appcompat.app.AppCompatActivity;
//import android.content.DialogInterface;
import android.content.res.Configuration;
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

    public View spaces[][];
    public String pieces[][];
    public int side = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spaces = new View[side][side];

        setContentView(R.layout.activity_main);

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

    private Boolean isWhiteSpace(int r,int c){
        return (r % 2 == 0)==(c % 2 == 0);
    }

    //configures buttons for a square layout
    private void setButtons_Square(GridLayout grid, int w){

        for(int row=0; row< side; row++){
            for(int col=0; col<side; col++) {
                String id = Integer.toString(row)+Integer.toString(col);
                spaces[row][col] = new View(this);
                //spaces[row][col].setOnClickListener(bh);
                spaces[row][col].setId(View.generateViewId());
                spaces[row][col].setBackground(getResources().getDrawable(holo_blue_light,null));
                if(isWhiteSpace(row,col)) {
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
