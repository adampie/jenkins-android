package uk.co.adampie.jenkins;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.R.attr.button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getApplicationContext().getSharedPreferences("ServerDetails", MODE_PRIVATE).getAll().isEmpty()) {
            setContentView(R.layout.activity_main);
        } else {
            changeView();
        }

        final EditText address = (EditText)findViewById(R.id.serverAddress);
        final EditText username = (EditText)findViewById(R.id.userName);
        final EditText password = (EditText)findViewById(R.id.password);

        Button button = (Button) findViewById(R.id.addServer1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("ServerDetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("address",address.getText().toString());
                editor.putString("username",username.getText().toString());
                editor.putString("password",password.getText().toString());
                editor.commit();
                System.out.println(pref.getString("address",null));
                System.out.println(pref.getString("username",null));
                System.out.println(pref.getString("password",null));
                changeView();
            }
        });


    }

    private void changeView() {
        Intent Dashboard = new Intent(this, Dashboard.class);
        startActivity(Dashboard);
    }
}
