package in.co.halexo.angry.transporterdriver.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.Objects;

import in.co.halexo.angry.transporterdriver.HomeActivity;
import in.co.halexo.angry.transporterdriver.R;

public class FragmentLogin extends Fragment implements View.OnClickListener {

    private RadioButton radioButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login,container,false);
        setUpUI(view);
        return view;
    }

    private void setUpUI(View view) {
        view.findViewById(R.id.login_Login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_Login:{
                Objects.requireNonNull(getContext()).startActivity(new Intent(getContext(), HomeActivity.class));
                break;
            }
        }
    }
}
