package uk.co.adampie.jenkins;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView serverListView;
    List<String> ServerList = new ArrayList<String>();
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("List of Jenkins Servers");

        pref = getApplicationContext().getSharedPreferences("ServerList", 0);

        for(int i = 0; i < (pref.getAll().size()/3); i++){
            if(!pref.getAll().isEmpty()){
                ServerList.add(pref.getString(i+"address",null));
            }
        }

        serverListView = (ListView) findViewById(R.id.server_list_view);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ServerList);
        serverListView.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, AddServer.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                Intent myIntent = new Intent(MainActivity.this, Jobs.class);
                myIntent.putExtra("server", pref.getString(String.valueOf(position)+"address",null));
                MainActivity.this.startActivity(myIntent);

            }
        };

        serverListView.setOnItemClickListener(itemClickListener);
    }
}
