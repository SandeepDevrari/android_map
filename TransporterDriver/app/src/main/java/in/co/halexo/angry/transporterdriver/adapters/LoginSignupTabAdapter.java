package in.co.halexo.angry.transporterdriver.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.co.halexo.angry.transporterdriver.fragments.FragmentLogin;
import in.co.halexo.angry.transporterdriver.fragments.FragmentSignUp;

public class LoginSignupTabAdapter extends FragmentPagerAdapter {
    private String[] tabs;
    public LoginSignupTabAdapter(FragmentManager fm,String[] tabs) {
        super(fm);
        this.tabs=tabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position){
            case 0:{
                fragment=new FragmentLogin();
                break;
            }
            case 1:{
                fragment=new FragmentSignUp();
                break;
            }
            default:{
                fragment=null;
            }
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
