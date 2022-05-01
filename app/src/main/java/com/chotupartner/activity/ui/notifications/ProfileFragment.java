package com.chotupartner.activity.ui.notifications;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chotupartner.R;
import com.chotupartner.activity.AboutUsActivity;
import com.chotupartner.activity.LoginThroughMobileNumberActivity;
import com.chotupartner.activity.PrivacyPolicyActivity;
import com.chotupartner.modelClass.forOutLet.User;
import com.chotupartner.utils.ChotuBoyPrefs;
import com.google.gson.Gson;


public class ProfileFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    TextView nameTv,phonetv,addressTv,cityTv;
    ImageView imageView,iconImg;
    LinearLayout logoutBtnLL;
    LinearLayout phoneLL,privacyLl,aboutLL;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        iconImg = root.findViewById(R.id.iconImg);
        nameTv = root.findViewById(R.id.outletName);
        phoneLL = root.findViewById(R.id.phoneLL);
        phonetv = root.findViewById(R.id.phoneTv);
        addressTv = root.findViewById(R.id.addressTv);
        imageView = root.findViewById(R.id.imageView);
        logoutBtnLL = root.findViewById(R.id.logoutBtnLL);
        privacyLl = root.findViewById(R.id.privacyLl);
        aboutLL = root.findViewById(R.id.aboutLL);
        updateDetails();



        logoutBtnLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChotuBoyPrefs.clear(getActivity());
                Intent intent = new Intent(getActivity(), LoginThroughMobileNumberActivity.class);
                startActivity(intent);
                getActivity().finish();


            }
        });

        aboutLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_about_us = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent_about_us);


            }
        });

        privacyLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_privacy_policy = new Intent(getActivity(), PrivacyPolicyActivity.class);
                startActivity(intent_privacy_policy);


            }
        });
        return root;
    }

    private void updateDetails() {
        User  user = null;
        if (!TextUtils.isEmpty(ChotuBoyPrefs.getString(getActivity(), "outletData"))) {
            user = new Gson().fromJson(ChotuBoyPrefs.getString(getActivity(), "outletData"),User.class);
            nameTv.setText(user.getOutletName());
            phonetv.setText(user.getPhone());
            phoneLL.setVisibility(View.VISIBLE);
            addressTv.setText(user.getAddress());
            iconImg.setBackgroundResource(R.drawable.ic_privacy);
            //cityTv.setText(user.getAssignedCity());
            if (!TextUtils.isEmpty(user.getImage())){
                Glide.with(getActivity())
                        .load(user.getImage())
                        .addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        }).diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true)
                        .error(R.drawable.place_holder_img)

                        .fitCenter() // scale to fit entire image within ImageView
                        .into(imageView);
            }

        }else{
            user = new Gson().fromJson(ChotuBoyPrefs.getString(getActivity(), "deliveryData"),User.class);
            nameTv.setText(user.getDelivery_name());
            phoneLL.setVisibility(View.GONE);
            addressTv.setText(user.getAddress());
            iconImg.setBackgroundResource(R.drawable.ic_scooty_white);

            //  cityTv.setText(user.getCity());
            if (!TextUtils.isEmpty(user.getImage())){
                Glide.with(getActivity())
                        .load(user.getImage())
                        .addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        }).diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true)
                        .error(R.drawable.ic_scooty_white)

                        .fitCenter() // scale to fit entire image within ImageView
                        .into(imageView);
            }

        }


        }
    }

