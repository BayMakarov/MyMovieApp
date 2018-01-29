package com.example.hp.thesiswork;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class IntentActivity extends AppCompatActivity {

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        readMovieData();
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        // @Override
        //public void onClick(View view) {
        //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //        .setAction("Action", null).show();

        //};

        txt = (TextView) findViewById(R.id.txt);

        // pick call made to Activity2 via Intent
        Intent myLocalIntent = getIntent();
        MainActivity mm = new MainActivity();


        // look into the bundle sent to Activity2 for data items
        Bundle myBundle = myLocalIntent.getExtras();
        int year1 = Integer.parseInt(myBundle.getString("val1"));
        int year2 = Integer.parseInt(myBundle.getString("val2"));
        String genre = myBundle.getString("val3");
        String rating = myBundle.getString("val4");
        String actor = myBundle.getString("val5");
        int rate = Integer.parseInt(String.valueOf(rating.charAt(rating.length() - 1)));




        ArrayList<String> ar = new ArrayList<String>();

        if ((year1 > year2)) {
            txt.setText("You gave wrong inputs");
        }
        if (genre.equalsIgnoreCase("Show me any movie")) {
            for (int i = 0; i < MovieList.size(); i++) {
                String m = "";


                m = "You can watch; \n" + MovieList.get(i).getMovieTitle() + "." +
                        "\nDirected by; " +
                        MovieList.get(i).getDirectorName() + ".\n Actors; " +
                        MovieList.get(i).getActor1() + ", " + MovieList.get(i).getActor2() + ", " +
                        MovieList.get(i).getActor3() + ". \nIMDB Grade is " + MovieList.get(i).getGrade() +
                        " \nMade in " + MovieList.get(i).getCountry() + ".\n Language; " +
                        MovieList.get(i).getLanguage() + ".\n Release date; " + MovieList.get(i).getYear() +
                        "\n IMDB Link; \n" + MovieList.get(i).getImdbLink();


                ar.add(m);


            }
        }
        if (actor.equalsIgnoreCase("")) {
            for (int i = 0; i < MovieList.size(); i++) {
                String m = "";
                int b = 0;

                String[] parts = MovieList.get(i).getGenre().split("\\|");
                List<String> itemList = Arrays.asList(parts);
                Log.d("dsf", itemList.toString());


                if (((year1 <= MovieList.get(i).getYear()) && (year2 >= MovieList.get(i).getYear()))  &&
                        (rate < MovieList.get(i).getGrade())
                        && (itemList.contains(genre))
                        ) {
                    Log.d("bu ef", Boolean.toString(Arrays.asList(itemList).contains(genre)));

                    m = "You can watch; " + MovieList.get(i).getMovieTitle() + ",\n directed by; " +
                            MovieList.get(i).getDirectorName() + ".\n Actors; " +
                            MovieList.get(i).getActor1() + ", " + MovieList.get(i).getActor2() + ", " +
                            MovieList.get(i).getActor3() + ".\n IMDB Grade is " + MovieList.get(i).getGrade() +
                            " .\n Made in " + MovieList.get(i).getCountry() + ". Language; " +
                            MovieList.get(i).getLanguage() + ".\n Release date; " + MovieList.get(i).getYear() +
                            "\n IMDB Link; \n" + MovieList.get(i).getImdbLink();


                    ar.add(m);
                    b++;


                }}




        } else {
            for (int i = 0; i < MovieList.size(); i++) {
                String m = "";
                int b = 0;

                String[] parts = MovieList.get(i).getGenre().split("\\|");
                List<String> itemList = Arrays.asList(parts);
                Log.d("dsf", itemList.toString());


                if (((year1 <= MovieList.get(i).getYear()) && (year2 >= MovieList.get(i).getYear())) &&
                        (actor.equalsIgnoreCase(MovieList.get(i).getActor1()) ||
                                actor.equalsIgnoreCase(MovieList.get(i).getActor2())
                                || actor.equalsIgnoreCase(MovieList.get(i).getActor3())) &&
                        (rate < MovieList.get(i).getGrade())
                        && (itemList.contains(genre))
                        ) {
                    Log.d("bu ef", Boolean.toString(Arrays.asList(itemList).contains(genre)));

                    m = "You can watch; " + MovieList.get(i).getMovieTitle() + ",\n directed by; " +
                            MovieList.get(i).getDirectorName() + ".\nActors; " +
                            MovieList.get(i).getActor1() + ", " + MovieList.get(i).getActor2() + ", " +
                            MovieList.get(i).getActor3() + ".\n IMDB Grade is " + MovieList.get(i).getGrade() +
                            " .\n Made in " + MovieList.get(i).getCountry() + ". Language; " +
                            MovieList.get(i).getLanguage() + ".\n Release date; " + MovieList.get(i).getYear() +
                            "\n IMDB Link; \n" + MovieList.get(i).getImdbLink();


                    ar.add(m);
                    b++;


                }


            }
        }
        Random rnd = new Random();
        if (ar.size() != 0) {
            int sayi = rnd.nextInt(ar.size());
            txt.setText(ar.get(sayi));
        } else {
            txt.setText("No movie to show");
        }


    }


    private List<Movies> MovieList = new ArrayList<Movies>();

    //This nethod takes datas from movie_metadata.csv and wrties to Array<List>
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


}

