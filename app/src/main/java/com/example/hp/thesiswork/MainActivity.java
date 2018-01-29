package com.example.hp.thesiswork;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.BufferedReader;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.FileReader;
import java.util.ArrayList;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    //Creating initial objects
    Intent sendString;
    Bundle myDataBundle;
    TextView txtSpeechInput;
    TextView txtName;
    Spinner spinnerYear;
    int controller;
    int counter;
    String check;
    Spinner spinnerYear2;
    Spinner spinnerGenre;
    Spinner spinnerRating;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextToSpeech tts;

    //Creating Arraylist for spinners
    ArrayList<String> listYear = new ArrayList<String>() {{
        add("1940");
        add("1970");
        add("1980");
        add("1990");
        add("1991");
        add("1992");
        add("1993");
        add("1994");
        add("1995");
        add("1996");
        add("1997");
        add("1998");
        add("1999");
        add("2000");
        add("2001");
        add("2002");
        add("2003");
        add("2004");
        add("2005");
        add("2006");
        add("2007");
        add("2008");
        add("2009");
        add("2010");
        add("2011");
        add("2012");
        add("2013");
        add("2014");
        add("2015");
        add("2016");
        add("2017");
    }};
    ArrayList<String> listYear2 = new ArrayList<String>() {{
        add("1970");
        add("1980");
        add("1990");
        add("1991");
        add("1992");
        add("1993");
        add("1994");
        add("1995");
        add("1996");
        add("1997");
        add("1998");
        add("1999");
        add("2000");
        add("2001");
        add("2002");
        add("2003");
        add("2004");
        add("2005");
        add("2006");
        add("2007");
        add("2008");
        add("2009");
        add("2010");
        add("2011");
        add("2012");
        add("2013");
        add("2014");
        add("2015");
        add("2016");
        add("2017");
    }};
    ArrayList<String> listGenre = new ArrayList<String>() {{
        add("Show me any movie");
        add("Action");
        add("Adventure");
        add("Animation");
        add("Biography");
        add("Comedy");
        add("Crime");
        add("Documentary");
        add("Drama");
        add("Family");
        add("Fantasy");
        add("Film-Noir");
        add("History");
        add("Horror");
        add("Music");
        add("Mystery");
        add("Romance");
        add("Sci-Fi");
        add("Sport");
        add("Thriller");
        add("War");
        add("Western");
    }};
    ArrayList<String> listRating = new ArrayList<String>() {{
        add("At least 0");
        add("At least 1");
        add("At least 2");
        add("At least 3");
        add("At least 4");
        add("At least 5");
        add("At least 6");
        add("At least 7");
        add("At least 8");
        add("At least 9");
    }};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        tts = new TextToSpeech(this, this);
        counter = 0;
        controller = 0;
        check = "";

        //Defining EditText and Textview objects
        txtName = (EditText) findViewById(R.id.txtName);
        spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        spinnerYear2 = (Spinner) findViewById(R.id.spinnerYear2);
        spinnerGenre = (Spinner) findViewById(R.id.spinnerGenre);
        spinnerRating = (Spinner) findViewById(R.id.spinnerRating);

        sendString = new Intent(MainActivity.this, IntentActivity.class);


        myDataBundle = new Bundle();

        //These adapters takes Spinner datas i defined above
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listYear);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listYear2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear2.setAdapter(dataAdapter2);

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listGenre);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(dataAdapter3);

        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listRating);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRating.setAdapter(dataAdapter4);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                promptSpeechInput();
            }
        });


    }

    //This method takes user voice command
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();

        }
    }

    //This nethod cahnge activity if user's voice command needs
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


                    if ((result.get(0).toString().equals("prediction")) || ((result.get(0).toString().equals("put addiction")))) {
                        Intent startNewAct = new Intent(MainActivity.this, PredictionActivity.class);
                        startActivity(startNewAct);
                    } else if (result.get(0).toString().equals("recommendation")) {
                        myDataBundle.putString("val1", spinnerYear.getSelectedItem().toString());
                        myDataBundle.putString("val2", spinnerYear2.getSelectedItem().toString());
                        myDataBundle.putString("val3", spinnerGenre.getSelectedItem().toString());
                        myDataBundle.putString("val4", spinnerRating.getSelectedItem().toString());
                        myDataBundle.putString("val5", txtName.getText().toString());


                        sendString.putExtras(myDataBundle);


                        startActivityForResult(sendString, 101);


                    } else {


                    }
                }
            }
        }
    }

    //This button makes REcommandation Activity start
    protected void recommandationButton(View e) {
        myDataBundle.putString("val1", spinnerYear.getSelectedItem().toString());
        myDataBundle.putString("val2", spinnerYear2.getSelectedItem().toString());
        myDataBundle.putString("val3", spinnerGenre.getSelectedItem().toString());
        myDataBundle.putString("val4", spinnerRating.getSelectedItem().toString());
        myDataBundle.putString("val5", txtName.getText().toString());


        sendString.putExtras(myDataBundle);


        startActivityForResult(sendString, 101);
    }

    //This button makes REcommandation Activity start
    public void PredictionButton(View view) {
        Intent startNewAct = new Intent(MainActivity.this, PredictionActivity.class);
        startActivity(startNewAct);
    }

    @Override
    public void onInit(int i) {

    }
}
