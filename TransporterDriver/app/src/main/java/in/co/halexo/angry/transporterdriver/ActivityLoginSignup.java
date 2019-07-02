package in.co.halexo.angry.transporterdriver;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import in.co.halexo.angry.transporterdriver.adapters.LoginSignupTabAdapter;

public class ActivityLoginSignup  extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        setUpUI();
    }

    private void setUpUI() {
        TabLayout tabLayout=findViewById(R.id.login_signup_tab);
        ViewPager viewPager=findViewById(R.id.login_signup_pager);
        String[] tabs=new String[]{
                getString(R.string.login),
                getString(R.string.register)
        };
        viewPager.setAdapter(new LoginSignupTabAdapter(getSupportFragmentManager(),tabs));
        tabLayout.setupWithViewPager(viewPager);
    }
}
