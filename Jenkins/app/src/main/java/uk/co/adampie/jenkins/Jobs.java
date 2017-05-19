package uk.co.adampie.jenkins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Jobs extends AppCompatActivity {

    private ListView jobsListView;
    List<String> JobsList = new ArrayList<String>();
    JSONObject result;
    String base = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);

        jobsListView = (ListView) findViewById(R.id.jobs_list_view);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, JobsList);
        jobsListView.setAdapter(adapter);

        setTitle("List of Jobs");

        Intent intent = getIntent();
        base = intent.getStringExtra("server");

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = base+"/view/All/api/json";

        System.out.println("LATEST URL - "+url);

        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,url,null,success(),fail());
        queue.add(myReq);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                Intent myIntent = new Intent(Jobs.this, Projects.class);
                myIntent.putExtra("name", JobsList.get(position));
                System.out.println("Jobs - "+base+"/view/All/job/"+JobsList.get(position));
                myIntent.putExtra("url", base+"/view/All/job/"+JobsList.get(position));

                Jobs.this.startActivity(myIntent);
            }
        };
        jobsListView.setOnItemClickListener(itemClickListener);
    }

    public void draw(){
        jobsListView = (ListView) findViewById(R.id.jobs_list_view);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, JobsList);
        jobsListView.setAdapter(adapter);
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
                    JSONArray jobs = response.getJSONArray("jobs");
                    for(int i = 0; i < response.getJSONArray("jobs").length(); i++){
                        String jobName = jobs.getJSONObject(i).getString("name");
                        addJob(jobName);
                    }
                    draw();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void addJob(String name){
        JobsList.add(name);
    }
}
