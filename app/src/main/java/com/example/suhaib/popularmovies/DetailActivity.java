package com.example.suhaib.popularmovies;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.suhaib.popularmovies.database.AppDatabase;
import com.example.suhaib.popularmovies.utilities.NetworkUtils;
import com.example.suhaib.popularmovies.utilities.NetworkUtilsTrailer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by suhaib on 9/8/18.
 */

public class DetailActivity extends AppCompatActivity {
    TextView nameOfMovie;
    TextView plotSynopsis;
    TextView userRating;
    TextView releaseData;
    ImageView imageView;
    ProgressBar mProgressBar;
    CollapsingToolbarLayout collapsingToolbarLayout = null;
    private RecyclerView recyclerView;
    private TrailerAdapter trailerAdapter;
    ArrayList<Trailer> mTrailerList;
    private String trailerUrl;
    private Movie mMovie;
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //Please note that you should set your [API KEY] in myKey from https://www.themoviedb.org/
    private String myKey = "b4999fff82a03f767ca4f5fb9ab9521f";
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private boolean added = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar);
        collapsingToolbarLayout.setTitle("Movie Details");
        //++++++++++++++++++++++++++++++++++++++++++++
        imageView = findViewById(R.id.image_header);
        nameOfMovie =findViewById(R.id.title);
        plotSynopsis = findViewById(R.id.plotsynopsis);
        userRating = findViewById(R.id.userrating);
        releaseData = findViewById(R.id.releasedate);
        //+++++++++++++++++++++++++++++++++++++++++++
        Intent intent = getIntent();
        if(intent.hasExtra("original_title")){
            String posterPath = getIntent().getExtras().getString("poster_path");
            String movieName = getIntent().getExtras().getString("original_title");
            String synopsis = getIntent().getExtras().getString("overview");
            String rating = getIntent().getExtras().getString("vote_average");
            String dateOfRelease = getIntent().getExtras().getString("release_date");

            mMovie = (Movie) intent.getSerializableExtra("movie");

            Glide.with(this)
                    .load(posterPath)
                    .placeholder(R.drawable.load)
                    .into(imageView);
            nameOfMovie.setText(movieName);
            plotSynopsis.setText(synopsis);
            userRating.setText(rating);
            releaseData.setText(dateOfRelease);
        }
        else {
            Toast.makeText(this,"No API Data",Toast.LENGTH_SHORT).show();
        }

        NetworkUtilsTrailer.networkStatus(DetailActivity.this);
        new FetchTrailer().execute();
        initViews();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                added = AppDatabase.getInstance(DetailActivity.this)
                        .getMoviesDao()
                        .checkMovie(mMovie.getId()).size() > 0;
            }
        });


    }// end onCreat

    private void initViews(){

        recyclerView =findViewById(R.id.recycler_view1);
        trailerAdapter =new TrailerAdapter(DetailActivity.this, mTrailerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(trailerAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (added){
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            AppDatabase.getInstance(DetailActivity.this)
                                    .getMoviesDao()
                                    .delete(mMovie);
                        }
                    });
                }else
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase.getInstance(DetailActivity.this)
                                .getMoviesDao()
                                .addMovie(mMovie);
                    }
                });

            }
        });

    }//end initViews

    //AsyncTask
    public class FetchTrailer extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            int movieId = getIntent().getExtras().getInt("id");
            trailerUrl = "http://api.themoviedb.org/3/movie/"+movieId+"/videos?api_key=" +myKey;

            mTrailerList = new ArrayList<>();
            try {
                mTrailerList = NetworkUtilsTrailer.fetchData(trailerUrl); //Get Trailer movies
            }//end try
            catch (IOException e){
                e.printStackTrace();
            }//end catch
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void  s) {
            super.onPostExecute(s);
            trailerAdapter.setTrailerList(mTrailerList);
            mProgressBar.setVisibility(View.INVISIBLE);
        }

    }//end AsyncTask


}