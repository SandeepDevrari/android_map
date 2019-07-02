package in.co.halexo.angry.transpoterapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import in.co.halexo.angry.transpoterapp.R;

public class FragmentMobileNumber extends Fragment implements View.OnClickListener {

    private EditText number;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //sBase.setLanguage(getContext());
        View view=inflater.inflate(R.layout.activity_language,container,false);
        setUp(view);
        return view;
    }

    private void setUp(View view) {
        //number=view.findViewById(in.co.halexo.angry.transpoterapp.R.id.mobileNumber_MobileNumber);
       // number.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Context context=getContext();
        switch (v.getId()) {}}
}
