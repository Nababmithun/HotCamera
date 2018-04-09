package com.androidlime.hotcamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    ImageView iv;
    Button take_photo;

    int flag = 0;
    Bitmap b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        iv = (ImageView) findViewById(R.id.my_image);
        take_photo = (Button) findViewById(R.id.take_photo);


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flag == 0) {
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i, 300);
                } else if (flag == 1) {

                    savePhotoToMySdcard(b);
                    Toast.makeText(getApplicationContext(),"Photo Saved to sd card",Toast.LENGTH_SHORT).show();

                    flag = 0;
                    take_photo.setText("Take Photo");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 300 && resultCode == RESULT_OK && data != null) ;
        {

             b = (Bitmap) data.getExtras().get("data");

            iv.setImageBitmap(b);

            flag = 1;

            take_photo.setText("Save Photo");


        }
    }

    private  void savePhotoToMySdcard(Bitmap bit){
        java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String pname=sdf.format(new Date());


        String root = Environment.getExternalStorageDirectory().toString();
        File folder = new File(root+"/Mithun_Folder");
         folder.mkdirs();



        File my_file = new File(folder,pname+".png");

        try {
            FileOutputStream stream = new FileOutputStream(my_file);
            bit.compress(Bitmap.CompressFormat.PNG,100,stream);
            stream.flush();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }






}








