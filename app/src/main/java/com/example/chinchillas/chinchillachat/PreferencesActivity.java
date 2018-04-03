package com.example.chinchillas.chinchillachat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by angelicagarcia16 on 4/3/2018.
 */

// features modifications from: https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list
// and from: https://developer.android.com/guide/topics/ui/controls/spinner.html#SelectListener

public class PreferencesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner = (Spinner) findViewById(R.id.dropdown);

    String[] people_types = new String[]{"nonbinary", "female", "male"};
    // Create an ArrayAdapter using the string array and a default spinner layout
   // ArrayAdapter<String> adapter = ArrayAdapter<>(this,
    //        R.array.people_array, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears    
   // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
  //  spinner.setAdapter(adapter);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }



}
