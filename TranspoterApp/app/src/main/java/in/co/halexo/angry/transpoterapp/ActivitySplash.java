package in.co.halexo.angry.transpoterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class ActivitySplash extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        if(!sharedPreferences.getBoolean("LanguageSelected",false)) {
            startActivity(new Intent(getApplicationContext(),ActivityLanguage.class));
        }else {

            startActivity(new Intent(getApplicationContext(), ActivityMobile.class));
        }
        finish();
    }
}
