package com.example.housshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Connection connection;
    List<Mask> data;
    ListView listView;
    AdapterMask pAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); ///

        GetTextFromSQL();
    }
    public void enterMobile() {
        pAdapter.notifyDataSetInvalidated();
        listView.setAdapter(pAdapter);
    }
    public void GetTextFromSQL() {
        data = new ArrayList<Mask>();
        listView = findViewById(R.id.lvData);
        pAdapter = new AdapterMask(MainActivity.this, data);
        try {
            ConSQL connectionHelper = new ConSQL();
            connection = connectionHelper.connectionClass();

            if (connection != null) {


                String query = "Select ID, Name, Cost, Photo  From Shop";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Mask tempMask = new Mask
                            (resultSet.getInt("ID"),
                                    resultSet.getString("Name"),
                                    resultSet.getString("Cost"),
                                    resultSet.getString("Photo")
                            );

                    data.add(tempMask);
                    pAdapter.notifyDataSetInvalidated();
                }
                connection.close();
            }
            else
            {

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        enterMobile();

    }
    public void operationAdd(View view) {

        Intent intent = new Intent(this, Main_ActivityAdd_in_Date.class);
        startActivity(intent);
    }
}