package com.example.gr33nsn4ck.classic1970bike;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.messaging.RemoteMessage;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class ShowDetailActivity extends AppCompatActivity {

    TextView txttopic;
    TextView txtdetail;
    TextView txtprice;
    TextView txtcategory;
    TextView txtphone;
    TextView txtprovince;
    TextView txtdatetime;
    TextView txtstatus;
    TextView txtipaddress;
    TextView txtvisited;
    TextView txtname;

    ArrayList<String> img_arr = new ArrayList<String>();
    String id;
    RemoteMessage remoteMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        ShowDetailActivity.this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);

        if(getIntent().getStringExtra("_id") != null) {
            id = getIntent().getStringExtra("_id");
            Log.e("Id From FCM", id);
        }

        if(getIntent().getStringExtra("id") != null){
            id = getIntent().getStringExtra("id");
            Log.e("Id From Motorcyclelist", id);
        }

        new getDetailTask().execute();
    }


    // Get Request to api
    public class getDetailTask extends AsyncTask<String, Void, String> {

        private ProgressDialog mProgressDialog = new ProgressDialog(ShowDetailActivity.this);

        private String topic = "";
        private String detail = "";
        private String price = "";
        private String category = "";
        private String phone = "";
        private String province = "";
        private String poster_id = "";
        private String datetime = "";
        private String status = "";
        private String ipaddress = "";
        private String visited = "";
        private String path_file = "";
        private String name = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setMessage("Loading data....");
            mProgressDialog.show();
            mProgressDialog.setCancelable(false);

            txttopic = (TextView) findViewById(R.id.txttopic);
            txtdetail = (TextView) findViewById(R.id.txtdetail);
            txtprice = (TextView) findViewById(R.id.txtprice);
            txtcategory = (TextView) findViewById(R.id.txtcategory);
            txtphone = (TextView) findViewById(R.id.txtphone);
            txtprovince = (TextView) findViewById(R.id.txtprovince);
            txtdatetime = (TextView) findViewById(R.id.txtdatetime);
            txtstatus = (TextView) findViewById(R.id.txtstatus);
            txtipaddress = (TextView) findViewById(R.id.txtipaddress);
            txtvisited = (TextView) findViewById(R.id.txtvisited);
            txtname = (TextView) findViewById(R.id.txtname);
        }

        protected String doInBackground(String... url) {

            String response = null;

            try {
                response = Ion.with(getApplicationContext()).load("http://trymycmh.16mb.com/index.php/api/motorcycle/" + id).asString().get();
                Thread.sleep(2000);
                JSONObject json_obj = new JSONObject(response);
                JSONArray array = json_obj.getJSONArray("data");

                // get string from json object
                for (int i = 0; i < array.length(); i++) {
                    topic = array.getJSONObject(i).getString("topic");
                    detail = array.getJSONObject(i).getString("detail");
                    price = array.getJSONObject(i).getString("price");
                    category = array.getJSONObject(i).getString("category");
                    phone = array.getJSONObject(i).getString("phone");
                    poster_id = array.getJSONObject(i).getString("poster_id");
                    province = array.getJSONObject(i).getString("province");
                    datetime = array.getJSONObject(i).getString("datetime");
                    status = array.getJSONObject(i).getString("status");
                    ipaddress = array.getJSONObject(i).getString("ipaddress");
                    visited = array.getJSONObject(i).getString("visited");
                    path_file = array.getJSONObject(i).getString("path_file");
                    name = array.getJSONObject(i).getString("name");
                }

                for (int i = 0; i < 5; i++) {
                    String image = array.getJSONObject(0).getString("image_" + i + "");
                    if (!image.isEmpty()) {
                        String img_url = "http://trymycmh.16mb.com/sell_image/" + path_file + "/" + image;
                        img_arr.add(img_url);
                    } else {
                        break;
                    }
                }


            } catch (JSONException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            return response;
        }

        protected void onPostExecute(String result) {
            mProgressDialog.dismiss();

            txttopic.setText(topic);
            txtdetail.setText("รายละเอียด \n\n" + detail);
            txtprice.setText("ราคา : " + price + "บาท");
            txtphone.setText("เบอร์โทร : " + phone);
            txtprovince.setText("จังหวัด : " + province);
            txtdatetime.setText("วัน/เวลา : " + datetime);
            txtipaddress.setText("IPADDRESS : " + ipaddress);
            txtvisited.setText("เข้าชม : " + visited + "ครั้ง");
            txtname.setText("ผู้โพส : " + name);

            switch (category) {
                case "1":
                    txtcategory.setText("หมวดหมู่ : มอไซค์คลาสสิค");
                    break;
                case "2":
                    txtcategory.setText("หมวดหมู่ : มอไซค์วิบาก");
                    break;
                case "3":
                    txtcategory.setText("หมวดหมู่ : มอไซค์บิ๊กไบค์");
                    break;
                case "4":
                    txtcategory.setText("หมวดหมู่ : มอไซค์ทั่วไป");
                    break;
            }

            if (status.equals("1")) {
                txtstatus.setText("สถานะ : ขาย");
            } else {
                txtstatus.setText("สถานะ : ขายแล้ว");
            }

            ViewPager viewPager = (ViewPager) findViewById(R.id.img_ViewPager);
            viewPager.setAdapter(new ImgPagerAdapter(getSupportFragmentManager()));

        } // onPostExecute
    } // end show_motorcycle_task


    private class ImgPagerAdapter extends FragmentPagerAdapter {

        public ImgPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return img_arr.size();
        }

        @Override
        public Fragment getItem(int position) {
            return Fragment_ImgSlidePager.newInstance(position, img_arr.get(position));
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }


}
