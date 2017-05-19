package uk.co.adampie.jenkins;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.R.attr.id;

public class AddServer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_server);

        setTitle("Add a new Server");

        final EditText address = (EditText)findViewById(R.id.serverAddress);
        final EditText username = (EditText)findViewById(R.id.userName);
        final EditText password = (EditText)findViewById(R.id.password);

        Button button = (Button) findViewById(R.id.addServer1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("ServerList", 0);
                SharedPreferences.Editor editor = pref.edit();

                editor.putString((pref.getAll().size()/3)+"address",address.getText().toString());

                if(username.getText().toString().equals("")){
                    editor.putString((pref.getAll().size()/3)+"username","empty");
                } else {
                    editor.putString((pref.getAll().size()/3)+"username", username.getText().toString());
                }
                if(password.getText().toString().equals("")) {
                    editor.putString((pref.getAll().size()/3)+"password","empty");
                } else {
                    editor.putString((pref.getAll().size()/3)+"password", password.getText().toString());
                }
                editor.commit();

                Intent myIntent = new Intent(AddServer.this, MainActivity.class);
                AddServer.this.startActivity(myIntent);
            }
        });
    }
}
