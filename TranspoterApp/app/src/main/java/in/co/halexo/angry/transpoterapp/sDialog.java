package in.co.halexo.angry.transpoterapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class sDialog extends AppCompatDialog implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener, View.OnClickListener {
    private MapView mapView;
    private Marker myLocationMarker;
    private LatLng mLatLng;
    private Context context;
    private TextView mapText;
    private PassDataBackToActivity passDataBackToActivity;


    public sDialog(Context context, LatLng latLng,PassDataBackToActivity passDataBackToActivity) {
        super(context, R.style.ThemeForDialog);
        this.context=context;
        mLatLng=latLng;
        this.passDataBackToActivity=passDataBackToActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map_select);
        //startTimer();
        setUpMap(savedInstanceState);
        mapText=findViewById(R.id.mapTxt);
        findViewById(R.id.mapSelect).setOnClickListener(this);

    }

    private void setUpMap(Bundle savedInstanceState) {
        mapView = findViewById(R.id.mapSelect_Map);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
            mapView.onResume();
            mapView.getMapAsync(sDialog.this);
        }
    }

    private void startTimer() {
        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
//                    zLogger logger = new zLogger(context,e.getMessage(),"");
//                    logger.upload();
                } finally {
//                    cInterface.customFunc();
//                    zCustomDialog.this.dismiss();
                }
            }
        };
        timer.start();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        //LatLng myLatLng= new LatLng(myLocation.getLatitude(),myLocation.getLongitude());

        myLocationMarker= googleMap.addMarker(new MarkerOptions().position(mLatLng).title("current"));
        myLocationMarker.setFlat(false);
        myLocationMarker.setDraggable(true);
        CameraPosition cameraPosition=new CameraPosition.Builder().target(mLatLng).zoom(15).build();
        //googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.setOnMarkerDragListener(this);
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        //draggind started
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        //still dragging
    }

    @Override
    public void onMarkerDragEnd(final Marker marker) {
        new sDialog.AsyncLocationAddress(context, new OnAddressFetched() {

            @Override
            public void onAddressFetched(String s) {
                marker.setSnippet(s);
                mapText.setText(s);
            }
        }).execute(marker.getPosition());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mapSelect:{
                passDataBackToActivity.passData(myLocationMarker.getPosition(),mapText.getText().toString());
                sDialog.this.dismiss();
                break;
            }
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class AsyncLocationAddress extends AsyncTask<LatLng,Void,String> {
        private Context context;
        private OnAddressFetched onAddressFetched;

        AsyncLocationAddress(Context context,OnAddressFetched onAddressFetched){
            this.context=context;
            this.onAddressFetched=onAddressFetched;
        }
        @Override
        protected String doInBackground(LatLng... locations) {
            Geocoder geocoder;
            List<Address> addresses=null;
            geocoder = new Geocoder(context, Locale.getDefault());
            try {
                LatLng location=locations[0];
                addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
            } catch (IOException e) {
                Log.e(sDialog.class.getSimpleName(),e.getMessage());
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

    }
    interface OnAddressFetched{
        //void onAddressCall();
        void  onAddressFetched(String s);
    }
    interface PassDataBackToActivity{
        void passData(LatLng latLng,String address);
    }
}
