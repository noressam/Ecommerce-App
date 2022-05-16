package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {


    E_CommerceDB e_commerceDB;
    String y,m,d;
    boolean isExist;
    EditText nametxt;
    EditText usetxt;
    EditText passtxt;
    EditText gendertxt;
    CalendarView calendarView;
    EditText jobtxt;
    Button regbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        e_commerceDB=new E_CommerceDB(getApplicationContext());
        initiate();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                 y=String.valueOf(year);
                 m=String.valueOf(month+1);
                 d=String.valueOf(dayOfMonth);
            }
        });

        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date=y+"/"+m+"/"+d;
                Customer newcustomer=new Customer();
                newcustomer.name=nametxt.getText().toString().trim();
                newcustomer.username=usetxt.getText().toString().trim();
                isExist=e_commerceDB.CheckUserName(usetxt.getText().toString().trim());
                newcustomer.birthdate=date;
                newcustomer.password=passtxt.getText().toString().trim();
                newcustomer.gender=gendertxt.getText().toString().trim();
                newcustomer.job=jobtxt.getText().toString().trim();
                if(isExist==true) {
                    e_commerceDB.AddCustomer(newcustomer);
                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                    clear();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Registered Failed Try to enter User Name Again", Toast.LENGTH_LONG).show();

                }

            }
        });





        
    }

    private void clear() {
        nametxt.setText("");
        usetxt.setText("");
        passtxt.setText("");
        gendertxt.setText("");
        jobtxt.setText("");
    }

    public void initiate(){

         nametxt=(EditText) findViewById(R.id.editTextTextPersonName);
         usetxt=(EditText) findViewById(R.id.editTextTextPersonName2);
         passtxt=(EditText) findViewById(R.id.editTextTextPersonName3);
         gendertxt=(EditText) findViewById(R.id.editTextTextPersonName4);
         calendarView=(CalendarView) findViewById(R.id.calendarView2);

         jobtxt=(EditText) findViewById(R.id.editTextTextPersonName6);
         regbutton=(Button) findViewById(R.id.button);
    }
}