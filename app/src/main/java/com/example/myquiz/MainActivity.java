package com.example.myquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myquiz.quizPackage.Effect;
import com.example.myquiz.quizPackage.Question;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView tv1;
    private ImageView imgV;
    private Button btnA1,btnA2,btnA3,btnA4,btnTimesUp;
    private String[] countries = new String[36];
    private int rightIndex;
    private String fromRightIndex;
    private int points;
    private int currRound = 0;
    private Effect effectGenerator;
    private int numOfQuestions = 35;
    private TextView textViewTimer;
    private boolean soundOn;
    private CountDownTimer countDownTimer;
    private long standardTime;
    private long timeLeft = 5000;
    private boolean timerOn;
    private void startCountdown(){
        if (countDownTimer != null) {
            countDownTimer.cancel();
            timeLeft = standardTime;
        }

        countDownTimer = new CountDownTimer(timeLeft, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                String timeFormatted = String.format(Locale.getDefault(), "%.1f", timeLeft / 1000.0);
                textViewTimer.setText(timeFormatted + "s");
            }

            @Override
            public void onFinish() {
                timeLeft = 0;
                String timeFormatted = String.format(Locale.getDefault(), "%.1f", timeLeft / 1000.0);
                textViewTimer.setText(timeFormatted + "s");
                btnA1.setVisibility(TextView.GONE);
                btnA2.setVisibility(TextView.GONE);
                btnA3.setVisibility(TextView.GONE);
                btnA4.setVisibility(TextView.GONE);
                btnTimesUp.setVisibility(TextView.VISIBLE);
                btnTimesUp.setText(fromRightIndex+"\n(0 pkt)");
                btnTimesUp.setOnClickListener(v->{
                    btnA1.setVisibility(TextView.VISIBLE);
                    btnA2.setVisibility(TextView.VISIBLE);
                    btnA3.setVisibility(TextView.VISIBLE);
                    btnA4.setVisibility(TextView.VISIBLE);
                    btnTimesUp.setVisibility(TextView.GONE);
                    createQuestion();
                    tv1.setText(points+"/"+(currRound-1));
                });
            }
        }.start();
    }

    private void findViews(){
        tv1 = findViewById(R.id.textView2);
        imgV = findViewById(R.id.imageView);
        btnA1 = findViewById(R.id.button);
        btnA2 = findViewById(R.id.button2);
        btnA3 = findViewById(R.id.button3);
        btnA4 = findViewById(R.id.button4);
        textViewTimer = findViewById(R.id.textViewTimer);
        countries = getResources().getStringArray(R.array.europe);
        Collections.shuffle(setOfQuests);
        effectGenerator = new Effect(this);
        numOfQuestions = getIntent().getIntExtra("round_len",5);
        soundOn = getIntent().getBooleanExtra("sound_cb",false);
        standardTime = getIntent().getIntExtra("stand_time",5) * 1000L;
        timerOn = getIntent().getBooleanExtra("timer_cb",false);
        btnTimesUp = findViewById(R.id.buttonTimerFinish);
        timeLeft = standardTime + 300L;
    }

    public void newGame(){
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
//        currRound = currRound - 1;

    }

    public void createQuestion() {
        btnA1.setVisibility(TextView.VISIBLE);
        btnA2.setVisibility(TextView.VISIBLE);
        btnA3.setVisibility(TextView.VISIBLE);
        btnA4.setVisibility(TextView.VISIBLE);
        btnTimesUp.setVisibility(TextView.GONE);

        if(numOfQuestions == currRound){
            effectGenerator.showSummary(points,currRound,this);
            if (countDownTimer != null) {
                countDownTimer.cancel();
                timeLeft = standardTime;
            }
        } else {
            if(timerOn) startCountdown();
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
            if(soundOn) effectGenerator.playRightSound();
            points++;
        } else {
            effectGenerator.showBlinkEffect(false);
            if(soundOn) effectGenerator.playWrongSound();
            Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_main), fromRightIndex, Snackbar.LENGTH_SHORT).setDuration(1500);
            TextView snackbarView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
            snackbarView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            snackbarView.setTextSize(18);
            snackbar.show();
        }

        createQuestion();

        tv1.setText(points+"/"+(currRound-1));
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
        createQuestion();

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

            tv1.setText(points+"/"+(currRound-1));
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