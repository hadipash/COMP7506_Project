package hk.hku.cs.comp7506_project.Wiki;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;
import hk.hku.cs.comp7506_project.MainActivity;
import hk.hku.cs.comp7506_project.R;

public class WikiPage extends AppCompatActivity{
    TextView tvKey;
    TextView tvResponse;


    public void popItUp(String word, Context mContext, View view) {


        LayoutInflater inflater = LayoutInflater.from(mContext);
        View popupView = inflater.inflate(R.layout.activity_wiki_page, null);

        tvKey = popupView.findViewById(R.id.wikiKey);
        tvResponse =  popupView.findViewById(R.id.wikiView);

        tvKey.setText(word);
        new HttpAsyncTask().execute("https://en.wikipedia.org/w/api.php?" +
                "format=json" +
                "&action=query" +
                "&prop=extracts" +
                "&explaintext=" +
                "&titles="+word);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, 200, focusable);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        popupWindow.setAnimationStyle(R.style.popup_window_animation_phone);
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.TOP, 0, 100);

    }


    //quoted by doInBackground()
    public static String GET(String url){

        String result = "";
        try {
            URL urlObj = new URL(url);

            // Build http connection
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
            InputStream is = urlConnection.getInputStream();

            // convert inputstream to string
            if(is != null)
                result = convertInputStreamToString(is);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    // quoted by GET()
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";

        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            //Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_SHORT).show();
            String UN="",PW="",user="",wikiText="";
            try {
                JSONObject json = new JSONObject(result); // convert String to JSONObject
                //JSONArray articles = json.getJSONArray("array"); // get articles array
                JSONObject query = json.getJSONObject("query");
                JSONObject pages = query.getJSONObject("pages");
                //user=pages.toString();

                String[] str_array = pages.toString().substring(0,20).split(":");
                String string1 = str_array[0];
                String pageid=string1.substring(2, string1.length()-1);
                //user=string1+"\n"+pageid;


                JSONObject page = pages.getJSONObject(pageid);

                wikiText=(String) page.get("extract");
                String[] str_array2 = wikiText.split("==");
                wikiText="";
                for(String temp:str_array2)
                {
                    wikiText=wikiText+"\n-----------------------------------------------\n"+temp.trim();
                }

                if(!wikiText.isEmpty())
                {
                    tvResponse.setText(wikiText.trim());

                }
                else
                    tvResponse.setText("No Result Found");
            }
            catch(JSONException e)
            {
                tvResponse.setText(e.toString());
                //Toast.makeText(getBaseContext(), "JSONException!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

