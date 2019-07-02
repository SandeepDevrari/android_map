package in.co.halexo.angry.transpoterapp;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import in.co.halexo.angry.transpoterapp.fragments.FragmentLanguage;
import in.co.halexo.angry.transpoterapp.fragments.FragmentMobileNumber;

public class ActivityStart extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sBase.setLanguage(ActivityStart.this);
        setContentView(R.layout.activity_start);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        if(!sharedPreferences.getBoolean("LanguageSelected",false)) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.startFrameLay, new FragmentLanguage());
            fragmentTransaction.addToBackStack("Language");
            fragmentTransaction.commit();
        }


       // setUp();
    }

    private void setUp() {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.startFrameLay,new FragmentMobileNumber());
        fragmentTransaction.addToBackStack("MobileNumber");
        fragmentTransaction.commit();


//        findViewById(R.id.english).setOnClickListener(this);
//        findViewById(R.id.hindi).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.english:{
//                break;
//            }
//            case R.id.hindi:{
//                break;
//            }
        }

    }
}
