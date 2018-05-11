package com.example.chinchillas.chinchillachat;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * Created by angelicagarcia16 on 4/3/2018.
 */

// features modifications from: https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list
// and from: https://developer.android.com/guide/topics/ui/controls/spinner.html#SelectListener

public class PreferencesActivity extends ChinchillaChatActivity {

    Spinner spinner;
    private Button findMatchButton;
    CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        spinner = (Spinner) findViewById(R.id.dropdown);

        String[] people_types = new String[]{"cisgender female", "cisgender male", "intersex", "nonbinary", "transgender female", "transgender male", "other", "anything"};

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, people_types) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                return super.getDropDownView(position, convertView, parent);
            }
        };

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findMatchButton = findViewById(R.id.findMatchBtn);
        findMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement
                displayComingSoonMessage();
            }
        });
    }
}
