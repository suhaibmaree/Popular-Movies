package com.example.suhaib.popularmovies;

import android.content.Intent;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initCollapsingToolbar();
        imageView = findViewById(R.id.image_header);
        nameOfMovie =findViewById(R.id.movietitle);
        plotSynopsis = findViewById(R.id.plotsynopsis);
        userRating = findViewById(R.id.userrating);
        releaseData = findViewById(R.id.releasedate);

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

    }

    private  void initCollapsingToolbar(){
        final CollapsingToolbarLayout collapsingToolbarLayout =
                findViewById(R.id.collapsingtoolbar);
        collapsingToolbarLayout.setTitle("");
        AppBarLayout appBarLayout =findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow =false;
            int scrallRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                    if(scrallRange == -1){
                        scrallRange = appBarLayout.getTotalScrollRange();
                    }
                    if (scrallRange + verticalOffset ==0) {
                        collapsingToolbarLayout.setTitle(getString(R.string.movie_details));
                        isShow = true;
                    }
                    else if (isShow){
                        collapsingToolbarLayout.setTitle(" ");
                        isShow = false;
                    }

            }
        });
    }
}