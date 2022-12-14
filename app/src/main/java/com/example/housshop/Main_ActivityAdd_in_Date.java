package com.example.housshop;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;

import java.util.List;

public class Main_ActivityAdd_in_Date extends AppCompatActivity
{
    Connection connection;
    List<Mask> data;
    ListView listView;
    AdapterMask pAdapter;

    EditText editName, editPrice;
    String Image;
    private static final int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_in_date);

        findViewById(R.id.AddPhoto).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

    }






    public void ADD_Photo (View view){

    }





    private Bitmap getBitmapFromUri(Uri uri) throws IOException
    {
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        ImageView imageView = (ImageView) findViewById(R.id.imageView3);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == SELECT_PICTURE)
            {
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




    //?????????????????????? ?? ????????????
    public String BitMapToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat. PNG ,100, baos);
        byte [] b = baos.toByteArray();
        String temp = Base64.encodeToString(b,Base64.DEFAULT);
        return temp;
    }



    public void ADD_inDate_base(View view)
    {
        data = new ArrayList<Mask>();
        listView = findViewById(R.id.lvData);
        pAdapter = new AdapterMask(Main_ActivityAdd_in_Date.this, data);

        editName = findViewById(R.id.NameO);
        editPrice = findViewById(R.id.PriceO);


        EditText edtH = (EditText)findViewById(R.id.NameO); // ????????????????
        String editH = edtH.getText().toString();

        EditText edtP = (EditText)findViewById(R.id.PriceO); // ????????
        String editP = edtP.getText().toString();

        Spinner spinner1 = (Spinner)findViewById(R.id.SpinnerHouse); // ???????????? ??????????????????
        String spinHouse = spinner1.getSelectedItem().toString();

        Spinner spinner2 = (Spinner)findViewById(R.id.SpinnerType); // ???????????? ????????
        String spinType = spinner2.getSelectedItem().toString();

        if (TextUtils.isEmpty(editName.getText().toString()))
        {
            Toast.makeText(this, "?????????????????? ???????????????? !", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(editPrice.getText().toString())) {
            Toast.makeText(this, "?????????????????? ???????? !", Toast.LENGTH_LONG).show();
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
                    query = "INSERT INTO Shop(Category, type_of,Name, Cost, Photo) VALUES ('" + spinHouse + "', '" + spinType + "','" + editH + "', '" + editP + "', '" + Image + "') ";
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate(query);
                }
            }
            catch (SQLException se)
            {
                Log.e("????????????", se.getMessage());
            }
            Toast.makeText(this, "???????????? ?????????????? ??????????????????", Toast.LENGTH_LONG).show();
            Bace_main(view);
        }
    }
    public void Bace_main(View view)
    {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

