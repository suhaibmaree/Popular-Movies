package com.example.suhaib.popularmovies;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.suhaib.popularmovies.api.Client;
import com.example.suhaib.popularmovies.api.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        movieAdapter =new Adapter(MainActivity.this, movieList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(movieAdapter);
        loadJson();

    }//end initViews

    public void loadJson(){
        try{
            if(BuildConfig.THE_MOVIE_DB_API.isEmpty()){
                Toast.makeText(this,"Obtain your API key from themoviedb.org",Toast.LENGTH_SHORT)
                        .show();
                pd.dismiss();
                return;
            }
            Client client =new Client();
            Service service = client.getClient().create(Service.class);
            Call<MoviesResponse> Call =service.getPopularMovies(BuildConfig.THE_MOVIE_DB_API);
            Call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies =response.body().getResults();
                    recyclerView.setAdapter(new Adapter(MainActivity.this, movies));
                    recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("Error",t.getMessage());
                    Toast.makeText(MainActivity.this,"Error Fetching Data",Toast.LENGTH_SHORT)
                            .show();

                }
            });

        }catch (Exception e){
            Log.d("Error",e.getMessage());
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }

    }//end loadJson

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
