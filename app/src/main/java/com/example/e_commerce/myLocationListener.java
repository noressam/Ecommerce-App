package com.example.e_commerce;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class myLocationListener implements LocationListener {

    private Context activityContext;
    public myLocationListener(Context cont) {
        activityContext=cont;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        double lat=location.getLatitude();
        double longl=location.getLongitude();
        LatLng latLng=new LatLng(lat,longl);
        Geocoder geocoder=new Geocoder(activityContext.getApplicationContext());
        try {
            List<Address>addressList= geocoder.getFromLocation(lat,longl,1);
            String str=addressList.get(0).getLocality()+",";
            str+=addressList.get(0).getCountryName();
            //mMap.addMarker(new MarkerOptions().position(latLng).title(str));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Toast.makeText(activityContext,location.getLatitude()+","+location.getLongitude(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Toast.makeText(activityContext,"GPS Enabled",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(activityContext,"GPS Disabled",Toast.LENGTH_LONG).show();
    }
}
