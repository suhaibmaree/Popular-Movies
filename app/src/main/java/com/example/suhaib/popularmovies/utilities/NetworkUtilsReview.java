package com.example.suhaib.popularmovies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.suhaib.popularmovies.Review;
import com.example.suhaib.popularmovies.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkUtilsReview {

    public static ArrayList<Review> fetchData(String url) throws IOException {
        ArrayList<Review> Reviews = new ArrayList<Review>();
        URL new_url = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) new_url.openConnection();
        try {

            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                String results = scanner.next();
                parseJson(results, Reviews);
            } else {
                return null;
            }
        } finally {
            connection.disconnect();
        }
        return Reviews;
    }//end Fetch Data

    public static void parseJson(String data, ArrayList<Review> list) {

        try {

            JSONObject mainObject = new JSONObject(data);
            JSONArray resArray = mainObject.getJSONArray("results");

            for (int i = 0; i < resArray.length(); i++) {
                JSONObject jsonObject = resArray.getJSONObject(i);
                Review review = new Review(); //New Review object
                review.setAuthor(jsonObject.getString("author"));
                review.setContent(jsonObject.getString("content"));
                //Adding a new Trailer object into ArrayList
                list.add(review);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }//end parsJson

    public static Boolean networkStatus(Context context){
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        Toast.makeText(context,"No Internet Connection",Toast.LENGTH_LONG).show();
        return false;
    }
}
