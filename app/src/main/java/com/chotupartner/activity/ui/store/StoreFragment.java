package com.chotupartner.activity.ui.store;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chotupartner.R;
import com.chotupartner.activity.DashBoardActivity;
import com.chotupartner.activity.ui.home.HomeViewModel;
import com.chotupartner.adapter.ProductAdapter;
import com.chotupartner.databinding.FragmentStoreBinding;
import com.chotupartner.retrofit.RestClient;
import com.chotupartner.utils.ChotuBoyPrefs;
import com.chotupartner.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StoreFragment extends Fragment implements ProductAdapter.ItemClickListener {


    private HomeViewModel homeViewModel;
    FragmentStoreBinding mBinding;

    DashBoardActivity dashBoardActivity;
    List<ProductOnOutlet> productOnOutletList;
    private AlertDialog dialog;
    private ProductAdapter productAdapter;
    private ProductAdapter productAdapter1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_store, container, false);
        View view = mBinding.getRoot();
        //here data must be an instance of the class MarsDataProvider\

        mBinding.cView.setVisibility(View.VISIBLE);
        mBinding.oView.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(ChotuBoyPrefs.getString(getActivity(), "outlet_id"))) {
            mBinding.confirmTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBinding.cView.setVisibility(View.VISIBLE);
                    mBinding.oView.setVisibility(View.GONE);
                    getAvailableProduct();
                }
            });


            // listening to search query text change


            mBinding.pendingTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBinding.cView.setVisibility(View.GONE);
                    mBinding.oView.setVisibility(View.VISIBLE);
                    getNotAvailableProduct();
                }
            });


            getAvailableProduct();

            mBinding.edtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.toString().length() > 2) {
                        if (mBinding.recyclerCartViewConfirm.getVisibility() == View.VISIBLE) {
                            filter(editable.toString());
                        } else {
                            filter2(editable.toString());

                        }
                    }
                }
            });

        }

        return view;


    }

    void filter(String text) {
        ArrayList<ProductOnOutlet> temp = new ArrayList();
        for (ProductOnOutlet d : productOnOutletList) {
            if (d.getProductName().toLowerCase().contains(text.toLowerCase())) {
                if (!temp.contains(d))
                    temp.add(d);
            }
        }
//        productAdapter = new ProductAdapter(getActivity(), temp, StoreFragment.this);
//        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        mBinding.recyclerCartViewConfirm.setLayoutManager(horizontalLayoutManagaer);
//        mBinding.recyclerCartViewConfirm.setAdapter(productAdapter);
        productAdapter.setData(temp);

    }

    void filter2(String text) {
        ArrayList<ProductOnOutlet> temp1 = new ArrayList();
        for (ProductOnOutlet d : productOnOutletList) {
            if (d.getProductName().toLowerCase().contains(text.toLowerCase())) {
                temp1.add(d);
            }
        }
//        productAdapter = new ProductAdapter(getActivity(), temp1, StoreFragment.this);
//        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        mBinding.recyclerCartViewPending.setLayoutManager(horizontalLayoutManagaer);
        productAdapter.setData(temp1);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dashBoardActivity = (DashBoardActivity) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void getAvailableProduct() {

        RequestBody customeridRequest = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(getActivity(), "outlet_id"));

        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getOutletProduct(customeridRequest, new Callback<List<ProductOnOutlet>>() {
                @Override
                public void onResponse(Call<List<ProductOnOutlet>> call, Response<List<ProductOnOutlet>> response) {
                    Utils.dismissProgressDialog();
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            productOnOutletList = response.body();

                            if (productOnOutletList != null && productOnOutletList.size() > 0) {
                                mBinding.recyclerCartViewConfirm.setVisibility(View.VISIBLE);
                                mBinding.cView.setVisibility(View.VISIBLE);
                                mBinding.oView.setVisibility(View.GONE);
                                mBinding.recyclerCartViewPending.setVisibility(View.GONE);
                                mBinding.emptyCart.setVisibility(View.GONE);

                                productAdapter = new ProductAdapter(getActivity(), productOnOutletList, StoreFragment.this);
                                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                mBinding.recyclerCartViewConfirm.setLayoutManager(horizontalLayoutManagaer);
                                mBinding.recyclerCartViewConfirm.setAdapter(productAdapter);

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
                public void onFailure(Call<List<ProductOnOutlet>> call, Throwable t) {
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


    private void getNotAvailableProduct() {

        RequestBody customeridRequest = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(getActivity(), "outlet_id"));

        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getOutletProductNtAvailable(customeridRequest, new Callback<List<ProductOnOutlet>>() {
                @Override
                public void onResponse(Call<List<ProductOnOutlet>> call, Response<List<ProductOnOutlet>> response) {
                    Utils.dismissProgressDialog();
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            List<ProductOnOutlet> productOnOutletList = response.body();


                            if (productOnOutletList != null && productOnOutletList.size() > 0) {
                                mBinding.cView.setVisibility(View.GONE);
                                mBinding.oView.setVisibility(View.VISIBLE);

                                mBinding.recyclerCartViewPending.setVisibility(View.VISIBLE);
                                mBinding.recyclerCartViewConfirm.setVisibility(View.GONE);
                                mBinding.emptyCart.setVisibility(View.GONE);

                                productAdapter = new ProductAdapter(getActivity(), productOnOutletList, StoreFragment.this);
                                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                mBinding.recyclerCartViewPending.setLayoutManager(horizontalLayoutManagaer);
                                mBinding.recyclerCartViewPending.setAdapter(productAdapter);

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
                public void onFailure(Call<List<ProductOnOutlet>> call, Throwable t) {
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

    private void pickupOrderDialog(ProductOnOutlet productOnOutlet, int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(dashBoardActivity);
        View view = LayoutInflater.from(dashBoardActivity).inflate(R.layout.edit_product, null);

        Button verifyOTp = view.findViewById(R.id.verifyOTp);
        TextView productName = view.findViewById(R.id.productName);
        ImageView imageHolder = view.findViewById(R.id.imagePlaceHolder);
        EditText mrp = view.findViewById(R.id.mrpEdt);
        EditText sellmrp = view.findViewById(R.id.sellingPrice);
        EditText discount = view.findViewById(R.id.discount);
        productName.setText(productOnOutlet.getProductName());
        if (TextUtils.isEmpty(productOnOutlet.getMrp())) {
            mrp.setText("" + productOnOutlet.getPrice());
            sellmrp.setText("" + (!TextUtils.isEmpty(productOnOutlet.getDiscountPrice()) ? productOnOutlet.getDiscountPrice() : "0"));
        } else {
            mrp.setText("" + productOnOutlet.getMrp());
            if (!TextUtils.isEmpty(productOnOutlet.getDiscount()) && Integer.parseInt(productOnOutlet.getDiscount())>0 ){
                sellmrp.setText("" + (int) Math.round((Float.parseFloat(productOnOutlet.getMrp())) - (Float.parseFloat(productOnOutlet.getMrp()) * Float.parseFloat(productOnOutlet.getDiscount()) / 100)));
            }else{
                sellmrp.setText("" +productOnOutlet.getMrp());
            }

        }
        discount.setText("" + productOnOutlet.getDiscount());
        sellmrp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    discount.setText("" + getDiscount(mrp.getText().toString(), s.toString()));
                }

            }
        });


        ImageView crossBtn = view.findViewById(R.id.closeDialog);
        builder.setCancelable(false);
        builder.setView(view);

        verifyOTp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (TextUtils.isEmpty(productOnOutlet.getProductid())) {
                    productOnOutlet.setProductid(productOnOutlet.getProduct_id());
                }
                productOnOutlet.setMrp(mrp.getText().toString());
                productOnOutlet.setDiscountPrice(sellmrp.getText().toString());
                productOnOutlet.setDiscount(discount.getText().toString());
                updateAvailable(productOnOutlet, "1", position);
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

    private int getDiscount(String mrp, String sellingprice) {
        if (!TextUtils.isEmpty(mrp) && !mrp.equalsIgnoreCase("0") && !TextUtils.isEmpty(sellingprice) && !sellingprice.equalsIgnoreCase("0")) {
            if (Integer.parseInt(mrp) >= Integer.parseInt(sellingprice)) {
                return  (int)Math.round(((Float.parseFloat(mrp) - Float.parseFloat(sellingprice)) * 100) / Float.parseFloat(mrp));

            } else {
                Toast.makeText(getActivity(), "Selling price should be less than MRP", Toast.LENGTH_LONG).show();
                return 0;
            }
        } else
            return 0;


    }


    @Override
    public void updateAvailable(ProductOnOutlet productOnOutlet, String available, int position) {
        if (TextUtils.isEmpty(productOnOutlet.getDiscountPrice()) || productOnOutlet.getDiscountPrice().equalsIgnoreCase("0")) {
            Toast.makeText(getActivity(), "Please update selling price first", Toast.LENGTH_LONG).show();
            return;
        }

        RequestBody outletID = RequestBody.create(MediaType.parse("text/plain"), ChotuBoyPrefs.getString(getActivity(), "outlet_id"));
        RequestBody productId = RequestBody.create(MediaType.parse("text/plain"), "" + productOnOutlet.getProductid());
        RequestBody mrp = RequestBody.create(MediaType.parse("text/plain"), "" + (!TextUtils.isEmpty(productOnOutlet.getMrp()) ? productOnOutlet.getMrp() : productOnOutlet.getPrice()));
        RequestBody d_price = RequestBody.create(MediaType.parse("text/plain"), "" + productOnOutlet.getDiscountPrice());
        RequestBody discount = RequestBody.create(MediaType.parse("text/plain"), "" + productOnOutlet.getDiscount());
        RequestBody availability = RequestBody.create(MediaType.parse("text/plain"), available);
        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.updateProductAvailability(outletID, productId, mrp, d_price, discount, availability, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (available.equalsIgnoreCase("1")) {
                            productAdapter.notifyItemChanged(position, productOnOutlet);
                            //  getNotAvailableProduct();
                        } else {
                            productAdapter.notifyItemChanged(position, productOnOutlet);

                            // getAvailableProduct();
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
    public void onEdit(ProductOnOutlet productOnOutlet, int position) {
        pickupOrderDialog(productOnOutlet, position);
    }
}
