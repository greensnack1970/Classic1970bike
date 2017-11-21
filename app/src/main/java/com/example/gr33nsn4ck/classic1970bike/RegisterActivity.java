package com.example.gr33nsn4ck.classic1970bike;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class RegisterActivity extends AppCompatActivity {

    private static String sex = "";
    private static String province = "";
    private EditText txtname;
    private EditText txtusername;
    private EditText txtpassword;
    private EditText txtconpassword;
    private EditText txtemail;
    private EditText txtphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtname = (EditText) findViewById(R.id.txtname);
        txtusername = (EditText) findViewById(R.id.txtusername);
        txtpassword = (EditText) findViewById(R.id.edt_password);
        txtconpassword = (EditText) findViewById(R.id.txtconpassword);
        txtemail = (EditText) findViewById(R.id.edt_email);
        txtphone = (EditText) findViewById(R.id.txtphone);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup_sex);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                if (radioButton.getText().toString().equals("Male")) {
                    sex = "1";
                } else if (radioButton.getText().toString().equals("Female")) {
                    sex = "2";
                }
            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.Province);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                province = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void btn_register_onclick(View view) {
        register_function();
    }


    private void register_function() {
        Ion.with(RegisterActivity.this)
                .load("http://trymycmh.16mb.com/index.php/api/register")
                .setBodyParameter("name", txtname.getText().toString())
                .setBodyParameter("username", txtusername.getText().toString())
                .setBodyParameter("password", txtpassword.getText().toString())
                .setBodyParameter("cpassword", txtconpassword.getText().toString())
                .setBodyParameter("email", txtemail.getText().toString())
                .setBodyParameter("phone", txtphone.getText().toString())
                .setBodyParameter("sex", sex)
                .setBodyParameter("province", province)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String response) {
                        if (response.equals("success")) {
                            Toast.makeText(RegisterActivity.this, "สมัครสมาชิกสำเร็จ", Toast.LENGTH_LONG).show();
                            RegisterActivity.this.finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "[!] สมัครสมาชิกไม่สำเร็จ \n กรุณากรอกข้อมูลให้ถูกต้อง", Toast.LENGTH_LONG).show();
                        }
                        RegisterActivity.this.finish();
                    }
                });

    } // register_function


    // เมื่อผู้ใช้กด back
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
