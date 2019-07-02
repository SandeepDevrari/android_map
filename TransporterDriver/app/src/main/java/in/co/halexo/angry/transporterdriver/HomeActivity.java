package in.co.halexo.angry.transporterdriver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback {

    private MapView mapView;
    private static final String TRACKING_LOCATION_KEY = "tracking_location";
    private boolean mTrackingLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private Marker myLocationMarker;
    private Location myLocation;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState != null) {
            mTrackingLocation = savedInstanceState.getBoolean(
                    TRACKING_LOCATION_KEY);
        }

        setUpUI();

        setUpMap(savedInstanceState);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setUpMap(Bundle savedInstanceState) {

        mapView = findViewById(R.id.home_mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        mLocationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (mTrackingLocation) {
                    myLocation=locationResult.getLastLocation();
                    if(myLocationMarker!=null) {
                        myLocationMarker.setPosition(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));
                        callLocationAddessThread();
                        CameraPosition cameraPosition=new CameraPosition.Builder().target(new LatLng(myLocation.getLatitude(), myLocation.getLongitude())).zoom(14).build();
                        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                }
            }
        };

        findMyLocation();
        startTrackingLocation();
    }

    private void setUpUI() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }
    private void callLocationAddessThread(){
        new AsyncLocationAddress(HomeActivity.this, new OnAddressFetched() {
            @Override
            public void onAddressCall() {
                //myLocationMarker.setSnippet(getResources().getString(R.string.loading));
            }

            @Override
            public void onAddressFetched(String s) {
                myLocationMarker.setSnippet(s);
            }

        }).execute(myLocation);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(sBase.checkLocationPermission(this)){
            googleMap.setMyLocationEnabled(true);
        }
        map=googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        LatLng myLatLng= new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
        myLocationMarker= googleMap.addMarker(new MarkerOptions().position(myLatLng).title("Me"));
        myLocationMarker.setFlat(false);
        callLocationAddessThread();
        CameraPosition cameraPosition=new CameraPosition.Builder().target(myLatLng).zoom(14).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    protected void onResume() {
        if (mTrackingLocation) {
            startTrackingLocation();
        }
        mapView.onResume();
        super.onResume();

    }

    @Override
    public void onPause() {
        mapView.onPause();
        if (mTrackingLocation) {
            stopTrackingLocation();
            mTrackingLocation = true;
        }
        super.onPause();

    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();

    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();

    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncLocationAddress extends AsyncTask<Location,Void,String> {
        private Context context;
        private OnAddressFetched onAddressFetched;

        AsyncLocationAddress(Context context,OnAddressFetched onAddressFetched){
            this.context=context;
            this.onAddressFetched=onAddressFetched;
        }
        @Override
        protected String doInBackground(Location... locations) {
            Geocoder geocoder;
            List<Address> addresses=null;
            geocoder = new Geocoder(context, Locale.getDefault());
            try {
                Location location=locations[0];
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            } catch (IOException e) {
                Log.e(AsyncLocationAddress.class.getSimpleName(),e.getMessage());
            }
            String address;
            if(addresses!=null && addresses.size()>0) {
                address = addresses.get(0).getAddressLine(0);
            }else{
                address="No Address found!";
            }
            return address;
        }

        @Override
        protected void onPostExecute(String s) {
            onAddressFetched.onAddressFetched(s);
            super.onPostExecute(s);

        }

        @Override
        protected void onPreExecute() {
            onAddressFetched.onAddressCall();
            super.onPreExecute();
        }
    }

    interface OnAddressFetched{
        void onAddressCall();
        void  onAddressFetched(String s);
    }

    private void startTrackingLocation() {
        if (!sBase.checkLocationPermission(this)){
            sBase.requestForPermission(HomeActivity.this);
        } else {
            if(sBase.isGPSEnabled(getApplicationContext())){
                mTrackingLocation = true;
                mFusedLocationClient.requestLocationUpdates(getLocationRequest(),mLocationCallback,null /* Looper */);
            }
            else{
                AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.AlertDialogCustom);
                builder.setTitle(getResources().getString(R.string.turn_on_device_location));
                builder.setMessage(getResources().getString(R.string.device_location_msg));
                builder.setPositiveButton(getResources().getString(R.string.Okay), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsOptionsIntent);
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }

        }
    }


    private void stopTrackingLocation() {
        if (mTrackingLocation) {
            mTrackingLocation = false;
        }
    }


    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    private void findMyLocation() {

        if(sBase.checkLocationPermission(this)) {
            if(sBase.isGPSEnabled(getApplicationContext())){
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    //Log.d("################"," long"+location.getLongitude()+"\t lati"+location.getLatitude());
                                    myLocation=location;
                                    mapView.getMapAsync(HomeActivity.this);
                                }
                            }
                        });
            }
            else{
                AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.AlertDialogCustom);
                builder.setTitle(getResources().getString(R.string.turn_on_device_location));
                builder.setMessage(getResources().getString(R.string.device_location_msg));
                builder.setPositiveButton(getResources().getString(R.string.Okay), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsOptionsIntent);
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        }
    }
}
