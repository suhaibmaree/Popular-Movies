package com.example.suhaib.popularmovies;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.suhaib.popularmovies.utilities.NetworkUtils;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter movieAdapter;
    ProgressBar mProgressBar;
    private String popularMovies;
    private String topRatedMovies;
    private String myKey = "b4999fff82a03f767ca4f5fb9ab9521f";
    ArrayList<Movie> mPopularList;
    ArrayList<Movie> mTopTopRatedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.indeterminateBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        new FetchMovies().execute();
        //String test = mPopularList.get(0).getTitle();
        //Toast.makeText(this,test,Toast.LENGTH_LONG).show();
        initViews();
    }//end onCreate

    private void initViews(){


        recyclerView =findViewById(R.id.recycler_view);
        movieAdapter =new Adapter(MainActivity.this, mPopularList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(movieAdapter);

    }//end initViews

    //AsyncTask
    public class FetchMovies extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            popularMovies = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="
            +myKey;

            topRatedMovies = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key="
            +myKey;
            mPopularList = new ArrayList<>();
            mTopTopRatedList = new ArrayList<>();
            try {
                    mPopularList = NetworkUtils.fetchData(popularMovies); //Get popular movies
                    mTopTopRatedList = NetworkUtils.fetchData(topRatedMovies); //Get top rated movies
            } catch (IOException e){
                e.printStackTrace();
            }
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
            mProgressBar.setVisibility(View.INVISIBLE);
            movieAdapter.setMovieList(mPopularList);
        }

    }//end AsyncTask


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
