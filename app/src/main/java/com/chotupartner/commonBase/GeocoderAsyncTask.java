package com.chotupartner.commonBase;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocoderAsyncTask extends AsyncTask<Double, String, String> {
    private Context context;
    private ProgressDialog progressDialog;

    public GeocoderAsyncTask(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(progressDialog!=null)
        {
            progressDialog.show();
        }
    }

    @Override
    protected String doInBackground(Double... doubles) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(doubles[0], doubles[1], 1);

            if (addresses != null && addresses.size() > 0) {
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String zip = addresses.get(0).getPostalCode();
                String country = addresses.get(0).getCountryName();
                return city+","+state;
            } else {
                return ",";
            }


        } catch (IOException e) {
            return ",";
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
