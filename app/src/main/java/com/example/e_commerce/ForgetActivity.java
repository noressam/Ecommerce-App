package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetActivity extends AppCompatActivity {

    EditText username;
    TextView password;
    Button retrieve;
    E_CommerceDB e_commerceDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
         username=(EditText) findViewById(R.id.usernameText);
         password=(TextView) findViewById(R.id.passwordText);
         retrieve=(Button) findViewById(R.id.retrieve);
         e_commerceDB=new E_CommerceDB(getApplicationContext());

        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString().trim();
                String retrievedPassword=e_commerceDB.getPassword(name);
                if(!retrievedPassword.equals("")) {
                    password.setVisibility(v.VISIBLE);
                    password.setText("Your Password is : "+retrievedPassword);
                }
                else
                    Toast.makeText(getApplicationContext(),"You aren’t an Exist User",Toast.LENGTH_LONG).show();
            }
        });
    }
}