package in.co.halexo.angry.transpoterapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import java.util.Locale;

public class sBase {
    static final int sPermissionCamera = 101,
            sPermissionCamera2 = 102,
            sPermissionReadPhoneState = 103,
            sPermissionReadExternalStorage = 104,
            sPermissionWriteExternalStorage = 105,
            sPermissionFineLocation=106;
    static final String LATITUDE="latitude",LONGITUDE="longitude",ADDRESS="address";

    static boolean checkInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected();
    }

    public static void setLanguage(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        setLocale(sharedPreferences.getString("Language","en"),context);
    }
    private static void setLocale(String lang, Context context) {
        Locale myLocale = new Locale(lang);
        Resources res =context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
    public static boolean checkLocationPermission(Context context) {
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED ){
            return false;
            //&& ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
        }
        return true;
    }
    public static void requestForPermission(Activity activity){
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},sBase.sPermissionFineLocation);
    }
    //Manifest.permission.ACCESS_COARSE_LOCATION,

    public static boolean isGPSEnabled(Context context){
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE );
        if (manager != null) {
            return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        return false;
    }
}
