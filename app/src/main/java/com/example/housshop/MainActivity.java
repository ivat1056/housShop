package com.example.housshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnconnect=(Button) findViewById(R.id.button);
        btnconnect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TextView Name=(TextView) findViewById(R.id.Serch);
                ConSQL c= new ConSQL();
                  connection =  c.conclass();
                if(c !=null){
                    try {
                        String sqlstatement = "Select Name from Category";
                        Statement smt = connection.createStatement();
                        ResultSet set = smt.executeQuery(sqlstatement);
                        while (set.next()) {
                            Name.setText(set.getString(2));
                        }
                        connection.close();
                    }
                    catch (Exception e)
                    {
                        Log.e("Error: ",e.getMessage());
                    }
                }
            }
        });
    }

}