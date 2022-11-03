package com.example.housshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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

public class MainActivity extends AppCompatActivity
{
    Connection connection;
    List<Mask> data;
    ListView listView;
    AdapterMask pAdapter;
    EditText SerchS;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); ///

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        query = "Select *  From Shop";
        GetTextFromSQL(query);

    }
    public void enterMobile()
    {
        pAdapter.notifyDataSetInvalidated();
        listView.setAdapter(pAdapter);
    }

    public void GetTextFromSQL(String query) {
        data = new ArrayList<Mask>();
        listView = findViewById(R.id.lvData);
        pAdapter = new AdapterMask(MainActivity.this, data);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long ID)
            {
                DateForClick((int) ID);
            }
        });

        try {
            ConSQL connectionHelper = new ConSQL();
            connection = connectionHelper.connectionClass();

            if (connection != null)
            {




                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next())
                {
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
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        enterMobile();

    }
    public void operationAdd(View view)
    {

        Intent intent = new Intent(this, Main_ActivityAdd_in_Date.class);
        startActivity(intent);
    }
    public void DateForClick(int ID)
    {
        Intent intent = new Intent(this, Main_Update_in_dateBase.class);
        Bundle b = new Bundle();
        b.putInt("ID", ID);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
    public int Spinner1(String index) // тип категории
    {
        if (index.equals("Квартира" ))
        {
            return 1;
        }
        if (index.equals("Коттедж" ))
        {
            return 2;
        }
        if (index.equals("Коммерческая недвижимость" ))
        {
            return 3;
        }
        if (index.equals("Недвижимость за рубежом" ))
        {
            return 4;
        }
        else
        {
            return 5;
        }
    }

    public int Spinner2(String index) // тип сделки
    {
        if (index.equals("Аренда" ))
        {
            return 1;
        }
        else
        {
            return 2;
        }
    }

    public int Spinner3(String index) // сортировка
    {
        if (index.equals("Возрастание" ))
        {
            return 1;
        }
        else
        {
            return 2;
        }
    }

    public void dellSerch(View view)
    {   EditText serch = findViewById(R.id.Serch);
        Spinner spinner1  = findViewById(R.id.SpinnerHouse); // категория
        Spinner spinner2 = findViewById(R.id.SpinnerType); // тип сделки
        Spinner spinner3 = findViewById(R.id.SpinnerSort); // убывания / возрастания
        spinner1.setSelection(0);
        spinner2.setSelection(0);
        spinner3.setSelection(0);
        serch.setText("");
        Serch_on_click(view);
        TextView dellS = findViewById(R.id.dellSerch);
        dellS.setVisibility(View.GONE);
    }


    public void Serch_on_click(View view)
    {
        data = new ArrayList<Mask>();
        listView = findViewById(R.id.lvData);
        pAdapter = new AdapterMask(MainActivity.this, data);

        SerchS = findViewById(R.id.Serch); // поисковый запрос
        String SelectSerch = SerchS.getText().toString();



            ConSQL connectionHelper = new ConSQL();
            connection = connectionHelper.connectionClass();
            String query = "";
            if (connection != null)
            {
                query = "Select * From Shop where Name  LIKE '%" + SelectSerch + "%'";
                GetTextFromSQL(query);
            }



        TextView dellS = findViewById(R.id.dellSerch);
        dellS.setVisibility(View.VISIBLE);




    }

    public void Sort_on_click(View view)
    {


        TextView dellS = findViewById(R.id.dellSerch);
        dellS.setVisibility(View.VISIBLE);




        Spinner spinner1 = (Spinner)findViewById(R.id.SpinnerHouse); // спинер категории
        String spinHouse = spinner1.getSelectedItem().toString();

        Spinner spinner2 = (Spinner)findViewById(R.id.SpinnerType); // спинер типа
        String spinType = spinner2.getSelectedItem().toString();

        Spinner spinner3 = (Spinner)findViewById(R.id.SpinnerSort); // спинер сортировки
        String spinSort = spinner3.getSelectedItem().toString();

        if(spinSort.equals("Возрастание" ) )
        {
            ConSQL connectionHelper = new ConSQL();
            connection = connectionHelper.connectionClass();
            if (connection != null)
            {
                query = "Select * From Shop where Category ='" + spinHouse +  "' and type_of =  '" + spinType +  "' ORDER BY Name ";
                GetTextFromSQL(query);
            }
        }
        else
        {
            ConSQL connectionHelper = new ConSQL();
            connection = connectionHelper.connectionClass();
            String query = "";
            if (connection != null)
            {
                query = "Select * From Shop where Category ='" + spinHouse +  "' and type_of =  '" + spinType +  "' ORDER BY Name DESC ";
                GetTextFromSQL(query);
            }
        }

    }
}