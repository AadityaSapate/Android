package com.example.aditya.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int score = 0;
    int numOfQuestions = 0;
    TextView textView;
    TextView textMsg;
    ArrayList<Integer> answer = new ArrayList<Integer>();
    TextView textResult;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    TextView timeView;
    int locationOfAns;
    CountDownTimer timer;
    Button button;
    Button playAgain;
    ConstraintLayout playLayout;
    boolean stop = false;
    public void generate()      //Generate Question;
    {

        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        textView.setText(Integer.toString(a) + "+" + Integer.toString(b));
       locationOfAns = rand.nextInt(4) ;
        int inCorrectAns;
        answer.clear();

        for(int i=0; i<4; i++)   // Adding Elements to  the list
        {
            if(i == locationOfAns)
            {
                answer.add(a + b);
            }
            else
            {
                inCorrectAns = rand.nextInt(41);
                while(inCorrectAns == a + b)
                {
                    inCorrectAns = rand.nextInt(41);
                }
                answer.add(inCorrectAns);
            }
        }
        button0.setText(Integer.toString(answer.get(0)));   // Setting Correct And Random Incorrect
        button1.setText(Integer.toString(answer.get(1)));   // Answers To Each Button
        button2.setText(Integer.toString(answer.get(2)));
        button3.setText(Integer.toString(answer.get(3)));


    }
    public void playAgain(View view)     // Restarts Play
    {
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        score = 0;
        numOfQuestions =0;
        timeView.setText("30s");
        textResult.setText("0/0");
        textMsg.setText("");
        playAgain.setVisibility((view.INVISIBLE));
        generate();
        timer =  new CountDownTimer(30100, 1000) {     // Setting Timer;
            @Override
            public void onTick(long millisUntilFinished) {

                timeView.setText(String.valueOf(millisUntilFinished/1000)+"s"); // updating Timer
            }

            @Override
            public void onFinish() {
                timeView.setText("0s");
                textMsg.setText("Your Score Is" + Integer.toString(score) +"/"+ Integer.toString(numOfQuestions));
                playAgain.setVisibility(View.VISIBLE);

                button0.setVisibility(View.INVISIBLE);
                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                button3.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                //  generate();

            }
        }.start();


    }
    public void go(View view)
    {
        button.setVisibility(view.INVISIBLE);
        playAgain(findViewById(R.id.playAgain));
        playLayout.setVisibility(view.VISIBLE);

    }
    public void choose(View view)
    {
        if(view.getTag().toString().equals(Integer.toString(locationOfAns)))
        {
          score++;
            textMsg.setText("Correct");
        }
        else
        {
            textMsg.setText("Wrong");
        }
        numOfQuestions++;
        textResult.setText(Integer.toString(score) +"/"+ Integer.toString(numOfQuestions));
        generate();

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
        textView = (TextView) findViewById(R.id.textView);
       textMsg = (TextView) findViewById(R.id.textMsg);
        textResult = (TextView) findViewById(R.id.textResult);
       timeView = (TextView) findViewById(R.id.timeView);
       button0 = (Button) findViewById(R.id.button0);
       button1 = (Button) findViewById(R.id.button1);
       button2 = (Button) findViewById(R.id.button2);
       button3 = (Button) findViewById(R.id.button3);
       button = (Button) findViewById(R.id.button);
       playAgain = (Button) findViewById(R.id.playAgain);
       playLayout = (ConstraintLayout) findViewById(R.id.playLayout);
       //generate();


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
