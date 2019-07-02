package in.co.halexo.angry.transporterdriver.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import in.co.halexo.angry.transporterdriver.R;
import in.co.halexo.angry.transporterdriver.sBase;

public class FragmentSignUp extends Fragment implements View.OnClickListener {

    private ConstraintLayout layout1,layout2;
    private Button signup;
    private File resultFile;
    private ImageView profileImage,aadharCardImage,drivingLicenceImage;
    private TextView text1,text2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_singup,container,false);
        setUpUI(view);
        return view;
    }

    private void setUpUI(View view) {
        layout1=view.findViewById(R.id.signup_constraintLay01);
        layout2=view.findViewById(R.id.signup_constraintLay02);
        signup=view.findViewById(R.id.signup_Signup);
        signup.setOnClickListener(this);
        view.findViewById(R.id.signup_constraintLay02_back).setOnClickListener(this);
        profileImage=view.findViewById(R.id.signup_profilePic);
        profileImage.setOnClickListener(this);
        aadharCardImage=view.findViewById(R.id.signup_aadharcard);aadharCardImage.setOnClickListener(this);
        drivingLicenceImage=view.findViewById(R.id.signup_drivingLicence);drivingLicenceImage.setOnClickListener(this);
        text1=view.findViewById(R.id.signup_constraintLay02_aadharText);
        text2=view.findViewById(R.id.signup_constraintLay02_driving_text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup_Signup:{
                if(layout1.getVisibility()==View.VISIBLE){
                    layout2.setVisibility(View.VISIBLE);
                    layout1.setVisibility(View.INVISIBLE);
                    signup.setText(R.string.register);
            }
                break;
            }
            case R.id.signup_constraintLay02_back:{
                if(layout1.getVisibility()==View.INVISIBLE){
                    layout2.setVisibility(View.INVISIBLE);
                    layout1.setVisibility(View.VISIBLE);
                    signup.setText(R.string.next);
                }
                break;
            }
            case R.id.signup_profilePic:{
                chooseOption(sBase.sProfileImage);
                break;
            }
            case R.id.signup_aadharcard:{
                chooseOption(sBase.sAadharCard);
                break;
            }
            case R.id.signup_drivingLicence:{
                chooseOption(sBase.sDrivingLicence);
                break;
            }
        }
    }

    private void chooseOption(int sCode) {
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file= new File(Environment.getExternalStorageDirectory()+File.separator+getString(R.string.app_name));
        if(!file.exists()){
            file.mkdir();
        }
        resultFile=new File(file,"Image"+new Date().getTime()+".jpeg");
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(resultFile));
        Intent galleryIntent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent chooserIntent=Intent.createChooser(cameraIntent,getString(R.string.select_image_picker));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Parcelable[]{galleryIntent});
        startActivityForResult(chooserIntent, sCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case sBase.sProfileImage:{
                if(setBitmapToView(profileImage,data)){
                }
                break;
            }
            case sBase.sAadharCard:{
                if(setBitmapToView(aadharCardImage,data)){
                    text1.setVisibility(View.INVISIBLE);
                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
                    aadharCardImage.setImageTintList(null);
                    else
                        aadharCardImage.clearColorFilter();
                }

                break;
            }
            case sBase.sDrivingLicence:{
                if(setBitmapToView(drivingLicenceImage,data)){
                    text2.setVisibility(View.INVISIBLE);
                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
                        drivingLicenceImage.setImageTintList(null);
                    else
                        drivingLicenceImage.clearColorFilter();
                }
                break;
            }
        }
    }
    private Bitmap getBitmap(Intent data){
        Bitmap bitmap;
        Uri selectedImageUri=null;
        if(data!=null){
            selectedImageUri=data.getData();
        }
            if(selectedImageUri==null){
                bitmap= BitmapFactory.decodeFile(resultFile.getAbsolutePath());
            }else{
                try {
                    bitmap=MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getContext()).getContentResolver(),selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                    bitmap=null;
                }
            }
        return bitmap;
    }
    private boolean setBitmapToView(ImageView imageView,Intent data){
        Bitmap bitmap=getBitmap(data);
        if(bitmap!=null) {
            imageView.setImageBitmap(bitmap);
            storeImage(bitmap);
            return true;
        }else{
            Toast.makeText(getContext(), getString(R.string.try_again), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void storeImage(Bitmap bitmap) {
        try {
            if(!resultFile.exists()) {
                FileOutputStream out = new FileOutputStream(resultFile, false);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                bitmap.recycle();
            }
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            Toast.makeText(getContext(), getString(R.string.unable_to_store_image), Toast.LENGTH_SHORT).show();
        }
    }
}
