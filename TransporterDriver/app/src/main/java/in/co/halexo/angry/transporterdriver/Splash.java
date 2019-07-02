package in.co.halexo.angry.transporterdriver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        if(!sharedPreferences.getBoolean("LanguageSelected",false)) {
            startActivity(new Intent(getApplicationContext(),ActivityLanguage.class));
        }else {

            startActivity(new Intent(getApplicationContext(), ActivityLoginSignup.class));
        }
        finish();
    }
}
