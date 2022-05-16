package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SubmissionActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    Button Address;
    Button Deliver;
    static String addr;
    Date date;
    Order order;
    E_CommerceDB e_commerceDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Address=(Button) findViewById(R.id.address);
        Deliver=(Button) findViewById(R.id.delev);
        order=new Order();
        e_commerceDB=new E_CommerceDB(getApplicationContext());
        Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(SubmissionActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(SubmissionActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

                }
                Toast.makeText(getApplicationContext(), "Address: "+addr, Toast.LENGTH_LONG).show();
            }
        });
        Deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = Calendar.getInstance().getTime();
                order.Address=addr;
              //  Toast.makeText(getApplicationContext(),Customer.username,Toast.LENGTH_LONG).show();
                order.Customer_id=e_commerceDB.getCustomerID(Customer.username);
                order.OrderDate=date;
                for(int i=0;i<Cart.list.size();i++){
                    order.product_name= Cart.list.get(i).name;
                    e_commerceDB.FinishOrder(order, Cart.list.get(i).quantity);

                }
                for(int i=0;i<Cart.list.size()-1;i++) {

                    e_commerceDB.AddOrder(order);

                }
               Toast.makeText(getApplicationContext(),"Order Will deliver to You Soon ",Toast.LENGTH_LONG).show();

            }
        });

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(SubmissionActivity.this, Locale.getDefault());
                        List<android.location.Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        addr = addressList.get(0).getAddressLine(0);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}