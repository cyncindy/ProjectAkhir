package com.example.projectakhir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ShowData extends AppCompatActivity {
    TextView title, detail;
    ImageView image;
    //Toolbar toolbar;
    String path = "http://192.168.43.133/infoapp/image/";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_data);

        //toolbar=findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.txtTitle);
        detail = findViewById(R.id.txtDetail);
        image = findViewById(R.id.imgPhoto);
        //ambil data intent
        title.setText(getIntent().getStringExtra("title"));
        detail.setText(getIntent().getStringExtra("detail"));
        Glide.with(getApplicationContext())
                .load(path+getIntent().getStringExtra("image"))
                .into(image);
    }
    @Override
    public void onBackPressed(){
        finish();
        super.onBackPressed();
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}