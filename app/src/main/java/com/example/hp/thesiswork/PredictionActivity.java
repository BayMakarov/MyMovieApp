package com.example.hp.thesiswork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.support.v7.widget.Toolbar;
import android.view.Window;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static java.lang.Double.NaN;

public class PredictionActivity extends AppCompatActivity {

    //Creating Initial objects
    TextView Actor1;
    TextView Actor2;
    TextView Director;
    TextView runTime;
    Spinner spinnerGenre;

    //Creating Arraylist for spinners
    ArrayList<String> listGenre = new ArrayList<String>() {{
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction2);
        readMovieData();
        Actor1 = (EditText) findViewById(R.id.txtName);
        Actor2 = (EditText) findViewById(R.id.txtName2);
        Director = (EditText) findViewById(R.id.txtNameDirector);
        runTime = (EditText) findViewById(R.id.txtNameruntime);
        spinnerGenre = (Spinner) findViewById(R.id.spinnerGenre);


        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listGenre);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(dataAdapter3);


    }

    //this is Arraylist to hold datas of movie_metadata.csv
    private List<Movies> MovieList = new ArrayList<Movies>();

    //This method is used to read datas from movie_metadata.csv and write to Arraylist
    private void readMovieData() {
        InputStream is = getResources().openRawResource(R.raw.movie_metadata);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {


                String[] tokens = line.split(",");
                Movies movies = new Movies();
                movies.setDirectorName(tokens[0]);
                if (tokens[1].length() > 0) {
                    movies.setDuration(Integer.parseInt(tokens[1]));
                }
                movies.setActor2(tokens[2]);
                movies.setGenre(tokens[3]);
                movies.setActor1(tokens[4]);
                movies.setMovieTitle(tokens[5]);
                movies.setActor3(tokens[6]);
                movies.setImdbLink(tokens[7]);
                movies.setLanguage(tokens[8]);
                movies.setCountry(tokens[9]);
                if (tokens[10].length() > 0) {
                    movies.setYear(Integer.parseInt(tokens[10]));
                }
                movies.setGrade(Double.parseDouble(tokens[11]));
                MovieList.add(movies);


            }
        } catch (IOException e) {
            Log.wtf("My Activity", "Error handling on file on line " + line, e);
            e.printStackTrace();
        }


    }

    //This is button to make grade prediction
    protected void Prediction1(View e) {
        TextView IMDB = (TextView) findViewById(R.id.imdbGrade);


        //if (Actor1.getText().toString().isEmpty() || Actor2.getText().toString().isEmpty() ||
        //      Director.getText().toString().isEmpty()) {
        // Log.d("noluyore", Actor1.getText().toString() +" "+ Actor2.getText().toString()+ " " +
        //         Director.getText().toString() + " " + runTime.getText().toString());
        // IMDB.setText("You gave wrong inputs");}
        //else{

        double s = predictorForActor1();
        double f = predictorForActor2();
        double x = predictorForDirector();
        double r = predictionForGenre();
        if (runTime.getText().toString().matches("")) {
            double result = ((s*1.1) + (f*1.1) + (x*1.3) + (r*0.5)) / 4;

            result = Math.floor(result * 10) / 10;

            IMDB.setText(Double.toString(result));

        } else {
            double v = predictorForRunTime();
            double result = ((s*1.4) + (f*1.4) + (x*1.6) + (v*0.5) + (r*0.5)) / 5;

            result = Math.floor(result * 10) / 10;

            IMDB.setText(Double.toString(result));


        }
    }


    /*These are some methods; They ared used to read data according to user input
     **They calculates and average grade for each input

    */

    private double predictionForGenre() {
        double a = 0;
        double b = 0;

        for (int i = 0; i < MovieList.size(); i++) {
            String[] parts = MovieList.get(i).getGenre().split("\\|");
            List<String> itemList = Arrays.asList(parts);
            //Log.d("neoldu", itemList.toString() + " " +spinnerGenre.getSelectedItem().toString()
            //      + " " +   a + " " +  b );

            if ((itemList.contains(spinnerGenre.getSelectedItem().toString()))) {
                a = a + MovieList.get(i).getGrade();
                b++;
            }
        }
        return a / b;

    }

    private double predictorForRunTime() {
        double a = 0;
        double b = 0;
        double[] runtimes = new double[MovieList.size()];
        double[] grades = new double[MovieList.size()];


        for (int i = 0; i < MovieList.size(); i++) {
            runtimes[i] = MovieList.get(i).getDuration();
            grades[i] = MovieList.get(i).getGrade();
        }

        LinearRegression lin = new LinearRegression(runtimes, grades);


        double s = Double.parseDouble(runTime.getText().toString());
        return lin.predict(s);


    }

    private double predictorForDirector() {
        double a = 0;
        double b = 0;
        for (int i = 0; i < MovieList.size(); i++) {
            if (Director.getText().toString().equalsIgnoreCase(MovieList.get(i).getDirectorName())) {
                a = a + MovieList.get(i).getGrade();
                b++;
            }
        }
        return a / b;
    }


    private double predictorForActor2() {
        double a = 0;
        double b = 0;
        for (int i = 0; i < MovieList.size(); i++) {
            if (Actor2.getText().toString().equalsIgnoreCase(MovieList.get(i).getActor1()) ||
                    Actor2.getText().toString().equalsIgnoreCase(MovieList.get(i).getActor2()) ||
                    Actor2.getText().toString().equalsIgnoreCase(MovieList.get(i).getActor3())) {
                a = a + MovieList.get(i).getGrade();
                b++;
            }
        }
        return a / b;
    }

    private double predictorForActor1() {
        double a = 0;
        double b = 0;
        for (int i = 0; i < MovieList.size(); i++) {
            if (Actor1.getText().toString().equalsIgnoreCase(MovieList.get(i).getActor1()) ||
                    Actor1.getText().toString().equalsIgnoreCase(MovieList.get(i).getActor2()) ||
                    Actor1.getText().toString().equalsIgnoreCase(MovieList.get(i).getActor3())) {
                a = a + MovieList.get(i).getGrade();
                b++;
            }
        }
        return a / b;

    }
}
