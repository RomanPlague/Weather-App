package com.example.weather;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Collections;
import java.util.List;


public class MainActivity extends Activity {

    Button btn;
    TextView currentTemp,location, conditions;
    ProgressDialog pd;
    ImageView imageView;
    GridView gridView;

    public static String temp;
    public static String min;
    public static String max;
    public static String humidity;
    public static String pressure;
    public static String speed=null;
    public static String direction=null;
    public static Context a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn = (Button) findViewById(R.id.button);
        currentTemp = (TextView) findViewById(R.id.currentTemp);
        location=(TextView) findViewById(R.id.dataLocation);
        conditions=(TextView) findViewById(R.id.conditions);
        imageView=(ImageView) findViewById(R.id.conditionImage);

        gridView=(GridView) findViewById(R.id.gridView);
        new JsonTask().execute("https://api.openweathermap.org/data/2.5/weather?id=5509403&appid=e7f383cea648c3cd85807a7f5d839d00&units=Imperial");

        a=this;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonTask().execute("https://api.openweathermap.org/data/2.5/weather?id=5509403&appid=e7f383cea648c3cd85807a7f5d839d00&units=Imperial");
            }
        });


    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            String locationData=null;
            String condition=null;
            String icon=null;
            JSONObject obj,main,wind,today;
            JSONArray wea;
            try {
                obj = new JSONObject(result);
                main = new JSONObject(obj.getString("main"));
                wind = new JSONObject(obj.getString("wind"));
                wea = new JSONArray(obj.getString("weather"));
                today = new JSONObject(wea.getString(0));
                temp=main.getString("temp");
                min=main.getString("temp_min")+"\u00B0F";
                max=main.getString("temp_max")+"\u00B0F";
                humidity=main.getString("humidity")+"%";
                pressure=main.getString("pressure")+" hPa";
                speed=wind.getString("speed")+" mph";
                direction=wind.getString("deg");
                icon=today.getString("icon");
                condition=today.getString("description");
                locationData=obj.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //"Tempature: "+temp+"\u00B0 F\nDaily Min: "+min+"\u00B0 F\nDaily Max: "+max+"\u00B0 F\nHumidity: "+humidity+"%\nPressure: "+pressure+"mm Hg"
            String[] values = {
                    "High",
                    "Wind Speed",
                    "Pressure",
                    "Low",
                    "Direction",
                    "Humidity"
            };

            String[] weather = {
                    max,
                    speed,
                    pressure,
                    min,
                    direction,
                    humidity
            };


            currentTemp.setText(temp+"\u00B0F");
            location.setText(locationData);
            conditions.setText(condition);
            setConditionImage(icon);


            GridAdapter gridAdapter = new GridAdapter(a,values,weather);
            gridView.setAdapter(gridAdapter);
            //The order of Data
            //daily high, low, wind speed, humidity, barometric pressure,



        }
    }

    public class GridAdapter extends BaseAdapter {

        Context context;
        View view;
        String [] labels,conditions;
        LayoutInflater layoutInflater;

        public GridAdapter(Context context,String [] labels,String [] conditions) {
            this.context = context;
            this.labels=labels;
            this.conditions=conditions;
        }

        @Override
        public int getCount() {
            return labels.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView==null){

                view=new View(context);
                view = layoutInflater.inflate(R.layout.single_item,null);
                TextView textDataView = (TextView) view.findViewById(R.id.textDataview);
                TextView textView = (TextView) view.findViewById(R.id.textview);
                textView.setText(labels[position]);
                textDataView.setText(conditions[position]);
            }

            return view;
        }
    }
    public void setConditionImage(String iconId){
        Drawable icon=null;
        switch(iconId){
            case "01d": icon=getResources().getDrawable(R.drawable.i01d);break;
            case "02d": icon=getResources().getDrawable(R.drawable.i02d);break;
            case "01n": icon=getResources().getDrawable(R.drawable.i01n);break;
            case "02n": icon=getResources().getDrawable(R.drawable.i02n);break;
            case "03d":
            case "03n": icon=getResources().getDrawable(R.drawable.i03);break;
            case "04d":
            case "04n": icon=getResources().getDrawable(R.drawable.i04);break;
            case "09d":
            case "09n": icon=getResources().getDrawable(R.drawable.i09);break;
            case "10d": icon=getResources().getDrawable(R.drawable.i10d);break;
            case "10n": icon=getResources().getDrawable(R.drawable.i10n);break;
            case "11d":
            case "11n": icon=getResources().getDrawable(R.drawable.i11);break;
            case "13d":
            case "13n": icon=getResources().getDrawable(R.drawable.i13);break;
            default: icon=getResources().getDrawable(R.drawable.i50);break;
        }
        imageView.setImageDrawable(icon);
    }
}
