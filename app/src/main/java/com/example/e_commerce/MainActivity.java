package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean remembered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText username=(EditText) findViewById(R.id.editText1);
        EditText password=(EditText) findViewById(R.id.editText2);
        TextView forgetme=(TextView) findViewById(R.id.textView3);
        Button login=(Button) findViewById(R.id.button1);
        Button signup=(Button) findViewById(R.id.button2);
        CheckBox checkBox=(CheckBox) findViewById(R.id.checkBox);
        E_CommerceDB e_commerceDB=new E_CommerceDB(getApplicationContext());
        Intent intent=new Intent(MainActivity.this,CategoriesActivity.class);



        if(Customer.rem==true){
            startActivity(intent);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String u1=username.getText().toString().trim();
                Customer.username=u1;
                String p1=password.getText().toString().trim();
               boolean logincheck= e_commerceDB.Login(u1,p1);
               if(logincheck== true) {
                   Toast.makeText(getApplicationContext(), "Login Succsse", Toast.LENGTH_LONG).show();
                       startActivity(intent);

               }
               else
                   Toast.makeText(getApplicationContext(),"Login Failed User Name or Password is Incorrect",Toast.LENGTH_LONG).show();

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked())
                    Customer.rem=true;
                else
                    Customer.rem=false;
            }
        });

        forgetme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this,ForgetActivity.class);
                startActivity(intent1);
            }
        });


    }
}