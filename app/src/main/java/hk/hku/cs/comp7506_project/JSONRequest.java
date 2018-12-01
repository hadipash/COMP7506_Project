package hk.hku.cs.comp7506_project;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JSONRequest extends AsyncTask<Map<String, String>, Void, JSONObject> {
    private static final String TAG = "JSONRequest";

    @Override
    protected final JSONObject doInBackground(Map<String, String>... maps) {
        OkHttpClient client = new OkHttpClient();

        // get URL address
        HttpUrl.Builder httpBuilder = HttpUrl.parse(maps[0].get("url")).newBuilder();
        maps[0].remove("url");
        // set parameters
        for (Map.Entry<String, String> entry : maps[0].entrySet()){
            httpBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }

        Request request = new Request.Builder().url(httpBuilder.build()).build();
        try {
            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Sending request", e);
        }

        return null;
    }
}
