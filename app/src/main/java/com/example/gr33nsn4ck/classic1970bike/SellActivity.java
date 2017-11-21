package com.example.gr33nsn4ck.classic1970bike;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;


public class SellActivity extends AppCompatActivity {

    EditText edt_topic;
    EditText edt_detail;
    EditText edt_price;

    ImageView img_1;
    ImageView img_2;
    ImageView img_3;
    ImageView img_4;
    ImageView img_5;
    ImageView img_6;

    String str_img1;
    String str_img2;
    String str_img3;
    String str_img4;
    String str_img5;
    String str_img6;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        if (checkUserSignin()) {

        edt_topic = (EditText) findViewById(R.id.edt_topic);
        edt_detail = (EditText) findViewById(R.id.edt_detail);
        edt_price = (EditText) findViewById(R.id.edt_price);

        img_1 = (ImageView) findViewById(R.id.imageView1);
        img_2 = (ImageView) findViewById(R.id.imageView2);
        img_3 = (ImageView) findViewById(R.id.imageView3);
        img_4 = (ImageView) findViewById(R.id.imageView4);
        img_5 = (ImageView) findViewById(R.id.imageView5);
        img_6 = (ImageView) findViewById(R.id.imageView6);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.sell_category_radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_classic:
                        category = "1";
                        break;
                    case R.id.radio_enduro:
                        category = "2";
                        break;
                    case R.id.radio_bigbike:
                        category = "3";
                        break;
                    case R.id.radio_other:
                        category = "4";
                        break;
                }
            }
        });



    } else {
        SellActivity.this.finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            set2ImageView(data, requestCode);
        }
    }

    private void set2ImageView(Intent data,int requestCode) {
        try {
            InputStream imageStream = getContentResolver().openInputStream(data.getData());
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

            switch (requestCode) {
                case 1:
                    img_1.setImageBitmap(selectedImage);
                    str_img1 = getRealPathFromURI(data.getData());
                    Log.e("......", str_img1);
                    break;
                case 2:
                    img_2.setImageBitmap(selectedImage);
                    str_img2 = getRealPathFromURI(data.getData());
                    break;
                case 3:
                    img_3.setImageBitmap(selectedImage);
                    str_img3 = getRealPathFromURI(data.getData());
                    break;
                case 4:
                    img_4.setImageBitmap(selectedImage);
                    str_img4 = getRealPathFromURI(data.getData());
                    break;
                case 5:
                    img_5.setImageBitmap(selectedImage);
                    str_img5 = getRealPathFromURI(data.getData());
                    break;
                case 6:
                    img_6.setImageBitmap(selectedImage);
                    str_img6 = getRealPathFromURI(data.getData());
                    break;
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    class SellSubmitTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog = new ProgressDialog(SellActivity.this);
        String topic;
        String detail;
        String id;
        String phone;
        String province;
        String user_token;
        String category;
        String price;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("....Please wait....");
            progressDialog.setCancelable(false);
            progressDialog.show();

            SharedPreferences spf = getSharedPreferences("account", Context.MODE_PRIVATE);
            topic = edt_topic.getText().toString();
            detail = edt_detail.getText().toString();
            price = edt_price.getText().toString();
            category = SellActivity.this.category;
            id = spf.getString("id", null);
            phone = spf.getString("phone", null);
            province = spf.getString("province", null);
            user_token = spf.getString("user_token", null);
        }

        @Override
        protected String doInBackground(String... url) {
            String response = null;

            try {
                // userfiles(0-5) , topic , detail , price , category , poster_id , phone , province , user_token

                MultipartEntity entity = new MultipartEntity();
                if (str_img1 != null) {
                    entity.addPart("userfile0", new FileBody(new File(str_img1)));
                }
                if (str_img2 != null) {
                    entity.addPart("userfile1", new FileBody(new File(str_img2)));
                }
                if (str_img3 != null) {
                    entity.addPart("userfile2", new FileBody(new File(str_img3)));
                }
                if (str_img4 != null) {
                    entity.addPart("userfile3", new FileBody(new File(str_img4)));
                }
                if (str_img5 != null) {
                    entity.addPart("userfile4", new FileBody(new File(str_img5)));
                }
                if (str_img6 != null) {
                    entity.addPart("userfile5", new FileBody(new File(str_img6)));
                }


                entity.addPart("topic", new StringBody(topic));
                entity.addPart("detail", new StringBody(detail));
                entity.addPart("price", new StringBody(price));
                entity.addPart("category", new StringBody(String.valueOf(category)));
                entity.addPart("phone", new StringBody(phone));
                entity.addPart("province", new StringBody(province, Charset.forName("UTF-8")));
                entity.addPart("user_token", new StringBody(user_token));
                entity.addPart("id", new StringBody(id));

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost("http://trymycmh.16mb.com/index.php/api/motorcycle");
                postRequest.setEntity(entity);
                HttpResponse httpResponse = httpClient.execute(postRequest);
                response = EntityUtils.toString(httpResponse.getEntity());

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("catch", "........");
            }
            return response;
        }


        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            progressDialog.dismiss();

            if (!response.equals("success")) {
                Toast.makeText(SellActivity.this, "ลงประกาศล้มเหลว\nกรุณากรอกข้อมูลให้ครบถ้วน\nและใช้ไฟล์ภาพขนาดไม่เกิน 1500x1500", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(SellActivity.this, "[!] ลงประกาศเรียบร้อย", Toast.LENGTH_LONG).show();
                SellActivity.this.finish();
            }

        }

    } // end SellSubmitTask


    // +++ Buttons +++ //

    public void btn_select_image1(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    public void btn_select_image2(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    public void btn_select_image3(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 3);
    }

    public void btn_select_image4(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 4);
    }

    public void btn_select_image5(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 5);
    }

    public void btn_select_image6(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 6);
    }


    public void btn_submit(View view) {
        new SellSubmitTask().execute();
    }


    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private boolean checkUserSignin() {
        File account_preference_file = new File("/data/data/" + getPackageName() + "/shared_prefs/account.xml");
        if (account_preference_file.exists()) {
            SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
            if (sharedPreferences.contains("id")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}

