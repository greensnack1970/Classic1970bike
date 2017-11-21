package com.example.gr33nsn4ck.classic1970bike;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    private EditText txtemail;
    private EditText txtpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);

        txtemail = (EditText) findViewById(R.id.edt_email);
        txtpassword = (EditText) findViewById(R.id.edt_password);

    }

    public void btn_signin_clicked(View view) {
        new LoginFunction().execute();
    }

    public void btn_signup_clicked(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    protected class LoginFunction extends AsyncTask<String, Void, String> {

        private String email;
        private String password;
        private ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage(". . . Please Wait . . .");
            progressDialog.setCancelable(false);
            progressDialog.show();

            email = txtemail.getText().toString();
            password = txtpassword.getText().toString();
        }

        @Override
        protected String doInBackground(String... a) {

            Ion.with(LoginActivity.this)
                    .load("http://trymycmh.16mb.com/index.php/api/auth")
                    .setBodyParameter("email", email)
                    .setBodyParameter("password", password)
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String response) {

                            if (response.equals("fail")) {
                                Toast.makeText(getApplicationContext(), "Login Fail", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    JSONObject json_obj = new JSONObject(response);
                                    String id = json_obj.optString("id");
                                    String name = json_obj.optString("name");
                                    String email = json_obj.optString("email");
                                    String phone = json_obj.optString("phone");
                                    String province = json_obj.optString("province");
                                    String user_token = json_obj.optString("user_token");

                                    SharedPreferences spf = getSharedPreferences("account", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = spf.edit();
                                    editor.putString("id", id);
                                    editor.putString("name", name);
                                    editor.putString("email", email);
                                    editor.putString("phone", phone);
                                    editor.putString("province", province);
                                    editor.putString("user_token", user_token);
                                    editor.commit();

		                            Thread.sleep(3000);
                                    Toast.makeText(getApplicationContext(), "Hi " + name, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    LoginActivity.this.finish();
                                } catch (JSONException | InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    });
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


}
