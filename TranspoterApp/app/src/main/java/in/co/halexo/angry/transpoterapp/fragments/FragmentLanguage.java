package in.co.halexo.angry.transpoterapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.co.halexo.angry.transpoterapp.R;
import in.co.halexo.angry.transpoterapp.sBase;

import static android.content.Context.MODE_PRIVATE;

public class FragmentLanguage extends Fragment implements View.OnClickListener {
    private TextView englishText,hindiText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sBase.setLanguage(getContext());
        View view=inflater.inflate(R.layout.activity_language,container,false);
        setUp(view);
        return view;
    }

    private void setUp(View view) {
        englishText=view.findViewById(R.id.english);
        englishText.setOnClickListener(this);
        hindiText=view.findViewById(R.id.hindi);
        hindiText.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Context context=getContext();
        switch (v.getId()) {
            case R.id.english: {

                if(context!=null) {
//                    hindiText.setBackground(ContextCompat.getDrawable(context, R.drawable.background_color_for_views));
//                    hindiText.setTextColor(getResources().getColor(R.color.text_color_for_views));
//                    englishText.setBackground(ContextCompat.getDrawable(context, R.drawable.background_blue_round));
//                    englishText.setTextColor(getResources().getColor(R.color.colorWhite));
                    //Toast.makeText(getContext(), ""+v.isFocused(), Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("LanguageSelected", true);
                    editor.putString("Language", "en");
                    editor.apply();
                }
                break;
            }
            case R.id.hindi: {
                //setLocale("hi");
                if(context!=null) {
//                    englishText.setBackground(ContextCompat.getDrawable(context, R.drawable.background_color_for_views));
//                    englishText.setTextColor(getResources().getColor(R.color.text_color_for_views));
//                    hindiText.setBackground(ContextCompat.getDrawable(context, R.drawable.background_blue_round));
//                    hindiText.setTextColor(getResources().getColor(R.color.colorWhite));
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("LanguageSelected", true);
                    editor.putString("Language", "hi");
                    editor.apply();
                }
                break;
            }
        }
        sBase.setLanguage(getContext());
        //getActivity().getSupportFragmentManager().popBackStack();
        //getFragmentManager().popBackStack();

        //getActivity().recreate();
    }
}
