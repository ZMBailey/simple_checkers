package com.checkers;

import androidx.appcompat.app.AppCompatActivity;
//import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.graphics.Point;
//import android.support.v7.app.AppCompatActivity;
//import android.app.AlertDialog;
//import android.view.Gravity;
import android.view.View;
//import android.view.ViewGroup;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
//import android.widget.RelativeLayout;
import android.widget.TextView;

//import java.util.Arrays;
//import java.util.Locale;

import static android.R.drawable.alert_light_frame;
import static android.R.color.holo_blue_light;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public Button spaces[][];
    public int side = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spaces = new Button[side][side];

        setContentView(R.layout.activity_main);

        makeBoard();
    }

    protected void makeBoard(){

        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);
        int w = 1;
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
                w = (p.y / side)/2;
            }else if( config.orientation == Configuration.ORIENTATION_PORTRAIT ) {
                w = p.x / side;
            }

            grid.setColumnCount(side);
            grid.setRowCount(side);

//            if(Game.getType()<7){
            setButtons_Square(grid,w);
//            }
//            else {
//                setButtons_Dmnd(bh, w);
//            }

        }catch(NullPointerException e){
            //showAlert(e);
        }
    }

    //configures buttons for a square layout
    private void setButtons_Square(GridLayout grid, int w){

        for(int row=0; row< side; row++){
            for(int col=0; col<side; col++) {
                String id = Integer.toString(row)+Integer.toString(col);
                spaces[row][col] = new Button(this);
                //spaces[row][col].setOnClickListener(bh);
                spaces[row][col].setId(View.generateViewId());
                spaces[row][col].setBackground(getResources().getDrawable(alert_light_frame,null));
                spaces[row][col].setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_blue_light));
                spaces[row][col].setId(Integer.parseInt(id));
                grid.addView(spaces[row][col],w,w);
            }
        }
    }
}
