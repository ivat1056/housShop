package com.example.housshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class Main_Update_in_dateBase extends AppCompatActivity {
    public  Integer idObjekt;
    Connection connection;
    List<Mask> data;
    ListView listView;
    AdapterMask pAdapter;
    String ConnectionResult;
    EditText editName, editPrice;
    Spinner spinner1, spinner2 ;

    String Image;
    private static final int SELECT_PICTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_update_in_date_base);

        Bundle arguments = getIntent().getExtras();
        idObjekt = arguments.getInt("ID");


        EditText Name1 = findViewById(R.id.NameO);
        EditText Price = findViewById(R.id.PriceO);

        try {
            ConSQL connectionHelper = new ConSQL();
            connection = connectionHelper.connectionClass();
            if (connection != null)
            {
                String query = "Select Category, type_of, Name, Cost  From Shop WHERE  ID = '" + idObjekt + "'"; // Значения по айди
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next())
                {
                    String Category = resultSet.getString(1);
                    spinner1.setSelection(Integer.parseInt(Category));
                    String type_of = resultSet.getString(2);
                    spinner2.setSelection(Integer.parseInt(type_of));
                    String Name = resultSet.getString(3);
                    editName.setText(Name);
                    String Cost = resultSet.getString(4);
                    //Toast.makeText(this, Cost, Toast.LENGTH_LONG).show();
                    editName.setText(Cost);
                }
                connection.close();
            }
            else
            {
                ConnectionResult="Проверьте соединение";
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        enterMobile();








        findViewById(R.id.UPD_Photo).setOnClickListener(new View.OnClickListener()
        {   /// фото

            public void onClick(View arg0)
            {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

            }
        });


        SelectionData_in_dataBase();


    }

    public void UPD_Photo (View view){

    }





    private Bitmap getBitmapFromUri(Uri uri) throws IOException
    {
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        ImageView imageView = (ImageView) findViewById(R.id.imageView3);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                try
                {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(null);
                imageView.setImageBitmap(bitmap);
                Image = BitMapToString(bitmap);

            }

        }
    }




    //Изображение в строку
    public String BitMapToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b = baos.toByteArray();
        String temp = Base64.encodeToString(b,Base64.DEFAULT);
        return temp;
    }




    public void SelectionData_in_dataBase()
    {


    }




    public void enterMobile()
    {
        pAdapter.notifyDataSetInvalidated();
        listView.setAdapter(pAdapter);
    }

    public void UPD_inDate_base(View view)
    {
        data = new ArrayList<Mask>();
        listView = findViewById(R.id.lvData);
        pAdapter = new AdapterMask(Main_Update_in_dateBase.this, data);


        EditText edtH = (EditText)findViewById(R.id.NameO); // название
        String editH = edtH.getText().toString();

        EditText edtP = (EditText)findViewById(R.id.PriceO); // Цена
        String editP = edtP.getText().toString();

        spinner1 = (Spinner)findViewById(R.id.SpinnerHouse); // спинер категории
        String spinHouse = spinner1.getSelectedItem().toString();

        spinner2 = (Spinner)findViewById(R.id.SpinnerType); // спинер типа
        String spinType = spinner2.getSelectedItem().toString();

        if (TextUtils.isEmpty(editName.getText().toString()))
        {
            Toast.makeText(this, "Заполните названия !", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(editPrice.getText().toString()))
        {
            Toast.makeText(this, "Заполните цену !", Toast.LENGTH_LONG).show();
        }
        else
        {
            try
            {
                ConSQL connectionHelper = new ConSQL();
                connection = connectionHelper.connectionClass();
                String query = "";
                if (connection != null)
                {
                    query = "UPDATE Shop SET Category = '" + spinHouse + "', type_of = '" + spinType + "', Name = '" + editH + "', Cost = '" + editP + "', Photo = '" + Image + "'";
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
}