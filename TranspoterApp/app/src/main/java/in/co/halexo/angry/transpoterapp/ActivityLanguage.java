package in.co.halexo.angry.transpoterapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ActivityLanguage extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        setUp();
    }
    private void setUp() {
        findViewById(R.id.english).setOnClickListener(this);
        findViewById(R.id.hindi).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.english: {
                    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("LanguageSelected", true);
                    editor.putString("Language", "en");
                    editor.apply();
                break;
            }
            case R.id.hindi: {
                    SharedPreferences sharedPreferences =getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("LanguageSelected", true);
                    editor.putString("Language", "hi");
                    editor.apply();
                break;
            }
        }
        sBase.setLanguage(getApplicationContext());
        startActivity(new Intent(getApplicationContext(),ActivityMobile.class));
    }

}
