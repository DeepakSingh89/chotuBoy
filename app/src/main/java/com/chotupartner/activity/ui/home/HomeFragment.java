package com.chotupartner.activity.ui.home;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chotupartner.R;
import com.chotupartner.activity.DashBoardActivity;
import com.chotupartner.adapter.ConfirmedOrderAdapter;
import com.chotupartner.adapter.MyOrderAdapter;
import com.chotupartner.adapter.NewOrderDeliveryAdapter;
import com.chotupartner.adapter.OnGoingDeliveryAdapter;
import com.chotupartner.databinding.FragmentHomeBinding;
import com.chotupartner.modelClass.orderlist.OrderInfo;
import com.chotupartner.modelClass.orderlist.OrderListResponse;
import com.chotupartner.retrofit.RestClient;
import com.chotupartner.utils.ChotuBoyPrefs;
import com.chotupartner.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements MyOrderAdapter.ItemClickListener, ConfirmedOrderAdapter.ItemClickListener, NewOrderDeliveryAdapter.ItemClickListener, OnGoingDeliveryAdapter.ItemClickListener {


    private HomeViewModel homeViewModel;
    ArrayList<OrderInfo> orderInfosOn = new ArrayList<>();
    ArrayList<OrderInfo> orderInfosNew = new ArrayList<>();
    ArrayList<OrderInfo> orderInfosOND = new ArrayList<>();
    ArrayList<OrderInfo> orderInfosNewD = new ArrayList<>();
    FragmentHomeBinding mBinding;

    DashBoardActivity dashBoardActivity;
    private AlertDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false);
        View view = mBinding.getRoot();
        //here data must be an instance of the class MarsDataProvider\

        mBinding.cView.setVisibility(View.VISIBLE);
        mBinding.oView.setVisibility(View.GONE);

        mBinding.confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.cView.setVisibility(View.VISIBLE);
                mBinding.oView.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(ChotuBoyPrefs.getString(getActivity(), "outletData"))) {
                    getConfirmedOrders();
                } else {
                    getDeliveryOngoingOrders();
                }

            }
        });


        mBinding.pendingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.cView.setVisibility(View.GONE);
                mBinding.oView.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(ChotuBoyPrefs.getString(getActivity(), "outletData"))) {
                    getPendingOrders();
                } else {
                    getDeliveryNewOrders();
                }


            }
        });


//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                String token = instanceIdResult.getToken();
//                ChotuBoyPrefs.putString(getActivity(), "fcm",token);
//                Log.d("FCM",""+token);
//            }
//        });

        return view;


    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dashBoardActivity = (DashBoardActivity) getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        IntentFilter filter = new IntentFilter();
        filter.addAction("custom.notification.navigation");
        dashBoardActivity.registerReceiver(broadcastReceiver, filter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        dashBoardActivity.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!TextUtils.isEmpty(ChotuBoyPrefs.getString(getActivity(), "outletData"))) {
            mBinding.pendingTv.setText("New Orders");
            mBinding.confirmTv.setText("Confirmed Orders");
            getConfirmedOrders();

        } else {
            mBinding.pendingTv.setText("New Orders");
            mBinding.confirmTv.setText("Delivery Orders");
            getDeliveryOngoingOrders();

        }

    }

    private void getConfirmedOrders() {

        RequestBody customeridRequest = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(getActivity(), "outlet_id"));

        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getConfirmedOrders(customeridRequest, new Callback<OrderListResponse>() {
                @Override
                public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body().getStatus()) {
                        if (response.body() != null) {
                            OrderListResponse cartInfoList = response.body();

                            if (cartInfoList.getOrderInfo() != null && cartInfoList.getOrderInfo().size() > 0) {
                                orderInfosOn.clear();
                                mBinding.recyclerCartViewConfirm.setVisibility(View.VISIBLE);
                                mBinding.cView.setVisibility(View.VISIBLE);
                                mBinding.oView.setVisibility(View.GONE);

                                mBinding.recyclerCartViewPending.setVisibility(View.GONE);
                                mBinding.emptyCart.setVisibility(View.GONE);

                                orderInfosOn.addAll(cartInfoList.getOrderInfo());
                                ConfirmedOrderAdapter horizontalAdapter = new ConfirmedOrderAdapter(getActivity(), orderInfosOn, HomeFragment.this);
                                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                mBinding.recyclerCartViewConfirm.setLayoutManager(horizontalLayoutManagaer);
                                mBinding.recyclerCartViewConfirm.setAdapter(horizontalAdapter);

                            } else {

                                mBinding.emptyCart.setVisibility(View.VISIBLE);
                                mBinding.recyclerCartViewPending.setVisibility(View.GONE);

                                mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                            }

                        }
                    } else {
                        mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                        mBinding.emptyCart.setVisibility(View.VISIBLE);
                        mBinding.recyclerCartViewPending.setVisibility(View.GONE);


                    }
                }

                @Override
                public void onFailure(Call<OrderListResponse> call, Throwable t) {
                    mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                    mBinding.recyclerCartViewPending.setVisibility(View.GONE);
                    mBinding.emptyCart.setVisibility(View.VISIBLE);
                    Log.d("tag", "onFailure: ");
                    Utils.dismissProgressDialog();
                    // Toast.makeText(CartListActivity.this, "" + getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(getActivity(), "Internet Connection Failed!!", Toast.LENGTH_SHORT).show();
        }
    }


    private void getPendingOrders() {

        RequestBody customeridRequest = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(getActivity(), "outlet_id"));

        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getAllOrederForOutLet(customeridRequest, new Callback<OrderListResponse>() {
                @Override
                public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body().getStatus()) {
                        if (response.body() != null) {
                            OrderListResponse cartInfoList = response.body();

                            if (cartInfoList.getOrderInfo() != null && cartInfoList.getOrderInfo().size() > 0) {
                                orderInfosNew.clear();
                                mBinding.cView.setVisibility(View.GONE);
                                mBinding.oView.setVisibility(View.VISIBLE);

                                mBinding.recyclerCartViewPending.setVisibility(View.VISIBLE);
                                orderInfosNew.addAll(cartInfoList.getOrderInfo());
                                mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                                mBinding.emptyCart.setVisibility(View.GONE);

                                MyOrderAdapter horizontalAdapter = new MyOrderAdapter(getActivity(), orderInfosNew, HomeFragment.this);
                                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                mBinding.recyclerCartViewPending.setLayoutManager(horizontalLayoutManagaer);
                                mBinding.recyclerCartViewPending.setAdapter(horizontalAdapter);

                            } else {

                                mBinding.emptyCart.setVisibility(View.VISIBLE);
                                mBinding.recyclerCartViewPending.setVisibility(View.GONE);
                                mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                            }

                        }
                    } else {
                        mBinding.recyclerCartViewPending.setVisibility(View.GONE);
                        mBinding.emptyCart.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<OrderListResponse> call, Throwable t) {
                    mBinding.recyclerCartViewPending.setVisibility(View.GONE);
                    mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                    mBinding.emptyCart.setVisibility(View.VISIBLE);
                    Log.d("tag", "onFailure: ");
                    Utils.dismissProgressDialog();
                    // Toast.makeText(CartListActivity.this, "" + getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(getActivity(), "Internet Connection Failed!!", Toast.LENGTH_SHORT).show();
        }
    }


    private void getDeliveryNewOrders() {

        RequestBody customeridRequest = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(getActivity(), "delivery_id"));

        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getDeliveryNewOrders(customeridRequest, new Callback<OrderListResponse>() {
                @Override
                public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body().getStatus()) {
                        if (response.body() != null) {
                            OrderListResponse cartInfoList = response.body();

                            if (cartInfoList.getOrderInfo() != null && cartInfoList.getOrderInfo().size() > 0) {
                                orderInfosNewD.clear();
                                mBinding.recyclerCartViewPending.setVisibility(View.VISIBLE);
                                mBinding.cView.setVisibility(View.GONE);
                                mBinding.oView.setVisibility(View.VISIBLE);

                                orderInfosNewD.addAll(cartInfoList.getOrderInfo());
                                mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                                mBinding.emptyCart.setVisibility(View.GONE);

                                NewOrderDeliveryAdapter horizontalAdapter = new NewOrderDeliveryAdapter(getActivity(), orderInfosNewD, HomeFragment.this);
                                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                mBinding.recyclerCartViewPending.setLayoutManager(horizontalLayoutManagaer);
                                mBinding.recyclerCartViewPending.setAdapter(horizontalAdapter);

                            } else {

                                mBinding.emptyCart.setVisibility(View.VISIBLE);
                                mBinding.recyclerCartViewPending.setVisibility(View.GONE);
                                mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                            }

                        }
                    } else {
                        mBinding.recyclerCartViewPending.setVisibility(View.GONE);
                        mBinding.emptyCart.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<OrderListResponse> call, Throwable t) {
                    mBinding.recyclerCartViewPending.setVisibility(View.GONE);
                    mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                    mBinding.emptyCart.setVisibility(View.VISIBLE);
                    Log.d("tag", "onFailure: ");
                    Utils.dismissProgressDialog();
                    // Toast.makeText(CartListActivity.this, "" + getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(getActivity(), "Internet Connection Failed!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDeliveryOngoingOrders() {

        RequestBody customeridRequest = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(getActivity(), "delivery_id"));

        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getDeliveryOngoingOrders(customeridRequest, new Callback<OrderListResponse>() {
                @Override
                public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body().getStatus()) {
                        if (response.body() != null) {
                            OrderListResponse cartInfoList = response.body();

                            if (cartInfoList.getOrderInfo() != null && cartInfoList.getOrderInfo().size() > 0) {
                                orderInfosOND.clear();
                                mBinding.recyclerCartViewConfirm.setVisibility(View.VISIBLE);
                                mBinding.cView.setVisibility(View.VISIBLE);
                                mBinding.oView.setVisibility(View.GONE);

                                orderInfosOND.addAll(cartInfoList.getOrderInfo());
                                mBinding.recyclerCartViewConfirm.setVisibility(View.VISIBLE);
                                mBinding.emptyCart.setVisibility(View.GONE);

                                OnGoingDeliveryAdapter horizontalAdapter = new OnGoingDeliveryAdapter(getActivity(), orderInfosOND, HomeFragment.this);
                                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                mBinding.recyclerCartViewConfirm.setLayoutManager(horizontalLayoutManagaer);
                                mBinding.recyclerCartViewConfirm.setAdapter(horizontalAdapter);

                            } else {

                                mBinding.emptyCart.setVisibility(View.VISIBLE);
                                mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                            }

                        }
                    } else {
                        mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                        mBinding.emptyCart.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<OrderListResponse> call, Throwable t) {
                    mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                    mBinding.emptyCart.setVisibility(View.VISIBLE);
                    Log.d("tag", "onFailure: ");
                    Utils.dismissProgressDialog();
                    // Toast.makeText(CartListActivity.this, "" + getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(getActivity(), "Internet Connection Failed!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccept(int orderId) {
        RequestBody outletID = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(getActivity(), "outlet_id"));
        RequestBody orderID = RequestBody.create(MediaType.parse("text/plain"), "" + orderId);
        RequestBody statusBody = RequestBody.create(MediaType.parse("text/plain"), "processing");
        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.updateOrderStatus(outletID, orderID, statusBody, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        // orderInfos.clear();
                        if (response.body() != null) {
                            getPendingOrders();
                        }
                    } else {
                        mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                        mBinding.emptyCart.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                    mBinding.emptyCart.setVisibility(View.VISIBLE);
                    Log.d("tag", "onFailure: ");
                    Utils.dismissProgressDialog();
                    // Toast.makeText(CartListActivity.this, "" + getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(getActivity(), "Internet Connection Failed!!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onReject(int orderId) {
        RequestBody outletID = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(getActivity(), "outlet_id"));
        RequestBody orderID = RequestBody.create(MediaType.parse("text/plain"), "" + orderId);
        RequestBody statusBody = RequestBody.create(MediaType.parse("text/plain"), "denied");
        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.updateOrderStatus(outletID, orderID, statusBody, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        getPendingOrders();
                    } else {
                        mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                        mBinding.emptyCart.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                    mBinding.emptyCart.setVisibility(View.VISIBLE);
                    Log.d("tag", "onFailure: ");
                    Utils.dismissProgressDialog();
                    // Toast.makeText(CartListActivity.this, "" + getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(getActivity(), "Internet Connection Failed!!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onAcceptPickup(int orderId, String orderCustomerId) {
        pickupOrderDialog(orderId, orderCustomerId, "Outlet Order Delivery Code", "pickup");

    }

    @Override
    public void onDeliveryOrder(int orderId, String orderCustomerId) {
        pickupOrderDialog(orderId, orderCustomerId, "Customer Order Delivery Code", "delivery");

    }

    private void pickupOrderDialog(int orderId, String orderCustomerId, String title, String type) {

        AlertDialog.Builder builder = new AlertDialog.Builder(dashBoardActivity);
        View view = LayoutInflater.from(dashBoardActivity).inflate(R.layout.enter_otp_dialog, null);

        Button verifyOTp = view.findViewById(R.id.verifyOTp);
        TextView dialogTitle = view.findViewById(R.id.dialogTitle);
        TextView orderIdTV = view.findViewById(R.id.orderId);
        EditText verifyPinEdtText = view.findViewById(R.id.verifyPinEdtTxt);

        dialogTitle.setText("" + title);
        orderIdTV.setText("Order ID : " + orderCustomerId);
        ImageView crossBtn = view.findViewById(R.id.closeDialog);
        builder.setCancelable(false);
        builder.setView(view);

        verifyOTp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(verifyPinEdtText.getText().toString())) {
                    if (type.equalsIgnoreCase("pickup")) {
                        verifPickupyOTp(verifyPinEdtText.getText().toString(), orderId);
                    } else {
                        dialog.dismiss();
                        verifDeliveredOTp(verifyPinEdtText.getText().toString(), orderId);
                    }
                } else {
                    Toast.makeText(dashBoardActivity, "Please enter otp from outlet", Toast.LENGTH_SHORT).show();
                }


            }
        });


        crossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog = builder.create();
        dialog.show();
    }

    private void verifPickupyOTp(String otp, int orderId) {
        RequestBody otpBody = RequestBody.create(MediaType.parse("text/plain"), otp);
        RequestBody orderID = RequestBody.create(MediaType.parse("text/plain"), "" + orderId);
        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), "dispatched");
        RequestBody comment = RequestBody.create(MediaType.parse("text/plain"), "dispatched");

        Utils.showProgressDialog(dashBoardActivity);
        RestClient.update_order(orderID, status, otpBody, comment, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utils.dismissProgressDialog();
                if (response != null && response.code() == 200 && response.body() != null) {
                    if (response.body() != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.optString("status").equalsIgnoreCase("Success")) {
                                dialog.dismiss();
                                getDeliveryNewOrders();
                            } else {
                                Toast.makeText(dashBoardActivity, "Please enter valid otp", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(dashBoardActivity, "Please enter valid otp", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(dashBoardActivity, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(dashBoardActivity, "Response failed", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void verifDeliveredOTp(String otp, int orderId) {
        RequestBody otpBody = RequestBody.create(MediaType.parse("text/plain"), otp);
        RequestBody orderID = RequestBody.create(MediaType.parse("text/plain"), "" + orderId);
        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), "complete");
        RequestBody comment = RequestBody.create(MediaType.parse("text/plain"), "complete");

        Utils.showProgressDialog(dashBoardActivity);
        RestClient.update_order(orderID, status, otpBody, comment, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utils.dismissProgressDialog();
                if (response != null && response.code() == 200 && response.body() != null) {
                    if (response.body() != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.optString("status").equalsIgnoreCase("Success")) {
                                dialog.dismiss();
                                getDeliveryOngoingOrders();
                            } else {
                                Toast.makeText(dashBoardActivity, "Please enter valid otp", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(dashBoardActivity, "Please enter valid otp", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(dashBoardActivity, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(dashBoardActivity, "Response failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Toast.makeText(dashBoardActivity, "New Order received", Toast.LENGTH_SHORT).show();

          if (mBinding.cView.getVisibility()==View.VISIBLE){
              if (!TextUtils.isEmpty(ChotuBoyPrefs.getString(getActivity(), "outletData"))) {
                  getConfirmedOrders();
              } else {
                  getDeliveryOngoingOrders();
              }
          }else{
              if (!TextUtils.isEmpty(ChotuBoyPrefs.getString(getActivity(), "outletData"))) {
                  getPendingOrders();
              } else {
                  getDeliveryNewOrders();
              }
          }


        }
    };

}
