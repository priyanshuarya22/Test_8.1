package com.example.test81;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test81.R;
import com.example.test81.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.test81.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    boolean TODO = false;
    //creating variables
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    EditText et1;
    Button b1;
    LocationManager locationManager;
    LatLng lg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //creating objects
        et1 = findViewById(R.id.et1);
        b1 = findViewById(R.id.b1);
        //setting listener for go button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting location text from edit text
                String loc = et1.getText().toString();
                //creating the object of Geocoder
                Geocoder gc = new Geocoder(MapsActivity.this);
                try {
                    //getting the list of addresses using Geocoder
                    List<Address> addresses = gc.getFromLocationName(loc, 10);
                    //selecting the top result
                    Address address = addresses.get(0);
                    //getting the latitude and longitude of that location
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    //updating the camera to that location and zooming to 15 percent
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                    //animating the camera to that location
                    mMap.animateCamera(cameraUpdate);
                    //creating object of Marker
                    MarkerOptions markerOptions = new MarkerOptions();
                    //positioning the marker on the searched location
                    markerOptions.position(latLng);
                    //adding title to the marker equals to the locality of the searched location
                    markerOptions.title(address.getLocality());
                    //adding the marker on the map
                    mMap.addMarker(markerOptions);
                } catch (Exception e) {
                    //displaying a toast in case of an error
                    Toast.makeText(MapsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //getting latitude and longitude of bareilly
        LatLng bareilly = new LatLng(28.3670, 79.4304);
        //adding marker on bareilly
        mMap.addMarker(new MarkerOptions().position(bareilly).title("Marker in Bareilly"));
        //moving camera to bareilly
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bareilly));
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, MapsActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //creating an object for menuInflater class
        MenuInflater menuInflater = new MenuInflater(MapsActivity.this);
        //inflating the menu
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //checking if the option selected is equal to normal
        if (item.getItemId() == R.id.m1) {
            //setting type to normal
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        //or terrain
        else if (item.getItemId() == R.id.m2) {
            //setting type to terrain
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
        //or satellite
        else if (item.getItemId() == R.id.m3) {
            //setting type to satellite
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        //or hybrid
        else if (item.getItemId() == R.id.m4) {
            //setting type to hybrid
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
        //or something else
        else {
            if (lg == null) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return TODO;
                }
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                lg = new LatLng(location.getLatitude(), location.getLongitude());
            }
            //updating the camera to that location and zooming to 15 percent
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(lg, 15);
            //animating the camera to that location
            mMap.animateCamera(cameraUpdate);
            //creating object of Marker
            MarkerOptions markerOptions = new MarkerOptions();
            //positioning the marker on the searched location
            markerOptions.position(lg);
            //adding title to the marker equals to the locality of the searched location
            markerOptions.title("Your Location");
            //adding the marker on the map
            mMap.addMarker(markerOptions);
            Toast.makeText(MapsActivity.this, "Located", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lg = new LatLng(location.getLatitude(), location.getLongitude());
    }
}