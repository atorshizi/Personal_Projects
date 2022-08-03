package com.example.TicTacToe;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public boolean xTurn = true;
    public ArrayList<Integer> xList = new ArrayList<>();
    public ArrayList<Integer> oList = new ArrayList<>();
    public ArrayList<int[]> winList = new ArrayList<>();
    public ArrayList<Integer> allButtons = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("TicTacToe");
        int[] combo1 = {R.id.button16, R.id.button15, R.id.button17};
        winList.add(combo1);
        int[] combo2 = {R.id.button16, R.id.button3, R.id.button13};
        winList.add(combo2);
        int[] combo3 = {R.id.button16, R.id.button10, R.id.button12};
        winList.add(combo3);
        int[] combo4 = {R.id.button10, R.id.button3, R.id.button8};
        winList.add(combo4);
        int[] combo5 = {R.id.button15, R.id.button3, R.id.button11};
        winList.add(combo5);
        int[] combo6 = {R.id.button12, R.id.button11, R.id.button13};
        winList.add(combo6);
        int[] combo7 = {R.id.button17, R.id.button8, R.id.button13};
        winList.add(combo7);
        int[] combo8 = {R.id.button17, R.id.button3, R.id.button12};
        winList.add(combo8);

        allButtons.add(R.id.button16);
        allButtons.add(R.id.button15);
        allButtons.add(R.id.button17);
        allButtons.add(R.id.button10);
        allButtons.add(R.id.button3);
        allButtons.add(R.id.button8);
        allButtons.add(R.id.button12);
        allButtons.add(R.id.button11);
        allButtons.add(R.id.button13);
    }

    public void click(View input) {
        Button v = (Button) input;
        if (xTurn) {
            v.setText("X");
            v.setBackgroundColor(0xffff2142);
            v.setTextColor(0xffffffff);
            v.setTextSize(35);
            xTurn = false;
            input.setEnabled(false);
            TextView turn = (TextView) findViewById(R.id.textView);
            xList.add(input.getId());
            turn.setText("It is O's Turn");
        } else {
            v.setText("O");
            v.setBackgroundColor(0xff6200EE);
            v.setTextColor(0xffffffff);
            v.setTextSize(35);
            xTurn = true;
            input.setEnabled(false);
            TextView turn = (TextView) findViewById(R.id.textView);
            oList.add(input.getId());
            turn.setText("It is X's Turn");
        }
        if (checkWin() == true){
            return;
        }
        else if ((xList.size() + oList.size()) == 9){
            TextView msg = findViewById(R.id.textView);
            msg.setText("IT'S A TIE!");
            return;
        }
    }

    private boolean checkWin(){
        boolean butFound = false;
        //check x win
        for (int i = 0; i < winList.size(); i++) {
            for (int j = 0; j < 3; j++) {
                if (!(xList.contains(winList.get(i)[j]))) {
                    break;
                }
                else if (j == 2){
                    //disable all other buttons
                    for (int k=0; k<allButtons.size(); k++){
                        for (int h = 0; h < 3; h++){
                            if ((winList.get(i)[h] == allButtons.get(k))){
                                butFound = true;
                            }
                        }
                        if (!butFound) {
                            Button but = (Button) findViewById(allButtons.get(k));
                            but.setEnabled(false);
                            but.setBackgroundColor(0x3A3A3A);
                        }
                        butFound = false;
                    }
                    TextView msg = findViewById(R.id.textView);
                    msg.setText("X WON!");
                    return true;
                }
            }
        }
        //check o win
        for (int i = 0; i < winList.size(); i++) {
            for (int j = 0; j < 3; j++) {
                if (!(oList.contains(winList.get(i)[j]))) {
                    break;
                }
                else if (j == 2){
                    //disable all other buttons
                    for (int k=0; k<allButtons.size(); k++){
                        for (int h = 0; h < 3; h++){
                            if ((winList.get(i)[h] == allButtons.get(k))){
                                butFound = true;
                            }
                        }
                        if (!butFound) {
                            Button but = (Button) findViewById(allButtons.get(k));
                            but.setEnabled(false);
                            but.setBackgroundColor(0x3A3A3A);
                        }
                        butFound = false;
                    }
                    TextView msg = findViewById(R.id.textView);
                    msg.setText("O WON!");
                    return true;
                }
            }
        }
        return false;
    }

    public void reset(View v){
        for (int i = 0; i < allButtons.size(); i++){
            Button button = findViewById(allButtons.get(i));
            button.setBackgroundColor(0xff3A3A3A);
            button.setText("Press to Choose");
            button.setTextColor(0x88ffffff);
            button.setEnabled(true);
            button.setTextSize(14);
            xList.clear();
            oList.clear();
            xTurn = true;
            TextView turn = (TextView) findViewById(R.id.textView);
            turn.setText("It is X's Turn");
        }
    }

}