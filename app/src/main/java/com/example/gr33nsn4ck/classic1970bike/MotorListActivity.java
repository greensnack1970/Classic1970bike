package com.example.gr33nsn4ck.classic1970bike;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import com.koushikdutta.ion.Ion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MotorListActivity extends AppCompatActivity {

    ListView listView;
    CustomAdapter_MortorcycleList adapter;
    int page = 1;
    String category;

    ArrayList<String> id = new ArrayList<String>();
    ArrayList<String> topic = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> datetime = new ArrayList<String>();
    ArrayList<String> path_file = new ArrayList<String>();
    ArrayList<String> image_0 = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor_pager_list);

        category = getIntent().getStringExtra("category");
        new GetFeedListTask().execute();

        listView = (ListView) findViewById(R.id.motor_feedlist);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int position;         // บอกตำแหน่งของ listview แถวล่างสุดที่มองเห็น
            int totalItemCount;   // จะเพิ่มทีละ 50 เมื่อเลื่อนลงถึงล่างสุด

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {  // state : 0=stop , 1=start , 2=scrolling
                // ถ้าตำแหน่งล่าสุดเท่ากับตำแหน่งล่างสุดและหยุดนิ่งแล้ว
                if (position == totalItemCount && scrollState == 0) {
                    new GetFeedListTask().execute();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                position = firstVisibleItem + visibleItemCount;
                this.totalItemCount = totalItemCount;
            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long long_id) {
                Intent intent = new Intent(MotorListActivity.this, ShowDetailActivity.class);
                intent.putExtra("id", id.get(position));
                startActivity(intent);
            }
        });


    }



    class GetFeedListTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog = new ProgressDialog(MotorListActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage(".. Loading ..");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String response = null;
            try {
                String url = "http://trymycmh.16mb.com/index.php/api/"+category+"/page/"+page;
                response = Ion.with(getApplicationContext()).load(url).asString().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            progressDialog.dismiss();

            if(response != null) {
                try {
                    json2ArrayList(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(page > 1){
                    adapter.notifyDataSetChanged();
                }else {
                    adapter = new CustomAdapter_MortorcycleList(getApplicationContext(), id,topic, price, datetime,path_file, image_0);
                    listView.setAdapter(adapter);
                }
                page += 1;
            }
        }

        private void json2ArrayList(String response) throws JSONException {
            JSONObject json_obj = new JSONObject(response);
            JSONArray array = json_obj.getJSONArray("data");
            for (int i=0; i<=array.length(); i++) {
                id.add(array.getJSONObject(i).getString("id"));
                topic.add(array.getJSONObject(i).getString("topic"));
                price.add(array.getJSONObject(i).getString("price"));
                datetime.add(array.getJSONObject(i).getString("datetime"));
                path_file.add(array.getJSONObject(i).getString("path_file"));
                image_0.add(array.getJSONObject(i).getString("image_0"));
            }
        }


    } // end asynctask

}
