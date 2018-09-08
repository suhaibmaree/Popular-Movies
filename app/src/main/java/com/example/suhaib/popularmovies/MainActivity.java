package com.example.suhaib.popularmovies;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter movieAdapter;
    private List<Movie> movieList;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }//end onCreate

    private void initViews(){
        pd =new ProgressDialog(this);
        pd.setMessage("Fetching Movies");
        pd.setCancelable(false);
        pd.show();

        recyclerView =findViewById(R.id.recycler_view);
        movieList = new ArrayList<>();
        movieAdapter =new Adapter(this, movieList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(Adapter);
        loadJson();

    }//end initViews

    public void loadJson(){

    }//end loadJson


}
