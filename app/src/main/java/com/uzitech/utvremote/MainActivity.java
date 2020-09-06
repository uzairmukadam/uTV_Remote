package com.uzitech.utvremote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText ip_address, port_no;
    Button connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(getApplicationContext(), RemoteActivity.class);

        final SharedPreferences preferences = getSharedPreferences("Network Data" ,MODE_PRIVATE);

        if(!preferences.contains("IP")){
            ip_address = findViewById(R.id.ip_address);
            port_no = findViewById(R.id.port_no);
            connect = findViewById(R.id.connect_btn);

            connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ip_address.getText().toString().isEmpty() || port_no.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Enter the connection details", Toast.LENGTH_SHORT).show();
                    }else {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("IP", ip_address.getText().toString());
                        editor.putString("PORT", port_no.getText().toString());
                        editor.apply();
                        intent.putExtra("IP", ip_address.getText().toString());
                        intent.putExtra("PORT", port_no.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }else {
            intent.putExtra("IP", preferences.getString("IP", ""));
            intent.putExtra("PORT", preferences.getString("PORT", ""));
            startActivity(intent);
            finish();
        }
    }
}