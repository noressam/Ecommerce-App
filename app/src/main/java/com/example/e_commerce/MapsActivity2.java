package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity2 extends AppCompatActivity {

    private GoogleMap map;
    EditText addressText;
    LocationManager locManager;
    myLocationListener locListener;
    Button getLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        addressText = (EditText) findViewById(R.id.editText1);
        getLocation = (Button) findViewById(R.id.btn1);
        locListener = new myLocationListener(getApplicationContext());
        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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

        if (locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    double lat=location.getLatitude();
                    double longl=location.getLongitude();
                    LatLng latLng=new LatLng(lat,longl);
                    Geocoder geocoder=new Geocoder(getApplicationContext());
                    try {
                        List<Address>addressList= geocoder.getFromLocation(lat,longl,1);
                        String str=addressList.get(0).getLocality()+",";
                        str+=addressList.get(0).getCountryName();
                        map.addMarker(new MarkerOptions().position(latLng).title(str));
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.0f));
                        addressText.setText(str);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
        else
        if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {

                @Override
                public void onLocationChanged(@NonNull Location location) {
                    double lat=location.getLatitude();
                    double longl=location.getLongitude();
                    LatLng latLng=new LatLng(lat,longl);
                    Geocoder geocoder=new Geocoder(getApplicationContext());
                    try {
                        List<Address>addressList= geocoder.getFromLocation(lat,longl,1);
                        String str=addressList.get(0).getLocality()+",";
                        str+=addressList.get(0).getCountryName();
                        map.addMarker(new MarkerOptions().position(latLng).title(str));
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.0f));
                        addressText.setText(str);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        //SupportMapFragment supportMapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);


    }
/*
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map=googleMap;
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960, 31.235711600),8));
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                Geocoder coder=new Geocoder(getApplicationContext());
                List<Address>addressList;
                Location loc=null;
                try{
                    loc=locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

//                    Toast.makeText(getApplicationContext(),loc.getLatitude()+","+loc.getLongitude(),Toast.LENGTH_LONG).show();
                }catch (SecurityException x){
                    Toast.makeText(getApplicationContext(),"no access",Toast.LENGTH_LONG).show();
                }
                if(loc !=null){
                    LatLng myposition=new LatLng(loc.getLatitude(),loc.getLongitude());
                    try{
                        addressList=coder.getFromLocation(myposition.latitude,myposition.longitude,1);
                        if(!addressList.isEmpty()){
                            String address="";
                            for(int i=0;i<addressList.get(0).getMaxAddressLineIndex();i++)
                                address+=addressList.get(0).getAddressLine(i)+",";
                            map.addMarker(new MarkerOptions().position(myposition)
                            .title("My Location").snippet(address)).setDraggable(true);
                            addressText.setText(address);

                        }
                    }catch (IOException e){
                        map.addMarker(new MarkerOptions().position(myposition).title("My Location"));
                    }
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myposition,15));
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please Wait",Toast.LENGTH_LONG).show();
                }
            }
        });
        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {
                Geocoder coder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                try {
                    addressList = coder.getFromLocation(marker.getPosition().latitude,
                            marker.getPosition().longitude, 1);
                    if (!addressList.isEmpty()) {
                        String address = "";
                        for (int i = 0; i < addressList.get(0).getMaxAddressLineIndex(); i++)
                            address += addressList.get(0).getAddressLine(i) + ",";
                        addressText.setText(address);
                    } else {
                        Toast.makeText(getApplicationContext(), "No address for this Location", Toast.LENGTH_LONG).show();
                        addressText.getText().clear();
                    }

                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Cant get address for this location ", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {

            }

            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {

            }
        });
    }

 */
}