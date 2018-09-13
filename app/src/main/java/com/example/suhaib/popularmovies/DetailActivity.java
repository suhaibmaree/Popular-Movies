package com.example.suhaib.popularmovies;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.bumptech.glide.Glide;

/**
 * Created by suhaib on 9/8/18.
 */

public class DetailActivity extends AppCompatActivity {
    TextView nameOfMovie;
    TextView plotSynopsis;
    TextView userRating;
    TextView releaseData;
    ImageView imageView;
    CollapsingToolbarLayout collapsingToolbarLayout = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar);
        collapsingToolbarLayout.setTitle("Movie Details");
        //++++++++++++++++++++++++++++++++++++++++++++
        imageView = findViewById(R.id.image_header);
        nameOfMovie =findViewById(R.id.movietitle);
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

    }// end onCreat



}