package com.example.myquiz.quizPackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.myquiz.MainActivity;
import com.example.myquiz.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Effect {

    private AudioAttributes audioAttributes;
    private SoundPool soundPool;
    private Activity activity;
    private Context context;
    private int rightBeepId, wrongBeepId;

    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }

    public Effect(Activity context) {
        this.activity = context;
        this.context = context;
        onCreate();
    }

    private void onCreate(){


        audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(1)
                .build();

        rightBeepId = soundPool.load(context,R.raw.right,1);
        wrongBeepId = soundPool.load(context,R.raw.wrong,1);


    }

    public void playRightSound(){
        soundPool.play(rightBeepId,1.0f,1.0f,1,0,1.0f);
    }

    public void playWrongSound(){
        soundPool.play(wrongBeepId,1.0f,1.0f,1,0,1.0f);
    }

    public void showBlinkEffect(boolean ifRight){
        this.showBlinkEffect(ifRight,800);
    }

    public void showSummary(int points, int rounds, MainActivity act){
        new MaterialAlertDialogBuilder(context, R.style.ThemeOverlay_App_MaterialAlertDialog)
                .setTitle("Poprawnie "+ points +"/" + rounds)
                .setPositiveButton("Zagraj ponownie", (d,w) -> act.newGame())
                .setNegativeButton("Zamknij aplikację", (d,w) -> activity.finishAffinity())
                .setNeutralButton("Ustawienia", (d,w)-> act.finish())
                .setCancelable(false)
                .show();
    }

    public void showBlinkEffect(boolean ifRight, int animDuration) {

        ConstraintLayout fadeLayer = activity.findViewById(R.id.animLayer);
        if(ifRight){
            fadeLayer.setBackgroundColor(ContextCompat.getColor(activity,R.color.rightAnimColor));
        } else {
            fadeLayer.setBackgroundColor(ContextCompat.getColor(activity,R.color.wrongAnimColor));
        }

        fadeLayer.setVisibility(View.VISIBLE);


        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(fadeLayer, "alpha", 0f, 0.5f);
        fadeIn.setDuration(animDuration/2);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(fadeLayer, "alpha", 0.5f, 0f);
        fadeOut.setDuration(animDuration/2);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(fadeIn, fadeOut);
        animatorSet.setInterpolator(new DecelerateInterpolator());

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fadeLayer.setVisibility(View.GONE);
            }
        });

        animatorSet.start();
    }
}
