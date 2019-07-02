package in.co.halexo.angry.transporterdriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ActivityMobile extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sBase.setLanguage(getApplicationContext());
        setContentView(R.layout.activity_mobile_number);
        setUp();
    }
    private void setUp() {
        findViewById(R.id.mobileNumber_MobileNumber).setOnClickListener(this);
        findViewById(R.id.mobileNumberNext).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mobileNumberNext:{
                startActivity(new Intent(getApplicationContext(),ActivityMobileOtp.class));
                break;
            }
        }
    }

}
