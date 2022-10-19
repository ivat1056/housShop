package com.example.housshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main_ActivityAdd_in_Date extends AppCompatActivity {
    Connection connection;
    List<Mask> data;
    ListView listView;
    AdapterMask pAdapter;

    EditText editName, editPrice;
    Spinner SpinnerHouse, SpinnerType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_in_date);






    }


    public void enterMobile() {
        pAdapter.notifyDataSetInvalidated();
        listView.setAdapter(pAdapter);
    }


    public void ADD_inDate_base(View view) {
        data = new ArrayList<Mask>();
        listView = findViewById(R.id.lvData);
        pAdapter = new AdapterMask(Main_ActivityAdd_in_Date.this, data);

        editName = findViewById(R.id.NameO);
        editPrice = findViewById(R.id.PriceO);

        EditText edtH = (EditText)findViewById(R.id.NameO); // название
        String editH = edtH.getText().toString();

        EditText edtP = (EditText)findViewById(R.id.PriceO); // Цена
        String editP = edtP.getText().toString();


        Spinner spinner1 = (Spinner)findViewById(R.id.SpinnerHouse); // спинер категории
        String spinHouse = spinner1.getSelectedItem().toString();

        Spinner spinner2 = (Spinner)findViewById(R.id.SpinnerType); // спинер типа
        String spinType = spinner2.getSelectedItem().toString();




        if (TextUtils.isEmpty(editName.getText().toString())) {
            Toast.makeText(this, "Заполните названия !", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(editPrice.getText().toString())) {
            Toast.makeText(this, "Заполните цену !", Toast.LENGTH_LONG).show();
        }
        else
        {
            try {
                ConSQL connectionHelper = new ConSQL();
                connection = connectionHelper.connectionClass();

                if (connection != null) {
                    String query = "INSERT INTO Shop(Category, type_of,Name, Cost, Photo) VALUES ('" + spinHouse + "', '" + spinType + "','" + editH + "', '" +  editP + "', 'NULL' ) " ;
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate(query);
                }

            }
            catch (SQLException se)
            {
                Log.e("Ошибка", se.getMessage());
            }
            editName.getText().clear();
            editPrice.getText().clear();
            Toast.makeText(this, "Запись успешно добавлена", Toast.LENGTH_LONG).show();
        }
    }
    public void Bace_main(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

