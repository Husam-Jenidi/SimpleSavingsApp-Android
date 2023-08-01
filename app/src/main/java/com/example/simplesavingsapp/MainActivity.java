package com.example.simplesavingsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Switch switchButton;
    private ConstraintLayout mainLayout;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_INPUT_1 = "input1";
    private static final String KEY_INPUT_2 = "input2";
    private static final String KEY_SWITCH_STATE = "switchState";
    private EditText name;
    private EditText location;
    private Button saveButton;
    private Button clearButton;
    private Button closeButton;
    private boolean switchState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        location = findViewById(R.id.location);
        saveButton = findViewById(R.id.save);
        clearButton = findViewById(R.id.clear);
        closeButton = findViewById(R.id.close);
        switchButton = findViewById(R.id.switch1);
        mainLayout = findViewById(R.id.mainLayout);
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Load saved input values from shared preferences
        String savedInput1 = sharedPreferences.getString(KEY_INPUT_1, "");
        String savedInput2 = sharedPreferences.getString(KEY_INPUT_2, "");
        switchState = sharedPreferences.getBoolean(KEY_SWITCH_STATE, false);
        switchButton.setChecked(switchState);

        // Populate input fields with saved values
        name.setText(savedInput1);
        location.setText(savedInput2);
        if (switchState) mainLayout.setBackgroundColor(Color.GRAY);
        else mainLayout.setBackgroundColor(Color.WHITE);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save input values to shared preferences
                String input1 = name.getText().toString();
                String input2 = location.getText().toString();
                // Update switchState with the current state of the switch
                switchState = switchButton.isChecked();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(KEY_SWITCH_STATE, switchState);
                editor.putString("input1", input1);
                editor.putString(KEY_INPUT_2, input2);
                editor.apply();
                Toast.makeText(getApplicationContext(), "Preferences saved!", Toast.LENGTH_SHORT).show();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear input fields and remove saved values from shared preferences
                name.setText("");
                location.setText("");
                mainLayout.setBackgroundColor(Color.WHITE);
                switchButton.setChecked(false);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(KEY_INPUT_1);
                editor.remove(KEY_INPUT_2);
                editor.remove(KEY_SWITCH_STATE);
                editor.apply();
                Toast.makeText(getApplicationContext(), "Preferences cleared!", Toast.LENGTH_SHORT).show();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close the app
                finish();
            }
        });

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // Set the background color to gray
                    mainLayout.setBackgroundColor(Color.GRAY);
                } else {
                    // Set the background color to white
                    mainLayout.setBackgroundColor(Color.WHITE);
                }
            }
        });
    }
    protected void onResume() {
        super.onResume();

        // Reload saved input values from shared preferences
        String savedInput1 = sharedPreferences.getString(KEY_INPUT_1, "");
        String savedInput2 = sharedPreferences.getString(KEY_INPUT_2, "");

        // Populate input fields with saved values
        name.setText(savedInput1);
        location.setText(savedInput2);
    }}