package com.example.gr33nsn4ck.classic1970bike;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class CategoryActivity extends AppCompatActivity {

    GridView gridView;
    int[] image_list = new int[] {R.drawable.btn_classic, R.drawable.btn_enduro, R.drawable.btn_bigbike, R.drawable.btn_other};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        gridView = (GridView) findViewById(R.id.gridview_category);
        gridView.setAdapter(new CustomAdapter_Gridview_1img(getApplicationContext(), image_list));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MotorListActivity.class);
                switch(position){
                    case 0:intent.putExtra("category", "classic");break;
                    case 1:intent.putExtra("category", "enduro");break;
                    case 2:intent.putExtra("category", "bigbike");break;
                    case 3:intent.putExtra("category", "other");break;
                }
                startActivity(intent);
            }
        });
    }
}
