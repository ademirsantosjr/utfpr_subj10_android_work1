package br.edu.utfpr.listadecomprasemmercados;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    public static final String FILE = "br.edu.utfpr.listadecomprasemmercados.SETTINGS";
    public static final String THEME = "THEME";

    private ConstraintLayout constraintLayoutSettings;
    private TextView textViewGeneral;
    private TextView textViewDarkTheme;
    private TextView textViewDarkThemeDescription;
    private Switch switchDarkTheme;

    private boolean isDarkThemeChosen = false;

    public static void settings(AppCompatActivity activity) {
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        constraintLayoutSettings = findViewById(R.id.constraintLayoutSettings);
        textViewGeneral = findViewById(R.id.textViewGeneral);
        textViewDarkTheme = findViewById(R.id.textViewDarkTheme);
        textViewDarkThemeDescription = findViewById(R.id.textViewDartThemeDescription);
        switchDarkTheme = findViewById(R.id.switchDarkTheme);

        setTitle(getString(R.string.title_settings));

        initSwitchListener();
        readPreference();
    }

    private void readPreference() {
        SharedPreferences sharedPreferences = getSharedPreferences(FILE, Context.MODE_PRIVATE);

        isDarkThemeChosen = sharedPreferences.getBoolean(THEME, isDarkThemeChosen);

        changeTheme();
    }

    private void saveTheme(boolean newValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(THEME, newValue);
        editor.commit();

        isDarkThemeChosen = newValue;

        changeTheme();
    }

    private void changeTheme() {

        if (isDarkThemeChosen) {
            constraintLayoutSettings.setBackgroundColor(Color.BLACK);
            textViewGeneral.setTextColor(Color.WHITE);
            textViewDarkTheme.setTextColor(Color.rgb(200,200,200));
            textViewDarkThemeDescription.setTextColor(Color.rgb(200,200,200));
            switchDarkTheme.setChecked(true);
        } else {
            constraintLayoutSettings.setBackgroundColor(Color.WHITE);
            textViewGeneral.setTextColor(Color.BLACK);
            textViewDarkTheme.setTextColor(Color.rgb(66,66,66));
            textViewDarkThemeDescription.setTextColor(Color.rgb(66,66,66));
            switchDarkTheme.setChecked(false);
        }
    }

    private void initSwitchListener() {
        switchDarkTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isSwitched) {
                isDarkThemeChosen = isSwitched;
                saveTheme(isDarkThemeChosen);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}