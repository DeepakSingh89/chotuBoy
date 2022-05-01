package com.chotupartner.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chotupartner.R;
import com.chotupartner.modelClass.login.LoginWithOtpResponse;
import com.chotupartner.retrofit.RestClient;
import com.chotupartner.utils.ChotuBoyPrefs;
import com.chotupartner.utils.Constants;
import com.chotupartner.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginThroughMobileNumberActivity extends AppCompatActivity {
    private static final String TAG = "FCM";
    public EditText edtTxtMobNo;
    public Button btnSendOtp;
    private String mobileNo;
    ImageView imageViewbackGroundImage;
    public RadioGroup radioGroup_User;
    public RadioButton rdBtnDelivery_Boy, rdBtnOutLet;
    private String userType="3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_through_mobile_number);

        edtTxtMobNo = findViewById(R.id.edtTxtMobNo);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        rdBtnDelivery_Boy = findViewById(R.id.RdBtnDeliveryBoy);
        rdBtnOutLet = findViewById(R.id.RdBtnOutLet);
        imageViewbackGroundImage = findViewById(R.id.backGroundImage);
        radioGroup_User = findViewById(R.id.userTypeRdGp);


        selectUserType();

        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validationLoginUser();
            }
        });

    }

    public void selectUserType() {
        radioGroup_User.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton genderrg = group.findViewById(checkedId);
                int selectedId = radioGroup_User.getCheckedRadioButtonId();
                if (null != genderrg) {
                    userType = genderrg.getText().toString();
                    if (userType.equalsIgnoreCase("Delivery Partner")) {
                        imageViewbackGroundImage.setBackgroundResource(R.drawable.ic_delivery);
                        userType = "2";
                    } else {
                        imageViewbackGroundImage.setBackgroundResource(R.drawable.ic_outlet);
                        userType = "3";
                    }


                    // Toast.makeText(getApplicationContext(), genderrg.getText().toString(), Toast.LENGTH_SHORT).show();
                    ChotuBoyPrefs.putString(getApplicationContext(), Constants.USERTYPE, userType);

                }
            }
        });

    }

    public boolean inputValidaton() {
        boolean check = true;
        mobileNo = edtTxtMobNo.getText().toString().trim();

        if (mobileNo.isEmpty() || edtTxtMobNo.length() < 10) {

            Snackbar.make(findViewById(android.R.id.content), "Enter a valid phone number ", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.WHITE)
                    .show();
            check = false;
        } else {
            edtTxtMobNo.setError(null);
            check = true;
        }

        return check;
    }

    private void validationLoginUser() {
        if (inputValidaton()) {

            RequestBody phoneno = RequestBody.create(MediaType.parse("text/plain"), mobileNo);
            RequestBody loginAs = RequestBody.create(MediaType.parse("text/plain"), userType);
            Utils.showProgressDialog(this);

            RestClient.logInWithOtpNewUser(phoneno, loginAs, new Callback<LoginWithOtpResponse>() {
                @Override
                public void onResponse(Call<LoginWithOtpResponse> call, Response<LoginWithOtpResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response != null && response.code() == 200 && response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            Intent intent = new Intent(getApplicationContext(), OtpVerificationActivity.class);
                            ChotuBoyPrefs.putString(getApplicationContext(), Constants.MOBILE, mobileNo);
                            ChotuBoyPrefs.putString(getApplicationContext(), Constants.USERTYPE, userType);

                            startActivity(intent);
                            finish();
                            Toast.makeText(LoginThroughMobileNumberActivity.this, "Pls verify Otp!", Toast.LENGTH_SHORT).show();
                        } else if (response.body().getStatus().equalsIgnoreCase("false")) {
                            Snackbar.make(findViewById(android.R.id.content), "We are unable to find your account at Chotu Grocery, Please contact to support", Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.WHITE)
                                    .show();
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "Something went wrong!!", Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.WHITE)
                                    .show();
                        }

                    }

                }

                @Override
                public void onFailure(Call<LoginWithOtpResponse> call, Throwable t) {
                    Toast.makeText(LoginThroughMobileNumberActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }




}
