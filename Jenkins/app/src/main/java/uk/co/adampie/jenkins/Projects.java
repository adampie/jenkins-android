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

public class Projects extends AppCompatActivity {

    String base = "";
    private ListView projectsListView;
    List<String> ProjectsList = new ArrayList<String>();
    JSONObject result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        setTitle(name);

        projectsListView = (ListView) findViewById(R.id.projects_list_view);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ProjectsList);
        projectsListView.setAdapter(adapter);

        base = intent.getStringExtra("url");
        String url = base+"/api/json";
//        System.out.println("LATEST URL - "+url);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,url,null,success(),fail());
        queue.add(myReq);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                Intent myIntent = new Intent(Projects.this, SingleProject.class);
                myIntent.putExtra("name", ProjectsList.get(position));
                int temp = Integer.parseInt(ProjectsList.get(position).replaceAll("[\\D]", ""));
                myIntent.putExtra("url", base+"/"+temp);
                Projects.this.startActivity(myIntent);
            }
        };
        projectsListView.setOnItemClickListener(itemClickListener);
    }
    public void draw(){
        projectsListView = (ListView) findViewById(R.id.projects_list_view);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ProjectsList);
        projectsListView.setAdapter(adapter);
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
                    JSONArray projects = response.getJSONArray("builds");
                    for(int i = 0; i < response.getJSONArray("builds").length(); i++){
                        String jobName = projects.getJSONObject(i).getString("number");
                        addProject("Build "+jobName);
                    }
                    draw();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void addProject(String name){

        ProjectsList.add(name);
    }
}
