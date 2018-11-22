package hk.hku.cs.comp7506_project.Wiki;

import android.content.Context;
import android.graphics.Paint;
import android.os.AsyncTask;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.WindowManager;
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
import hk.hku.cs.comp7506_project.R;

public class WikiPage extends AppCompatActivity{
    TextView tvTitle;
    TextView tvText;
    TextView tvMore;


    public void popItUp(String word, Context mContext, View view) {


        LayoutInflater inflater = LayoutInflater.from(mContext);
        View popupView = inflater.inflate(R.layout.activity_wiki_page, null);

        tvTitle = popupView.findViewById(R.id.wikiKey);
        tvText =  popupView.findViewById(R.id.wikiView);
        tvMore =  popupView.findViewById(R.id.wikiMore);


        new HttpAsyncTask().execute("https://en.wikipedia.org/w/api.php?" +
                "format=json" +
                "&action=query" +
                "&prop=extracts" +
                "&explaintext=" +
                "&titles="+word);


        // get the width & height of the screen
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, (int)(0.8*width), (int)(0.25*height), focusable);

        //popupWindow.setBackgroundDrawable(new BitmapDrawable());

        popupWindow.setAnimationStyle(R.style.popup_window_animation_phone);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.TOP, 0, (int)(0.1*height));

    }


    //called by doInBackground()
    public static String GET(String url){

        String result = "";
        try {
            URL urlObj = new URL(url);

            // Build http connection
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
            InputStream is = urlConnection.getInputStream();

            // convert inputstream to string
            if(is != null) {
                result = convertInputStreamToString(is);
                Log.d("InputStream", "Received");
            }
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    // called by GET()
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
            String wikiText = "";
            String wikiTextShow = "";
            String wikiTitle = "";
            try {

                JSONObject json = new JSONObject(result); // convert String to JSONObject

                JSONObject query = json.getJSONObject("query");
                JSONObject pages = query.getJSONObject("pages");


                String[] str_array = pages.toString().substring(0,20).split(":");
                String string1 = str_array[0];
                String pageid=string1.substring(2, string1.length()-1);


                JSONObject page = pages.getJSONObject(pageid);

                wikiTitle = (String) page.get("title");
                wikiText = (String) page.get("extract");

                String[] str_array2 = wikiText.split("==");


                if(!wikiText.isEmpty())
                {
                    wikiTextShow =  str_array2[0];

                    tvTitle.setText(wikiTitle.trim());
                    tvText.setText(wikiTextShow);
                    tvMore.setText("more");
                    tvMore.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                }
                else
                    tvText.setText("Sorry, No Result Found");

                // Detect the lines number of the page
                tvText.post(new Runnable() {
                    @Override
                    public void run() {
                        int lines = tvText.getLineCount();
                        tvMore.setVisibility(View.GONE);
                        if (lines > 8) {
                            tvText.setMaxLines(8);
                            tvMore.setVisibility(View.VISIBLE);

                            // Click "more"
                            tvMore.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    tvMore.setVisibility(View.GONE);
                                    tvText.setMaxLines(Integer.MAX_VALUE);
                                }
                            });
                        }
                    }
                });

            }
            catch(JSONException e)
            {
                tvText.setText(e.toString());
                //Toast.makeText(getBaseContext(), "JSONException!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}