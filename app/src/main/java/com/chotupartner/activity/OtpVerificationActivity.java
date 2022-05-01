package com.chotupartner.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.chotupartner.R;
import com.chotupartner.modelClass.delivery.DeliveryPartnerResponse;
import com.chotupartner.modelClass.forOutLet.OutletLoginModel;
import com.chotupartner.modelClass.login.LoginWithOtpResponse;
import com.chotupartner.retrofit.RestClient;
import com.chotupartner.utils.ChotuBoyPrefs;
import com.chotupartner.utils.Constants;
import com.chotupartner.utils.Utils;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerificationActivity extends AppCompatActivity{

    TextView changeNumberTv, mobileNumberTv, resend;
    EditText verifyPinEdtText;
    Button btnLogin;
    public String mobileNo;
    Context context;
    public String otpNumber;
    public String customerUserId, firstName, lastName, userEmail, userAddress;
    com.chotupartner.modelClass.forOutLet.OutletLoginModel OutletLoginModel;
    private String userType;


    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= 23&& context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    final int REQUEST = 112;
    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Login Using OTP");
        }

     //   allowPermission();
        initView();
    }




    private void initView() {

        changeNumberTv = findViewById(R.id.changeNumberTv);
        mobileNumberTv = findViewById(R.id.mobileNumberTv);
        verifyPinEdtText = findViewById(R.id.verifyPinEdtTxt);
        btnLogin = findViewById(R.id.vrfyPin_btnLogin);
        resend = findViewById(R.id.resend);

        mobileNumberTv.setText(ChotuBoyPrefs.getString(getApplicationContext(), Constants.MOBILE));
        mobileNo = ChotuBoyPrefs.getString(getApplicationContext(), Constants.MOBILE);
        userType = ChotuBoyPrefs.getString(getApplicationContext(),Constants.USERTYPE);

        changeNumberTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginThroughMobileNumberActivity.class);
                startActivity(intent);
                finish();
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestBody phoneno = RequestBody.create(MediaType.parse("text/plain"), mobileNo);
                RequestBody userType = RequestBody.create(MediaType.parse("text/plain"),  ChotuBoyPrefs.getString(getApplicationContext(), Constants.USERTYPE));

                Utils.showProgressDialog(OtpVerificationActivity.this);

                RestClient.logInWithOtpNewUser(phoneno,userType, new Callback<LoginWithOtpResponse>() {
                    @Override
                    public void onResponse(Call<LoginWithOtpResponse> call, Response<LoginWithOtpResponse> response) {
                        Utils.dismissProgressDialog();
                        if (response != null && response.code() == 200 && response.body() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("true")) {
                                Toast.makeText(getApplicationContext(), "OTP resend your mobile number successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginWithOtpResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed! resent OTP ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (ChotuBoyPrefs.getString(getApplicationContext(), Constants.USERTYPE).equalsIgnoreCase("2")){
                  verifyPinOperationForDelivery();

              }else{
                  verifyPinOperationForOutLet();

              }
            }
        });

    }

    public void verifyPinOperationForDelivery() {
        boolean check = true;
        otpNumber = verifyPinEdtText.getText().toString().trim();
        if (otpNumber.isEmpty() || verifyPinEdtText.length()<4) {
            verifyPinEdtText.setError("enter a valid otp");
            check = false;
        } else {
            verifyPinEdtText.setError(null);
            check = true;
        }

        if (check) {

            RequestBody phoneno = RequestBody.create(MediaType.parse("text/plain"), mobileNo);
            RequestBody otp = RequestBody.create(MediaType.parse("text/plain"), otpNumber);
            RequestBody userType = RequestBody.create(MediaType.parse("text/plain"),  ChotuBoyPrefs.getString(getApplicationContext(), Constants.USERTYPE));

            Utils.showProgressDialog(this);
            RestClient.verifyOtpDelivery(phoneno, otp, userType,new Callback<DeliveryPartnerResponse>() {
                @Override
                public void onResponse(Call<DeliveryPartnerResponse> call, Response<DeliveryPartnerResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response != null && response.code() == 200 && response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            ChotuBoyPrefs.putString(OtpVerificationActivity.this,"deliveryData",new Gson().toJson(response.body().getDeliveryUser()));
                            ChotuBoyPrefs.putString(OtpVerificationActivity.this,"outletData","");
                            ChotuBoyPrefs.putString(OtpVerificationActivity.this,"delivery_id",response.body().getDeliveryUser().getDeliveryId());
                            ChotuBoyPrefs.putBoolean(getApplicationContext(), Constants.LoginCheck, true);
                            Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                            ChotuBoyPrefs.putString(getApplicationContext(),Constants.USERTYPE,ChotuBoyPrefs.getString(getApplicationContext(), Constants.USERTYPE));

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter valid otp", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DeliveryPartnerResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Response failed", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    public void verifyPinOperationForOutLet() {
        boolean check = true;
        otpNumber = verifyPinEdtText.getText().toString().trim();
        if (otpNumber.isEmpty() || verifyPinEdtText.length()<4) {
            verifyPinEdtText.setError("enter a valid otp");
            check = false;
        } else {
            verifyPinEdtText.setError(null);
            check = true;
        }

        if (check) {

            RequestBody phoneno = RequestBody.create(MediaType.parse("text/plain"), mobileNo);
            RequestBody otp = RequestBody.create(MediaType.parse("text/plain"), otpNumber);
            RequestBody userType = RequestBody.create(MediaType.parse("text/plain"),  ChotuBoyPrefs.getString(getApplicationContext(), Constants.USERTYPE));

            Utils.showProgressDialog(this);
            RestClient.verifyOtpOutLet(phoneno, otp, userType,new Callback<OutletLoginModel>() {
                @Override
                public void onResponse(Call<OutletLoginModel> call, Response<OutletLoginModel> response) {
                    Utils.dismissProgressDialog();
                    if (response != null && response.code() == 200 && response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            ChotuBoyPrefs.putString(OtpVerificationActivity.this,"outletData",new Gson().toJson(response.body().getUser()));
                            ChotuBoyPrefs.putString(OtpVerificationActivity.this,"deliveryData","");
                            ChotuBoyPrefs.putString(OtpVerificationActivity.this,"outlet_id",response.body().getUser().getOutletId());
                            ChotuBoyPrefs.putBoolean(getApplicationContext(), Constants.LoginCheck, true);
                            Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                            ChotuBoyPrefs.putString(getApplicationContext(),Constants.USERTYPE,ChotuBoyPrefs.getString(getApplicationContext(), Constants.USERTYPE));

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter valid otp", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OutletLoginModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Response failed", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish(); // close this activity and return to preview activity (if there is any)
        return super.onOptionsItemSelected(item);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        onBackPressed();
        return super.onContextItemSelected(item);
    }

}
