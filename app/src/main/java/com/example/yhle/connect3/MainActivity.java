package com.example.yhle.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int activePlayer = 0; //0 is yellow, 1 is red
    //default empty state: 2
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    //possible winning states: tags of the winning positions
    int[][] winStates = {{0,1,2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //method to animate token drop in
    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        //get the tag property of each token, need to convert tag to string then to int
        int selectedToken = Integer.parseInt(counter.getTag().toString());
        gameState[selectedToken] = activePlayer;
        //dropping: shift view up, then animate drop down
        counter.setTranslationY(-1500);
        //set color of chip based on current player
        if (activePlayer == 0) {
            counter.setImageResource(R.drawable.yellow);
            activePlayer = 1;
        } else {
            counter.setImageResource(R.drawable.red);
            activePlayer = 0;
        }

        //animate
        counter.animate().translationYBy(1500).setDuration(300);
        winCheck();
    }

    public void winCheck() {
        //to win, all 3 win state positions must have the same activePlayer value
        for (int[] winState : winStates) {
            if (gameState[winState[0]] == gameState[winState[1]]
                    && gameState[winState[1]] == gameState[winState[2]] &&
                    gameState[winState[0]] !=2) {
                //show a toast for winner
                Toast.makeText(this, "Someone has won!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}