package com.example.myquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    private Button btn1;
    private EditText et1;
    private int editTextNum;
    private CheckBox checkBoxSound, checkBoxTimer;
    private NumberPicker numberPicker;

    private void changeActv(){
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.putExtra("round_len", editTextNum);
        intent.putExtra("sound_cb", checkBoxSound.isChecked());
        intent.putExtra("stand_time", numberPicker.getValue());
        intent.putExtra("timer_cb", checkBoxTimer.isChecked());
        Log.d("lg", String.valueOf(numberPicker.getValue()));
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btn1 = findViewById(R.id.buttonSettings);
        et1 = findViewById(R.id.editTextSettings);
        checkBoxSound = findViewById(R.id.checkBoxSettings);
        numberPicker = findViewById(R.id.numberPicker3);
        checkBoxTimer = findViewById(R.id.checkBox);
        numberPicker.setMinValue(2);
        numberPicker.setMaxValue(10);
        if(savedInstanceState!=null){
            numberPicker.setValue(savedInstanceState.getInt("numberpicker_k"));
        }
        btn1.setEnabled(false);
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = et1.getText().toString();
                if(!text.isEmpty()) {
                    editTextNum = Integer.parseInt(text);
                } else {
                    editTextNum = 0;
                }

                btn1.setEnabled(editTextNum >= 5 && editTextNum <= 35);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn1.setOnClickListener(v -> changeActv());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("numberpicker_k", numberPicker.getValue());
    }
}