package hk.hku.cs.comp7506_project.Wiki.kline;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vinsonguo.klinelib.model.HisData;


import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;


import hk.hku.cs.comp7506_project.R;
import hk.hku.cs.comp7506_project.Wiki.kline.model.KModel;


public class Util {

    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.getDefault());
    private static SimpleDateFormat sFormat1 = new SimpleDateFormat("HHmm", Locale.getDefault());
    private static SimpleDateFormat sFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static SimpleDateFormat sFormat3 = new SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault());


//    public static List<HisData> getHisData(Context context) {
//        InputStream is = context.getResources().openRawResource(R.raw.his_data);
//        Writer writer = new StringWriter();
//        char[] buffer = new char[1024];
//        try {
//            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            int n;
//            while ((n = reader.read(buffer)) != -1) {
//                writer.write(buffer, 0, n);
//            }
//            is.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String json = writer.toString();
//        final List<Model> list = new Gson().fromJson(json, new TypeToken<List<Model>>() {
//        }.getType());
//        List<HisData> hisData = new ArrayList<>(100);
//        for (Model m : list) {
//            HisData data = new HisData();
//            data.setHigh(m.getHigh());
//            data.setLow(m.getLow());
//            data.setOpen(m.getOpen());
//            data.setClose(m.getClose());
//            data.setVol(m.getVol());
//            try {
//                data.setDate(sFormat.parse(m.getsDate()).getTime());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            hisData.add(data);
//        }
//        return hisData;
//    }


//    public static List<HisData> get1Day(Context context) {
//        InputStream is = context.getResources().openRawResource(R.raw.oneday);
//        Writer writer = new StringWriter();
//        char[] buffer = new char[1024];
//        try {
//            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            int n;
//            while ((n = reader.read(buffer)) != -1) {
//                writer.write(buffer, 0, n);
//            }
//            is.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String json = writer.toString();
//        final List<LineModel> list = new Gson().fromJson(json, new TypeToken<List<LineModel>>() {
//        }.getType());
//        List<HisData> hisData = new ArrayList<>(100);
//        for (int i = 0; i < list.size(); i++) {
//            LineModel m = list.get(i);
//            HisData data = new HisData();
//            data.setClose(m.getPrice());
//            data.setVol(m.getVolume());
//            data.setOpen(i == 0 ? 0 : list.get(i - 1).getPrice());
//            try {
//                data.setDate(sFormat1.parse(m.getTime()).getTime());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            hisData.add(data);
//        }
//        return hisData;
//    }


//    public static class HttpAsyncTask2 extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... urls) {
//            String result = "";
//
//            try {
//                URL urlObj = new URL(urls[0]);
//
//                // Build http connection
//                HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
//                InputStream is = urlConnection.getInputStream();
//
//                // convert inputstream to string
//                Writer writer = new StringWriter();
//                char[] buffer = new char[1024];
//                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//                int n;
//                while ((n = reader.read(buffer)) != -1) {
//                    writer.write(buffer, 0, n);
//                }
//                is.close();
//
//                result = writer.toString();
//
//            } catch (Exception e) {
//                Log.d("InputStream", e.getLocalizedMessage());
//            }
//            return result;
//        }
//
//    }


//    public static List<List<HisData>> get5Day(Context context){

//            AsyncTask getTask = new HttpAsyncTask2();
//
//            try{
//                getTask.execute("http://hq.sinajs.cn/list=sh601006");
//                String res = "";
//                final List<LinkedHashMap<String, List<LineModel>>> list = new Gson().fromJson((String) res, new TypeToken<List<LinkedHashMap<String, List<LineModel>>>>() {
//                }.getType());
//                List<List<HisData>> fivedays = new ArrayList<>(5);
//
//                for (int i = 0; i < list.size(); i++) {
//
//                    List<HisData> hisData = new ArrayList<>(100);
//                    List<LineModel> lineModels = list.get(i).values().iterator().next();
//                    String time = list.get(i).keySet().iterator().next();
//
//                    for (int j = 0; j < lineModels.size(); j++) {
//                        LineModel m = lineModels.get(j);
//                        HisData data = new HisData();
//                        data.setClose(m.getPrice());
//                        data.setVol(m.getVolume());
//                        data.setOpen(j == 0 ? 0 : lineModels.get(j - 1).getPrice());
//                        try {
//                            data.setDate(sFormat3.parse(time + m.getTime()).getTime());
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        hisData.add(data);
//                    }
//                    fivedays.add(hisData);
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        return null;
//
//
//
//    }

//    public static List<List<HisData>> get5Day(Context context) {
//        InputStream is = context.getResources().openRawResource(R.raw.fiveday);
//        Writer writer = new StringWriter();
//        char[] buffer = new char[1024];
//        try {
//            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            int n;
//            while ((n = reader.read(buffer)) != -1) {
//                writer.write(buffer, 0, n);
//            }
//            is.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String json = writer.toString();
//        final List<LinkedHashMap<String, List<LineModel>>> list = new Gson().fromJson(json, new TypeToken<List<LinkedHashMap<String, List<LineModel>>>>() {
//        }.getType());
//        List<List<HisData>> fivedays = new ArrayList<>(5);
//
//        for (int i = 0; i < list.size(); i++) {
//
//            List<HisData> hisData = new ArrayList<>(100);
//            List<LineModel> lineModels = list.get(i).values().iterator().next();
//            String time = list.get(i).keySet().iterator().next();
//
//            for (int j = 0; j < lineModels.size(); j++) {
//                LineModel m = lineModels.get(j);
//                HisData data = new HisData();
//                data.setClose(m.getPrice());
//                data.setVol(m.getVolume());
//                data.setOpen(j == 0 ? 0 : lineModels.get(j - 1).getPrice());
//                try {
//                    data.setDate(sFormat3.parse(time + m.getTime()).getTime());
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                hisData.add(data);
//            }
//            fivedays.add(hisData);
//        }
//        return fivedays;
//    }





//
//
//        InputStream is = context.getResources().openRawResource(R.raw.fiveday);
//        Writer writer = new StringWriter();
//        char[] buffer = new char[1024];
//        try {
//            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            int n;
//            while ((n = reader.read(buffer)) != -1) {
//                writer.write(buffer, 0, n);
//            }
//            is.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }







    public static List<HisData> getK(Context context, int day) {
        int res = R.raw.day_k;
        if (day == 7) {
            res = R.raw.week_k;
        } else if (day == 30) {
            res = R.raw.month_k;
        }
        InputStream is = context.getResources().openRawResource(res);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String json = writer.toString();
        final List<KModel> list = new Gson().fromJson(json, new TypeToken<List<KModel>>() {
        }.getType());
        List<HisData> hisData = new ArrayList<>(100);
        for (int i = 0; i < list.size(); i++) {
            KModel m = list.get(i);
            HisData data = new HisData();
            data.setClose(m.getPrice_c());
            data.setOpen(m.getPrice_o());
            data.setHigh(m.getPrice_h());
            data.setLow(m.getPrice_l());
            data.setVol(m.getVolume());
            try {
                data.setDate(sFormat2.parse(m.getTime()).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            hisData.add(data);
        }
        return hisData;
    }
}
