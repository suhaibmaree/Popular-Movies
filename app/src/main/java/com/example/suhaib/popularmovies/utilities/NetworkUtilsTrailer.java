package com.example.suhaib.popularmovies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
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

/**
 * Created by suhaib on 10/8/18.
 */

public class NetworkUtilsTrailer {

    public static ArrayList<Trailer> fetchData(String url) throws IOException {
        ArrayList<Trailer> Trailers = new ArrayList<Trailer>();
        URL new_url = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) new_url.openConnection();
        try {

            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                String results = scanner.next();
                parseJson(results, Trailers);
            } else {
                return null;
            }
        } finally {
            connection.disconnect();
        }
        return Trailers;
    }//end Fetch Data

    public static void parseJson(String data, ArrayList<Trailer> list) {

        try {

            JSONObject mainObject = new JSONObject(data);
            JSONArray resArray = mainObject.getJSONArray("results");

            for (int i = 0; i < resArray.length(); i++) {
                JSONObject jsonObject = resArray.getJSONObject(i);
                Trailer trailer = new Trailer(); //New Trailer object
                trailer.setKey(jsonObject.getString("key"));
                trailer.setName(jsonObject.getString("name"));
                //Adding a new Trailer object into ArrayList
                list.add(trailer);
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
