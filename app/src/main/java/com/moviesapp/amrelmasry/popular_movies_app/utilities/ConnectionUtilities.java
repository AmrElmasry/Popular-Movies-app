package com.moviesapp.amrelmasry.popular_movies_app.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by AmrELmasry on 9/24/2015.
 */
public class ConnectionUtilities {


    public static String getJSONString(Uri uri) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JsonStr = null;

        try {

            URL url = new URL(uri.toString());

            Log.v("URI : ", "Built URI " + uri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            JsonStr = buffer.toString();
            Log.i("Success", JsonStr);

        } catch (Exception e) {
            Log.e("Connection Error", "Can't download JSON ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("Connection Error", "Can't close reader ", e);

                }
            }
        }

        return JsonStr;
    }


}
