package com.nsystem.mapsexperiment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        LocationListener {

    private LatLng mLocation;
    private LatLng mMyLocation;
    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;

    private LocationManager mLocManager;

    private double mLat;
    private double mLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request permission for ACCESS_FINE_LOCATION
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(this,"Allow ACCESS to enable showing location in this project", Toast.LENGTH_LONG).show();
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

            return;
        }

        // Initialize GPS
        mLocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        // Initialize and Sync Maps
        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

    }

    @Override
    public void onLocationChanged(Location location) {
        mLat = location.getLatitude();
        mLng = location.getLongitude();

        // Add Marker of Current Location to Maps after sending it
        // Sample Loc (Lat : 0.571708, Lng : 101.424622)
        mMap.clear();
        mMyLocation = new LatLng(mLat, mLng);
        mMap.addMarker(new MarkerOptions().position(mMyLocation).title("My Position").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person)));

        //Move the camera instantly to mMyLocation with a zoom of "x".
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mMyLocation, 13));

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}

