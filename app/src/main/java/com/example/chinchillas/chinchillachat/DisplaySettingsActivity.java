package com.example.chinchillas.chinchillachat;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * This activity allows the user to select new themes
 * for their app display.
 */

public class DisplaySettingsActivity extends ChinchillaChatActivity {

    private Button confirmBtn;
    private Spinner themeSpinner;
    private int themeSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_settings);

        confirmBtn = findViewById(R.id.confirmSettingsBtn);
        themeSpinner = findViewById(R.id.themeSpinner);

        String[] themeNames = new String[]{getString(R.string.default_theme), getString(R.string.cool_theme), getString(R.string.night_theme)};
        themeSelection = THEME_DEFAULT;

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, themeNames) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                return super.getDropDownView(position, convertView, parent);
            }
        };

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        themeSpinner.setAdapter(adapter);

        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (selectedItemText.equals(getString(R.string.cool_theme))){
                    themeSelection = THEME_COOL;
                } else if (selectedItemText.equals(getString(R.string.night_theme))){
                    themeSelection = THEME_NIGHT;
                } else {
                    themeSelection = THEME_DEFAULT;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = themeSelection;
                editor.putInt("theme", themeSelection);
                editor.commit();
                Toast.makeText(DisplaySettingsActivity.this, getString(R.string.restart_to_change_theme), Toast.LENGTH_LONG).show();
            }
        });
    }
}