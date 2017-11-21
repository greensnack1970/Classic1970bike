package com.example.gr33nsn4ck.classic1970bike;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class ShowFullScreenImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showfullscreenimage);
        ImageView fullscreenimg = (ImageView) findViewById(R.id.fullscreen_imageView);
        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("image_path")).into(fullscreenimg);

    }
}
