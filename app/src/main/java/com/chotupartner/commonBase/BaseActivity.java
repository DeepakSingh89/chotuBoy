package com.chotupartner.commonBase;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.chotupartner.R;
import com.google.gson.Gson;

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
    }

    /**
     * This method used to add/replace fragment on desire container with bundle and back stack status.
     *
     * @param fragmentName
     * @param bundle
     * @param container
     * @param addToBackStack
     * @param isReplace
     */
    public void addReplaceFragment(String fragmentName, Bundle bundle, int container, boolean addToBackStack, boolean isReplace) {

        try {
            Fragment fragment = Fragment.instantiate(this, fragmentName, bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            if (isReplace) {
                transaction.replace(container, fragment, fragmentName);
            } else {
                transaction.add(container, fragment, fragmentName);
            }
            transaction.addToBackStack(addToBackStack ? fragment.getClass().getName() : null);
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method used to add/replace fragment on desire container with bundle and back stack status.
     *
     * @param fragmentName
     * @param bundle
     * @param container
     * @param addToBackStack
     * @param isReplace
     */
    public void addReplaceFragment(String fragmentName, Bundle bundle, int container, boolean addToBackStack, boolean isReplace, boolean isAnimate) {

        try {
            Fragment fragment = Fragment.instantiate(this, fragmentName, bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (isAnimate) {
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            }
            if (isReplace) {
                transaction.replace(container, fragment, fragmentName);
            } else {
                transaction.add(container, fragment, fragmentName);
            }
            transaction.addToBackStack(addToBackStack ? fragment.getClass().getName() : null);
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


    public void enableTouch(boolean enabled) {

        if (enabled) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }


    public String jsonToString(Object jsonObject) {
        Gson gson = new Gson();
        return gson.toJson(jsonObject);
    }

    public Object stringToJson(String data, Object object) {
        Gson gson = new Gson();
        return gson.fromJson(data, object.getClass());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (getCurrentFragment() != null) {
            getCurrentFragment().onActivityResult(requestCode, resultCode, data);
        }
    }

    public Fragment getCurrentFragment() {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.container);
        return currentFragment;
    }





    public boolean checkInternetConection() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
