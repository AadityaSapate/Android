package com.example.aditya.rightwrong;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 0 = right and 1 = wrong ;
    int state = 0;
    int[] gameState = {2,2,2,2,2,2,2,2,2};// 2 = used state
    int[][] winningP = { {0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8},{0,4,8},{2,4,6}};
    boolean gameOn = true;
    int count = 1;
    public void dropIn(View view)
    {
        ImageView counter = (ImageView) view;
        int UsedCounter = Integer.parseInt(counter.getTag().toString());// each button has given a tag

        counter.setTranslationX(-1000f);
        if(gameState[UsedCounter] == 2 && gameOn)
        {
            count ++;
            gameState[UsedCounter] = state; // state = 0

            if (state == 0) {
                counter.setImageResource(R.drawable.right);
                state = 1;
            } else {
                counter.setImageResource(R.drawable.wrong);
                state = 0;
            }
        }

        counter.animate().translationXBy(1000f).setDuration(300);
        for (int[] winningP : winningP)
        {
            if(gameState[winningP[0]] == gameState[winningP[1]] && gameState[winningP[0]] == gameState[winningP[2]] &&
                    gameState[winningP[0]] != 2 )
            {
                gameOn = false;
                String winner = "Right one !!!";
                if(gameState[winningP[0]] == 1)
                {
                    winner = "Wrong one !!!";
                }



                View button = findViewById(R.id.restart);
                button.setVisibility(View.INVISIBLE);
                TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                winnerMessage.setText(winner + "Wins Congrats !!!");
                LinearLayout playagainLayout = (LinearLayout) findViewById(R.id.playagainLayout);
                playagainLayout.setVisibility(View.VISIBLE);

            }
        }

    }
    public void playAgain(View view)
    {
        gameOn = true;
        LinearLayout playagainLayout = (LinearLayout) findViewById(R.id.playagainLayout);
        playagainLayout.setVisibility(View.INVISIBLE);
        View button = findViewById(R.id.restart);
        button.setVisibility(View.VISIBLE);
        state = 0;
        for(int i=0; i<gameState.length; i++)
        {
            gameState[i] = 2;
        }

            ImageView myImage2 = (ImageView) findViewById(R.id.imageView2);
            myImage2.setImageResource(0);
        ImageView myImage3 = (ImageView) findViewById(R.id.imageView3);
        myImage3.setImageResource(0);
        ImageView myImage4 = (ImageView) findViewById(R.id.imageView4);
        myImage4.setImageResource(0);
        ImageView myImage5 = (ImageView) findViewById(R.id.imageView5);
        myImage5.setImageResource(0);
        ImageView myImage6 = (ImageView) findViewById(R.id.imageView6);
        myImage6.setImageResource(0);
        ImageView myImage7 = (ImageView) findViewById(R.id.imageView7);
        myImage7.setImageResource(0);
        ImageView myImage8 = (ImageView) findViewById(R.id.imageView8);
        myImage8.setImageResource(0);
        ImageView myImage9 = (ImageView) findViewById(R.id.imageView9);
        myImage9.setImageResource(0);
        ImageView myImage10 = (ImageView) findViewById(R.id.imageView10);
        myImage10.setImageResource(0);

        /*
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
         */
       // for(int x = 0; x < gridLayout.getChildCount(); x++)
        //{
          //  ((ImageView) gridLayout.getChildAt(x)).setImageResource(0);
        //}

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
