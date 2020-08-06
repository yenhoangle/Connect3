package com.example.yhle.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int activePlayer = 0; //0 is yellow, 1 is red
    //default empty state: 2
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    //possible winning states: tags of the winning positions
    int[][] winStates = {{0,1,2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4,6}};

    boolean activeState = true; //game active state, false when game is over
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selectSquare(View view) {
        ImageView squareView = (ImageView) view;
        //get the tag property of each token, need to convert tag to string then to int
        int selectedToken = Integer.parseInt(squareView.getTag().toString());
        //check to see if selected square is empty
        if (gameState[selectedToken] == 2 && activeState) {
            dropIn(squareView, selectedToken);
        }
    }

    //method to animate token drop in
    public void dropIn(ImageView squareView, int selectedToken) {
        gameState[selectedToken] = activePlayer;
        //dropping: shift view up, then animate drop down
        squareView.setTranslationY(-1500);
        //set color of chip based on current player
        if (activePlayer == 0) {
            squareView.setImageResource(R.drawable.yellow);
            activePlayer = 1;
        } else {
            squareView.setImageResource(R.drawable.red);
            activePlayer = 0;
        }
        //animate
        squareView.animate().translationYBy(1500).setDuration(300);
        winCheck();
    }

    public void winCheck() {
        //to win, all 3 win state positions must have the same activePlayer value
        for (int[] winState : winStates) {
            if (gameState[winState[0]] == gameState[winState[1]]
                    && gameState[winState[1]] == gameState[winState[2]] &&
                    gameState[winState[0]] !=2) {
                activeState = false;
                String winner = "";
                //show winner message and display play again button
                winner = (activePlayer == 0) ? "Player Red" : "Player Yellow";
                TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                Button playAgainBtn = (Button) findViewById(R.id.playAgainBtn);
                winnerTextView.setText(winner + " has won!");
                winnerTextView.setVisibility(View.VISIBLE); //0 is visible, 4 is invis
                playAgainBtn.setVisibility(View.VISIBLE);
            }
        }
    }

    public void playAgain(View view) {
        //reset button and messages
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        Button playAgainBtn = (Button) findViewById(R.id.playAgainBtn);
        winnerTextView.setText("");
        winnerTextView.setVisibility(View.INVISIBLE); //0 is visible, 4 is invis
        playAgainBtn.setVisibility(View.INVISIBLE);

        //reset board by iterating through all the items in the grid view
        GridLayout gameBoardGridLayout = (GridLayout) findViewById(R.id.gameBoardGridLayout);
        for (int i = 0; i < gameBoardGridLayout.getChildCount(); i++) {
            //use getChildAt() to access gridLayout items
            ImageView gameSquareView = (ImageView) gameBoardGridLayout.getChildAt(i);
            //remove source from image source by setting drawable to null
            gameSquareView.setImageDrawable(null);
        }
        //also need to reset board square to all empties, game state, and active player
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        activeState = true;
        activePlayer = 0;

    }
}