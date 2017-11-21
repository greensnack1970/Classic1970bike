package com.example.gr33nsn4ck.classic1970bike;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.koushikdutta.ion.Ion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    int page = 1;
    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    CustomAdapter_MortorcycleList adapter;

    ArrayList<String> id = new ArrayList<String>();
    ArrayList<String> topic = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> datetime = new ArrayList<String>();
    ArrayList<String> path_file = new ArrayList<String>();
    ArrayList<String> image_0 = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkNetworkState()) { // เช็คการเชื่อมต่อ Internet

            // handle fcm on background
            if (getIntent().getExtras().getString("_id") != null) {
                String id_from_fcm = getIntent().getExtras().getString("_id");
                Intent intent = new Intent(MainActivity.this, ShowDetailActivity.class);
                intent.putExtra("_id", id_from_fcm);
                startActivity(intent);
            }

                new GetTask().execute();
                listView = (ListView) findViewById(R.id.listview_timeline);
                listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    int position;         // บอกตำแหน่งของ listview แถวล่างสุดที่มองเห็น
                    int totalItemCount;   // จะเพิ่มทีละ 50 เมื่อเลื่อนลงถึงล่างสุด

                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {  // state : 0=stop , 1=start , 2=scrolling
                        // ถ้าตำแหน่งล่าสุดเท่ากับตำแหน่งล่างสุดและหยุดนิ่งแล้ว
                        if (position == totalItemCount && scrollState == 0) {
                            new GetTask().execute();
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
                        Intent intent = new Intent(MainActivity.this, ShowDetailActivity.class);
                        intent.putExtra("id", id.get(position));
                        startActivity(intent);
                    }
                });


                swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });


        } else {
            Toast.makeText(this, "กรุณาเชื่อมต่ออินเตอร์เน็ต !", Toast.LENGTH_SHORT).show();
            MainActivity.this.finish();
        }
    }



    public class GetTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(".. Loading ..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... s) {
            String response;

            try {
                String url = "http://trymycmh.16mb.com/index.php/api/timeline_motorcycle/" + page;
                response = Ion.with(getApplicationContext()).load(url).asString().get();
                Thread.sleep(1000);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return null;
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


    // Check Internet Connect ? //

    public boolean checkNetworkState() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        if (isWifi || is3g) {
            return true;
        } else {
            return false;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sell:
                Intent action_sell = new Intent(MainActivity.this, SellActivity.class);
                startActivity(action_sell);
                return true;
            case R.id.action_category:
                Intent action_category = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(action_category);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





}
