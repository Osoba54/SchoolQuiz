package com.example.myquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myquiz.quizPackage.Effect;
import com.example.myquiz.quizPackage.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private void shortcutToFunctions(){
        if(true) return;
        findViews();
        newGame();
        createQuestion();
        checkIfRightAnswIndx(0);
        onDestroy();
    }

    private TextView tv1;
    private ImageView imgV;
    private Button btnA1,btnA2,btnA3,btnA4;
    private String[] countries = new String[36];
    private int rightIndex;
    private String fromRightIndex;
    private int points;
    private int currRound = 0;
    private Effect effectGenerator;
    private int numOfQuestions = 35;
//    TO DO: Sprawdzic jak sie zachowa gdy out of range przy zmianie orientacji ekranu


    private void findViews(){
        tv1 = findViewById(R.id.textView2);
        imgV = findViewById(R.id.imageView);
        btnA1 = findViewById(R.id.button);
        btnA2 = findViewById(R.id.button2);
        btnA3 = findViewById(R.id.button3);
        btnA4 = findViewById(R.id.button4);
        countries = getResources().getStringArray(R.array.europe);
        Collections.shuffle(setOfQuests);
        effectGenerator = new Effect(this);

    }

    private void newGame(){
        Log.d("lg","NOWA GRA");
        points = 0;
        currRound = 0;
        tv1.setText("0/0");
        setOfQuests = new ArrayList<>(Arrays.asList(
                new Question("Albania", R.drawable.albania),
                new Question("Andora", R.drawable.andora),
                new Question("Austria", R.drawable.austria),
                new Question("Belgia", R.drawable.belgia),
                new Question("Bułgaria", R.drawable.bulgaria),
                new Question("Chorwacja", R.drawable.chorwacja),
                new Question("Czechy", R.drawable.czechy),
                new Question("Dania", R.drawable.dania),
                new Question("Estonia", R.drawable.estonia),
                new Question("Finlandia", R.drawable.finlandia),
                new Question("Francja", R.drawable.francja),
                new Question("Grecja", R.drawable.grecja),
                new Question("Gruzja", R.drawable.gruzja),
                new Question("Hiszpania", R.drawable.hiszpania),
                new Question("Irlandia", R.drawable.irlandia),
                new Question("Islandia", R.drawable.islandia),
                new Question("Liechtenstein", R.drawable.liechtenstein),
                new Question("Litwa", R.drawable.litwa),
                new Question("Łotwa", R.drawable.lotwa),
                new Question("Luksemburg", R.drawable.luksemburg),
                new Question("Macedonia Północna", R.drawable.macedonia),
                new Question("Malta", R.drawable.malta),
                new Question("Mołdawia", R.drawable.moldawia),
                new Question("Monako", R.drawable.monako),
                new Question("Czarnogóra", R.drawable.czarnogora),
                new Question("Niemcy", R.drawable.niemcy),
                new Question("Norwegia", R.drawable.norwegia),
                new Question("Polska", R.drawable.polska),
                new Question("Portugalia", R.drawable.portugalia),
                new Question("Słowacja", R.drawable.slowacja),
                new Question("Słowenia", R.drawable.slowenia),
                new Question("Szwajcaria", R.drawable.szwajcaria),
                new Question("Szwecja", R.drawable.szwecja),
                new Question("Węgry", R.drawable.wegry),
                new Question("Włochy", R.drawable.wlochy),
                new Question("Turcja", R.drawable.turcja)));
        Collections.shuffle(setOfQuests);
        createQuestion();
        currRound = currRound - 1;
    }

    private void createQuestion() {
        if(numOfQuestions == currRound){
            effectGenerator.showSummary(points,currRound);
            newGame();
        }

        String rightAnswer = setOfQuests.get(currRound).getAnswer();
        int rightImg = setOfQuests.get(currRound).getImg();

        ArrayList<String> shuffledCountries = new ArrayList<>(Arrays.asList(countries));
        Collections.shuffle(shuffledCountries);


        String[] possibleAns = new String[4];
        possibleAns[0] = rightAnswer;

        int index = 1;
        for (int i = 0; i < shuffledCountries.size() && index < 4; i++) {
            if (!shuffledCountries.get(i).equals(rightAnswer)) {
                possibleAns[index++] = shuffledCountries.get(i);
            }
        }

        while (index < 4) {
            possibleAns[index++] = shuffledCountries.get(new Random().nextInt(shuffledCountries.size()));
        }

        Collections.shuffle(Arrays.asList(possibleAns));

        Log.d("lg", possibleAns[0] + " " + possibleAns[1] + " " + possibleAns[2] + " " + possibleAns[3] + " : " + rightAnswer + " : " + rightIndex);

        imgV.setImageResource(rightImg);

        rightIndex = Arrays.asList(possibleAns).indexOf(rightAnswer);
        fromRightIndex = rightAnswer;

        btnA1.setText(possibleAns[0]);
        btnA2.setText(possibleAns[1]);
        btnA3.setText(possibleAns[2]);
        btnA4.setText(possibleAns[3]);


        currRound++;
    }


    @SuppressLint("SetTextI18n")
    private void checkIfRightAnswIndx(int btnNum){
        if(btnNum==rightIndex){
            effectGenerator.showBlinkEffect(true);
            effectGenerator.playRightSound();
//            Toast.makeText(MainActivity.this, "Dobrze",Toast.LENGTH_SHORT).show();
            points++;
        } else {
            effectGenerator.showBlinkEffect(false);
            effectGenerator.playWrongSound();
            Toast.makeText(MainActivity.this, fromRightIndex,Toast.LENGTH_SHORT).show();
        }
        Log.d("lg",rightIndex + " " + btnNum);

        createQuestion();

        tv1.setText("Punkty\n"+points+"/"+(currRound-1));

    }

    ArrayList<Question> setOfQuests = new ArrayList<>(Arrays.asList(
        new Question("Albania", R.drawable.albania),
        new Question("Andora", R.drawable.andora),
        new Question("Austria", R.drawable.austria),
        new Question("Belgia", R.drawable.belgia),
        new Question("Bułgaria", R.drawable.bulgaria),
        new Question("Chorwacja", R.drawable.chorwacja),
        new Question("Czechy", R.drawable.czechy),
        new Question("Dania", R.drawable.dania),
        new Question("Estonia", R.drawable.estonia),
        new Question("Finlandia", R.drawable.finlandia),
        new Question("Francja", R.drawable.francja),
        new Question("Grecja", R.drawable.grecja),
        new Question("Gruzja", R.drawable.gruzja),
        new Question("Hiszpania", R.drawable.hiszpania),
        new Question("Irlandia", R.drawable.irlandia),
        new Question("Islandia", R.drawable.islandia),
        new Question("Liechtenstein", R.drawable.liechtenstein),
        new Question("Litwa", R.drawable.litwa),
        new Question("Łotwa", R.drawable.lotwa),
        new Question("Luksemburg", R.drawable.luksemburg),
        new Question("Macedonia Północna", R.drawable.macedonia),
        new Question("Malta", R.drawable.malta),
        new Question("Mołdawia", R.drawable.moldawia),
        new Question("Monako", R.drawable.monako),
        new Question("Czarnogóra", R.drawable.czarnogora),
        new Question("Niemcy", R.drawable.niemcy),
        new Question("Norwegia", R.drawable.norwegia),
        new Question("Polska", R.drawable.polska),
        new Question("Portugalia", R.drawable.portugalia),
        new Question("Słowacja", R.drawable.slowacja),
        new Question("Słowenia", R.drawable.slowenia),
        new Question("Szwajcaria", R.drawable.szwajcaria),
        new Question("Szwecja", R.drawable.szwecja),
        new Question("Węgry", R.drawable.wegry),
        new Question("Włochy", R.drawable.wlochy),
        new Question("Turcja", R.drawable.turcja)
));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        createQuestion(); // chyba przesunac do else NULL savedInsStat
//        prawdopodonie podac do funkcji instancesavestate albo jego czesc


        btnA1.setOnClickListener(view -> checkIfRightAnswIndx(0));
        btnA2.setOnClickListener(view -> checkIfRightAnswIndx(1));
        btnA3.setOnClickListener(view -> checkIfRightAnswIndx(2));
        btnA4.setOnClickListener(view -> checkIfRightAnswIndx(3));

        if(savedInstanceState != null){
            points = savedInstanceState.getInt("points_k");
            currRound = savedInstanceState.getInt("currRound_k");
            numOfQuestions = savedInstanceState.getInt("numOfQuestions_k");
            setOfQuests = savedInstanceState.getParcelableArrayList("setOfQuests_k");
//            rightIndex = savedInstanceState.getInt("rightIndex_k");
//            fromRightIndex = savedInstanceState.getString("fromRightIndex_k");

            tv1.setText("Punkty\n"+points+"/"+(currRound-1));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("points_k", points);
        outState.putInt("currRound_k", currRound);
        outState.putInt("numOfQuestions_k", numOfQuestions);
        outState.putParcelableArrayList("setOfQuests_k", setOfQuests);
        outState.putInt("rightIndex_k", rightIndex);
        outState.putString("fromRightIndex_k", fromRightIndex);

    }

    protected void onDestroy() {
        super.onDestroy();
        if (effectGenerator != null) {
            effectGenerator.release();
        }
    }
}