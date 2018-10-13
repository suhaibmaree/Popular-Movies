package com.example.suhaib.popularmovies;


import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.suhaib.popularmovies.database.AppDatabase;
import com.example.suhaib.popularmovies.utilities.NetworkUtils;
import com.example.suhaib.popularmovies.utilities.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String ARG_FLAG = "flag";
    private RecyclerView recyclerView;
    private Adapter movieAdapter;
    ProgressBar mProgressBar;
    private String popularMovies;
    private String topRatedMovies;
    int mNoOfColumns;
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //Please note that you should set your [API KEY] in myKey from https://www.themoviedb.org/
    private String myKey = "b4999fff82a03f767ca4f5fb9ab9521f";
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    ArrayList<Movie> mPopularList;
    ArrayList<Movie> mTopTopRatedList;
    private int flag;

    private static String LIST_STATE = "list_state";
    private Parcelable savedRecyclerState;
    private static final String BUNDEL_RECYCLER_LAYOUT = "recycler_layout";
    private ArrayList<Movie> moviesInstence = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.indeterminateBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mNoOfColumns = Utility.calculateNoOfColumns(getApplicationContext());
        NetworkUtils.networkStatus(MainActivity.this);

        Parcelable state = null;
        if (savedInstanceState != null) {
            state = savedInstanceState.getParcelable(BUNDEL_RECYCLER_LAYOUT);
        }

        if (savedInstanceState != null) {
            flag = savedInstanceState.getInt(ARG_FLAG);
        } else
            flag = 0;

        if (flag == 2)
            showFromDatabase();
        else
            new FetchMovies(flag ,state).execute();

        initViews(state);

        updateTitle();
    }//end onCreate

    private void initViews(Parcelable savedRecyclerState) {

        RecyclerView.LayoutManager m = new GridLayoutManager(this, mNoOfColumns);
        if (savedRecyclerState != null)
            m.onRestoreInstanceState(savedRecyclerState);

        recyclerView = findViewById(R.id.recycler_view);
        movieAdapter = new Adapter(MainActivity.this, mPopularList);
        recyclerView.setLayoutManager(m);
        recyclerView.setAdapter(movieAdapter);

    }//end initViews

    //AsyncTask
    public class FetchMovies extends AsyncTask<Void, Void, Void> {
        int flag;
        Parcelable state;

        FetchMovies(int flag , Parcelable state ) {
            this.flag = flag;
            this.state = state;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            popularMovies = "http://api.themoviedb.org/3/movie/popular?api_key="
                    + myKey;

            topRatedMovies = "http://api.themoviedb.org/3/movie/top_rated?api_key="
                    + myKey;
            mPopularList = new ArrayList<>();
            mTopTopRatedList = new ArrayList<>();
            try {
                mPopularList = NetworkUtils.fetchData(popularMovies); //Get popular movies
                mTopTopRatedList = NetworkUtils.fetchData(topRatedMovies); //Get top rated movies
            }//end try
            catch (IOException e) {
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
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            mProgressBar.setVisibility(View.INVISIBLE);

            if (flag == 0) {
                movieAdapter.setMovieList(mPopularList);
                if(state != null)
                initViews(state);
            }
            else if (flag == 1) {
                movieAdapter.setMovieList(mTopTopRatedList);
                if (state != null)
                    initViews(state);
            }
        }

    }//end AsyncTask


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.pop_movies) {
            refreshList(mPopularList);
            flag = 0;

        } else if (id == R.id.top_movies) {
            refreshList(mTopTopRatedList);
            flag = 1;
        } else if (id == R.id.favorites) {
            showFromDatabase();
            flag = 2;
        }

        updateTitle();
        return super.onOptionsItemSelected(item);
    }

    void updateTitle() {
        if (flag == 0)
            setTitle("Most Populer");
        else if (flag == 1)
            setTitle("Top Rated");
        else if (flag == 2)
            setTitle("Favorites");
    }

    private void showFromDatabase() {
        AppDatabase.getInstance(this).getMoviesDao()
                .getAllMovies()
                .observe(this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {
                        if (flag == 2)
                            refreshList(movies);
                    }
                });
    }

    private void refreshList(List<Movie> list) {
        movieAdapter.setMovieList(list);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_FLAG, flag);
        if (flag == 0) {
            outState.putParcelable(BUNDEL_RECYCLER_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
        } else if (flag == 1) {
            outState.putParcelable(BUNDEL_RECYCLER_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
        }
    }


}

