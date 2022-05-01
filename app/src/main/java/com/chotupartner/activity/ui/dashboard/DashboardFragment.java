package com.chotupartner.activity.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chotupartner.R;
import com.chotupartner.activity.DashBoardActivity;
import com.chotupartner.activity.ui.home.HomeFragment;
import com.chotupartner.adapter.ConfirmedOrderAdapter;
import com.chotupartner.databinding.FragmentDashboardBinding;
import com.chotupartner.databinding.FragmentHomeBinding;
import com.chotupartner.modelClass.orderlist.OrderInfo;
import com.chotupartner.modelClass.orderlist.OrderListResponse;
import com.chotupartner.retrofit.RestClient;
import com.chotupartner.utils.ChotuBoyPrefs;
import com.chotupartner.utils.Utils;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment implements ConfirmedOrderAdapter.ItemClickListener {

    int orderID;
    String selectedOrderStatus;
    private String comments;
    DashBoardActivity dashBoardActivity;
    FragmentDashboardBinding mBinding;
    ArrayList<OrderInfo> orderInfos = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_dashboard, container, false);
        View view = mBinding.getRoot();
        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dashBoardActivity = (DashBoardActivity) getActivity();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(ChotuBoyPrefs.getString(getActivity(), "outletData"))) {
            getAllOutLetOrders();
        } else {
            getAllDeliveryOrders();
        }
    }


    private void getAllOutLetOrders() {

        RequestBody customeridRequest = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(getActivity(), "outlet_id"));

        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getAllOrderOutlet(customeridRequest, new Callback<OrderListResponse>() {
                @Override
                public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body().getStatus()) {
                        orderInfos.clear();
                        if (response.body() != null) {
                            OrderListResponse cartInfoList = response.body();

                            if (cartInfoList.getOrderInfo() != null && cartInfoList.getOrderInfo().size() > 0) {
                                mBinding.recyclerCartViewConfirm.setVisibility(View.VISIBLE);
                                mBinding.emptyCart.setVisibility(View.GONE);

                                orderInfos.addAll(cartInfoList.getOrderInfo());
                                ConfirmedOrderAdapter horizontalAdapter = new ConfirmedOrderAdapter(getActivity(), orderInfos, DashboardFragment.this);
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


    private void getAllDeliveryOrders() {

        RequestBody customeridRequest = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(getActivity(), "delivery_id"));

        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getAllOrderDelivery(customeridRequest, new Callback<OrderListResponse>() {
                @Override
                public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body().getStatus()) {
                        orderInfos.clear();
                        if (response.body() != null) {
                            OrderListResponse cartInfoList = response.body();

                            if (cartInfoList.getOrderInfo() != null && cartInfoList.getOrderInfo().size() > 0) {
                                mBinding.recyclerCartViewConfirm.setVisibility(View.VISIBLE);
                                mBinding.emptyCart.setVisibility(View.GONE);

                                orderInfos.addAll(cartInfoList.getOrderInfo());
                                ConfirmedOrderAdapter horizontalAdapter = new ConfirmedOrderAdapter(getActivity(), orderInfos, DashboardFragment.this);
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

    }

    @Override
    public void onReject(int orderId) {

    }
}
