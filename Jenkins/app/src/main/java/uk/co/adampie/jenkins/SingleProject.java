package uk.co.adampie.jenkins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SingleProject extends AppCompatActivity {

    private TextView buildResult;
    private TextView buildTime;
    private EditText log;

    List<String> BuildsList = new ArrayList<String>();
    JSONObject result;
    String base = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_project);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        base = intent.getStringExtra("url");
        setTitle(name);

        buildResult = (TextView) findViewById(R.id.result);
        buildTime = (TextView) findViewById(R.id.buildTime);
        log = (EditText) findViewById(R.id.log);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = base + "/api/json";
        String logUrl = base+"/consoleText";

        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, url, null, success(), fail());
        queue.add(myReq);


        StringRequest strReq = new StringRequest(Request.Method.GET, logUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                log.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //System.out.println(error.getMessage());
            }
        });
        queue.add(strReq);

    }

    private Response.ErrorListener fail() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //System.out.println(error.getMessage());
            }
        };
    }
    private Response.Listener<JSONObject> success() {
        return new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                result = response;
                try {
                    buildResult.setText(result.getString("result"));
                    int timing = Integer.parseInt(result.getString("duration"))/1000;
                    int hours = timing / 3600;
                    int minutes = (timing % 3600) / 60;
                    int seconds = (timing % 3600) % 60;
                    buildTime.setText("Build time: "+hours+"h "+minutes+"m "+seconds+"s");
                } catch (JSONException e) {
                    e.printStackTrace();
            }
        }
    };
    }
    private void addBuild(String name){
        BuildsList.add(name);
    }
}
