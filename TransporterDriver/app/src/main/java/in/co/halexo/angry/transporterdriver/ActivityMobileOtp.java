package in.co.halexo.angry.transporterdriver;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

public class ActivityMobileOtp extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_otp);
        setUp();
    }

    private void setUp() {
        //FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        findViewById(R.id.otpNext).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.otpNext: {
                if (!sBase.checkLocationPermission(this)) {
                    sBase.requestForPermission(ActivityMobileOtp.this);
                } else {
                    if(sBase.isGPSEnabled(getApplicationContext())){
                        moveToHome();
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
                break;
            }
        }
    }

    private void moveToHome() {
//        Intent intent=new Intent(ActivityMobileOtp.this,HomeActivity.class);
//        startActivity(intent);
//        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==sBase.sPermissionFineLocation){
            if(grantResults[0]== PermissionChecker.PERMISSION_GRANTED){
                moveToHome();
            }
            else{
                showRequestMsg();
            }
        }
    }

    private void showRequestMsg() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.AlertDialogCustom);
        @SuppressLint("InflateParams") View view= LayoutInflater.from(this).inflate(R.layout.custom_alert_box,null);
        builder.setView(view);
        final AlertDialog alertDialog=builder.create();
        view.findViewById(R.id.customAlertAllowButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sBase.requestForPermission(ActivityMobileOtp.this);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
        alertDialog.setCancelable(false);
    }
//    private void requestForPermission(){
//        ActivityCompat.requestPermissions(ActivityMobileOtp.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},sBase.sPermissionFineLocation);
//    }
//    //Manifest.permission.ACCESS_COARSE_LOCATION,
}
