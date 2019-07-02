package in.co.halexo.angry.transpoterapp;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivityRoute extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap map;
    private LatLng pickupLatLong,dropLatLong;
    //private LatLng mLatLng;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        setUpUI();
        setUpMap(savedInstanceState);
    }

    private void setUpUI() {
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            pickupLatLong = new LatLng(bundle.getDouble(sBase.LATITUDE, 0), bundle.getDouble(sBase.LONGITUDE, 0));
            ((TextView) findViewById(R.id.route_SendToEditText)).setText(bundle.getString(sBase.ADDRESS, "Deafult"));
        }
        findViewById(R.id.route_SendToEditText).setOnClickListener(this);
        findViewById(R.id.route_WhereToEditText).setOnClickListener(this);
    }
    private void setUpMap(Bundle savedInstanceState) {
        MapView mapView = findViewById(R.id.routeMapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(ActivityRoute.this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(sBase.checkLocationPermission(this)){
            googleMap.setMyLocationEnabled(true);
        }
        map=googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        addNewMarker(pickupLatLong,getResources().getString(R.string.from_to),
                ((TextView) findViewById(R.id.route_SendToEditText)).getText().toString(),
                BitmapDescriptorFactory.defaultMarker());
//        Marker marker1 = map.addMarker(new MarkerOptions()
//                .position(pickupLatLong)
//                .title(getResources().getString(R.string.from_to))
//                .snippet(((TextView) findViewById(R.id.route_SendToEditText)).getText().toString()));
//        marker1.setFlat(false);
//        marker1.showInfoWindow();
        CameraPosition cameraPosition=new CameraPosition.Builder().target(pickupLatLong).zoom(14).build();
        //myGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.route_SendToEditText:{
                sDialog sDialogObj=new sDialog(this, pickupLatLong, new sDialog.PassDataBackToActivity() {
                    @Override
                    public void passData(LatLng latLng, String address) {
                        if(!address.equals(getResources().getString(R.string.drag_the_marker))) {
                        ((TextView)v).setText(address);
                        //if(sourceMarker==null) {
                            map.clear();
                            pickupLatLong=latLng;
                            addNewMarker(latLng,getResources().getString(R.string.from_to),
                                    address,BitmapDescriptorFactory.defaultMarker());
//                            sourceMarker = map.addMarker(new MarkerOptions()
//                                    .position(latLng)
//                                    .title(getResources().getString(R.string.from_to))
//                                    .snippet(address));
//                            sourceMarker.setFlat(false);
//                            sourceMarker.showInfoWindow();
                            if(dropLatLong!=null){
                                addNewMarker(dropLatLong,getResources().getString(R.string.to_where),
                                        address,BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//                                destinationMarker = map.addMarker(new MarkerOptions()
//                                        .position(destinationMarker.getPosition())
//                                        .title(getResources().getString(R.string.to_where))
//                                        .snippet(address)
//                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//                                destinationMarker.setFlat(false);
//                                destinationMarker.showInfoWindow();
                            }
//                        }else{
//
//                            //sourceMarker.setPosition(latLng);
//                        }
                        setRoute();
                        }else{
                            Toast.makeText(ActivityRoute.this, getResources().getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                sDialogObj.show();
                break;
            }
            case R.id.route_WhereToEditText:{
                LatLng tempLat;
                if(dropLatLong!=null){
                    tempLat=dropLatLong;
                }else{
                    tempLat=pickupLatLong;
                }
                sDialog sDialogObj=new sDialog(this, tempLat, new sDialog.PassDataBackToActivity() {
                    @Override
                    public void passData(LatLng latLng, String address) {
                        if(!address.equals(getResources().getString(R.string.drag_the_marker))) {
                            ((TextView) v).setText(address);
                            //if(destinationMarker==null) {
                            dropLatLong=latLng;
                            map.clear();
                            addNewMarker(dropLatLong,getResources().getString(R.string.to_where),
                                    address,BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            if(pickupLatLong!=null){
                                addNewMarker(pickupLatLong,getResources().getString(R.string.from_to),
                                        address,BitmapDescriptorFactory.defaultMarker());
                            }
                            //}else{
                                //destinationMarker.setPosition(latLng);
                            //}
//                            float[] resultDistance=new float[5];
//                            if(pickupLatLong!=null & dropLatLong!=null) {
//                                //LatLng latLng1 = sourceMarker.getPosition(), latLng2 = destinationMarker.getPosition();
//                                Location.distanceBetween(pickupLatLong.latitude,
//                                        pickupLatLong.longitude,
//                                        dropLatLong.latitude,
//                                        dropLatLong.longitude, resultDistance);
                                setRoute();
//                                for (Float f : resultDistance) {
//                                    //if(f!=null)
//                                    Log.w("%%%%%%%%%%%%%%%%%%%", "" + f);
//                                }
//                            }
                        }else{
                            Toast.makeText(ActivityRoute.this, getResources().getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                sDialogObj.show();
                break;
            }
        }
    }

    private void addNewMarker(LatLng latLng, String status, String address, BitmapDescriptor bitmapDescriptor){
        Marker marker = map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(status)
                .snippet(address)
                .icon(bitmapDescriptor));
        marker.setFlat(false);
        marker.showInfoWindow();
    }
    private void setRoute(){
        if (pickupLatLong!=null && dropLatLong!=null) {
            if(sBase.checkLocationPermission(this)){
                map.setMyLocationEnabled(false);
            }
            map.getUiSettings().setMyLocationButtonEnabled(false);
            //LatLng origin = sourceMarker.getPosition();
            //LatLng dest = destinationMarker.getPosition();

            // Getting URL to the Google Directions API
            String url = getDirectionsUrl(pickupLatLong, dropLatLong);

            DownloadTask downloadTask = new DownloadTask();

            // Start downloading json data from Google Directions API
            downloadTask.execute(url);
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class DownloadTask extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String,String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String,String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String,String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            for (int i = 0; i < result.size(); i++) {
                points=new ArrayList<>();
                lineOptions = new PolylineOptions();

                List<HashMap<String,String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(getResources().getColor(R.color.colorAccent));
                lineOptions.geodesic(true);

            }
            map.addPolyline(lineOptions);

            float[] resultDistance=new float[1];
            if(pickupLatLong!=null & dropLatLong!=null) {
                //LatLng latLng1 = sourceMarker.getPosition(), latLng2 = destinationMarker.getPosition();
                Location.distanceBetween(pickupLatLong.latitude,
                        pickupLatLong.longitude,
                        dropLatLong.latitude,
                        dropLatLong.longitude, resultDistance);
//                float finalDistance=0;
//                for (float f : resultDistance) {
//                    if(f<)
//                    Log.w("%%%%%%%%%%%%%%%%%%%", "" + f);
//                }
                CameraPosition cameraPosition;
                if(resultDistance[0]<=100){
                    cameraPosition=new CameraPosition.Builder().target(dropLatLong).zoom(16).build();
                }else if(resultDistance[0]>100 & resultDistance[0]<=500){
                    cameraPosition=new CameraPosition.Builder().target(dropLatLong).zoom(15).build();
                }else if(resultDistance[0]>500 & resultDistance[0]<=1000){
                    cameraPosition=new CameraPosition.Builder().target(dropLatLong).zoom(14).build();
                }else if(resultDistance[0]>1000 & resultDistance[0]<=5000){
                    cameraPosition=new CameraPosition.Builder().target(dropLatLong).zoom(12).build();
                }
                else{
                    cameraPosition=new CameraPosition.Builder().target(dropLatLong).zoom(10).build();
                }

                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
//            Float distance=lineOptions.getWidth();
//            Log.d("#################",""+distance);
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        //String urlN = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            assert iStream != null;
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
