package com.example.connectfour;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    boolean isGameActive=true;
    int activePlayer=1; //0:none, 1: red, 2: yellow
    androidx.gridlayout.widget.GridLayout gridLayoutBoard;
    int [] gameState= new int[42];
    int[][] winningPositions= {
            //horizontal --24
            {0,1,2,3},{1,2,3,4},{2,3,4,5},{3,4,5,6},
            {7,8,9,10},{8,9,10,11},{9,10,11,12},{10,11,12,13},
            {14,15,16,17},{15,16,17,18},{16,17,18,19},{17,18,19,20},
            {21,22,23,24},{22,23,24,25},{23,24,25,26},{24,25,26,27},
            {28,29,30,31},{29,30,31,32},{30,31,32,33},{31,32,33,34},
            {35,36,37,38},{36,37,38,39},{37,38,39,40},{38,39,40,41},

            //vertical --21
            {0,7,14,21},{7,14,21,28},{14,21,28,35},
            {1,8,15,22},{8,15,22,29},{15,22,29,36},
            {2,9,16,23},{9,16,23,30},{16,23,30,37},
            {3,10,17,24},{10,17,24,31},{17,24,31,38},
            {4,11,18,25},{11,18,25,32},{18,25,32,39},
            {5,12,19,26},{12,19,26,33},{19,26,33,40},
            {6,13,20,27},{13,20,27,34},{20,27,34,41},

            //diagonal (top left to bottom right) --12
            {0,8,16,24},{1,9,17,25},{2,10,18,26},{3,11,19,27},
            {7,15,23,31},{8,16,24,32},{9,17,25,31},{10,18,26,32},
            {14,22,30,38},{15,23,31,39},{16,24,32,40},{17,25,33,41},

            //diagonal(top right to bottom left) --12
            {3,9,15,21},{4,10,16,22},{5,11,17,23},{6,12,18,24},
            {10,16,22,28},{11,17,23,29},{12,18,24,30},{13,19,25,31},
            {17,23,29,35},{18,24,30,36},{19,25,31,37},{20,26,32,38},

            //-24+21+12+12 = 69
    };

    public void dropToken(View view){
        ImageView counter = (ImageView) view;
        ImageView slot;
        int initialTokenTag = Integer.parseInt(counter.getTag().toString());
        int tokenTag = 0;
        int column;
        int bottomToken=35;

        /*this can be further compressed (inner for) but keeping it as it is would help readability.
          if we expand this it would be 7x6 sections.
          a better way might be to place this in a method.
         */
        /*initialToken is used ONLY to derive the column in which the token is placed.
          tokenTag is derived not from initialToken, but from bottomToken+column.
          this is because player might press one of the upper token slots even if the ones below are empty.
          when this happens, tokenTag will skip over the empty slots because
          */
        if(isGameActive) {
            if (initialTokenTag % 7 == 0) {
                column = 0;
                for (tokenTag = bottomToken + column; tokenTag >= 0; tokenTag -= 7) {
                    if (gameState[tokenTag] == 0) {
                        break;
                    }
                }
            } else if (initialTokenTag % 7 == 1) {
                column = 1;
                for (tokenTag = bottomToken + column; tokenTag >= 0; tokenTag -= 7) {
                    if (gameState[tokenTag] == 0) {
                        break;
                    }
                }
            } else if (initialTokenTag % 7 == 2) {
                column = 2;
                for (tokenTag = bottomToken + column; tokenTag >= 0; tokenTag -= 7) {
                    if (gameState[tokenTag] == 0) {
                        break;
                    }
                }
            } else if (initialTokenTag % 7 == 3) {
                column = 3;
                for (tokenTag = bottomToken + column; tokenTag >= 0; tokenTag -= 7) {
                    if (gameState[tokenTag] == 0) {
                        break;
                    }
                }
            } else if (initialTokenTag % 7 == 4) {
                column = 4;
                for (tokenTag = bottomToken + column; tokenTag >= 0; tokenTag -= 7) {
                    if (gameState[tokenTag] == 0) {
                        break;
                    }
                }
            } else if (initialTokenTag % 7 == 5) {
                column = 5;
                for (tokenTag = bottomToken + column; tokenTag >= 0; tokenTag -= 7) {
                    if (gameState[tokenTag] == 0) {
                        break;
                    }
                }
            } else if (initialTokenTag % 7 == 6) {
                column = 6;
                for (tokenTag = bottomToken + column; tokenTag >= 0; tokenTag -= 7) {
                    if (gameState[tokenTag] == 0) {
                        break;
                    }
                }
            }
            gameState[tokenTag] = activePlayer;//keep track of slots owned by players
            Log.i("asd ",tokenTag+"  ");
            slot = (ImageView) gridLayoutBoard.getChildAt(tokenTag);
            slot.setTranslationY(-1000);
            if (activePlayer == 1) {
                slot.setImageResource(R.drawable.red);
                activePlayer=2;
            } else {
                slot.setImageResource(R.drawable.yellow);
                activePlayer=1;
            }

            slot.animate().rotation(360).translationY(0).setDuration(700);

            for (int[] winningPosition : winningPositions) {
                if (
                        gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                                gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                                gameState[winningPosition[2]] == gameState[winningPosition[3]] &&
                                gameState[winningPosition[0]]!=0) {

                    TextView textViewAuthor = (TextView)findViewById(R.id.textViewAuthor);
                    textViewAuthor.setAlpha(0.0f);

                    TextView textViewWinner = (TextView)findViewById(R.id.textViewWinner);
                    String winner;
                    if(activePlayer==1){
                        winner = "YELLOW WINS!";
                        textViewWinner.setTextColor(0xFFFFCC11);
                    }else{
                        winner="RED WINS!";
                        textViewWinner.setTextColor(0xFFFF0033);
                    }
                    textViewWinner.setText(winner);
                    textViewWinner.animate().alpha(1.0f);




                    Button buttonRestart = (Button)findViewById(R.id.buttonRestart);
                    buttonRestart.animate().alpha(1.0f);

                    isGameActive=false;
                }
            }
        }
    }

    public void restartGame(View view){
        Arrays.fill(gameState,0);

        gridLayoutBoard = findViewById(R.id.gridLayoutBoard);
        for(int i =0;i<gridLayoutBoard.getChildCount();i++){
            ImageView counter =(ImageView) gridLayoutBoard.getChildAt(i);
            counter.setImageDrawable(null);
        }
        gridLayoutBoard.setAlpha(0.0f);
        gridLayoutBoard.animate().alpha(1.0f).setDuration(3000);

        TextView textViewAuthor = (TextView)findViewById(R.id.textViewAuthor);
        textViewAuthor.setAlpha(0.0f);
        textViewAuthor.animate().alpha(1.0f).setDuration(4000);

        TextView textViewWinner = (TextView)findViewById(R.id.textViewWinner);
        textViewWinner.setAlpha(0.0f);

        Button buttonRestart = (Button)findViewById(R.id.buttonRestart);
        buttonRestart.setAlpha(0.0f);

        isGameActive=true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Arrays.fill(gameState,0);

        gridLayoutBoard = findViewById(R.id.gridLayoutBoard);
        for(int i =0;i<gridLayoutBoard.getChildCount();i++){
            ImageView counter =(ImageView) gridLayoutBoard.getChildAt(i);
            counter.setImageDrawable(null);
        }
        gridLayoutBoard.setAlpha(0.0f);
        gridLayoutBoard.animate().alpha(1.0f).setDuration(3000);

        TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewTitle.setAlpha(0.0f);
        textViewTitle.animate().alpha(1.0f).setDuration(2000);

        TextView textViewAuthor = (TextView)findViewById(R.id.textViewAuthor);
        textViewAuthor.setAlpha(0.0f);
        textViewAuthor.animate().alpha(1.0f).setDuration(4000);

        TextView textViewWinner = (TextView)findViewById(R.id.textViewWinner);
        textViewWinner.setAlpha(0.0f);

        Button buttonRestart = (Button)findViewById(R.id.buttonRestart);
        buttonRestart.setAlpha(0.0f);
    }
}