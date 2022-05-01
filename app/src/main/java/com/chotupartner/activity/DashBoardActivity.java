package com.chotupartner.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.chotupartner.R;
import com.chotupartner.retrofit.RestClient;
import com.chotupartner.utils.ChotuBoyPrefs;
import com.chotupartner.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardActivity extends AppCompatActivity{
    AppBarConfiguration appBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
         appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_dashboard,R.id.navigation_product, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(navView, navController);



    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(ChotuBoyPrefs.getString(this, "outletData"))) {
            updaateFCMOutlet();
        } else {
            updaateFCMDelivery();
        }
    }


    private void updaateFCMOutlet() {
        RequestBody outletID = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(this, "outlet_id"));
        RequestBody fcm = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(this, "fcm"));

        RestClient.updateFCMOutlet(outletID,fcm, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utils.dismissProgressDialog();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }


    private void updaateFCMDelivery() {
        RequestBody delivryId = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(this, "delivery_id"));
        RequestBody fcm = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(this, "fcm"));

        RestClient.updateFCMDelivery(delivryId,fcm, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utils.dismissProgressDialog();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
}
