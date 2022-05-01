package com.chotupartner.retrofit;

import com.chotupartner.activity.ui.store.ProductOnOutlet;
import com.chotupartner.modelClass.delivery.DeliveryPartnerResponse;
import com.chotupartner.modelClass.forOutLet.GetorderdetailbyidResp;
import com.chotupartner.modelClass.forOutLet.OutletLoginModel;
import com.chotupartner.modelClass.login.LoginWithOtpResponse;
import com.chotupartner.modelClass.orderlist.OrderListResponse;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {


    @Multipart
    @POST("ApiData/SendOtp")
    Call<LoginWithOtpResponse> loginWithOtp(@Part("phone") RequestBody phone, @Part("user_type") RequestBody user_type);

    @Multipart
    @POST("ApiData/VerifyOtp")
    Call<OutletLoginModel> verifyOtpOutLet(@Part("phone") RequestBody phone,
                                           @Part("otp") RequestBody otp,
                                           @Part("user_type") RequestBody user_type
    );

    @Multipart
    @POST("ApiData/updateFcmtokenInOutlet")
    Call<ResponseBody> updateFCMOutlet(@Part("outlet_id") RequestBody phone,
                                           @Part("fcm_token") RequestBody otp
    );


    @Multipart
    @POST("ApiData/VerifyOtp")
    Call<DeliveryPartnerResponse> verifyOtpDelivery(@Part("phone") RequestBody phone,
                                                    @Part("otp") RequestBody otp,
                                                    @Part("user_type") RequestBody user_type
    );


    @Multipart
    @POST("ApiData/updateFcmtokenInDelivery")
    Call<ResponseBody> updateFCMDelivery(@Part("delivery_id") RequestBody phone,
                                                    @Part("fcm_token") RequestBody otp
    );


    @Multipart
    @POST("ApiData/getAllOrder")
    Call<OrderListResponse> GETTING_ORDER_RESPONSE_FOR_OUT_LET_CALL(@Part("outlet_id") RequestBody outlet_id);


    @Multipart
    @POST("ApiData/getNewOrders")
    Call<OrderListResponse> getPendingOrders(@Part("outlet_id") RequestBody outlet_id);


    @Multipart
    @POST("ApiData/getOngoingOrders")
    Call<OrderListResponse> getConfirmedOrders(@Part("outlet_id") RequestBody outlet_id);


    @Multipart
    @POST("ApiData/getOutletProduct")
    Call<List<ProductOnOutlet>> getOutletProduct(@Part("outlet_id") RequestBody outlet_id);


    @Multipart
    @POST("ApiData/getProductWithoutOutletid")
    Call<List<ProductOnOutlet>> getOutletProductNtAvailable(@Part("outlet_id") RequestBody outlet_id);

    @Multipart
    @POST("ApiData/getDeliveryNewOrders")
    Call<OrderListResponse> getDeliveryNewOrders(@Part("delivery_id") RequestBody outlet_id);


    @Multipart
    @POST("ApiData/getDeliveryOngoingOrders")
    Call<OrderListResponse> getDeliveryOngoingOrders(@Part("delivery_id") RequestBody outlet_id);


    @Multipart
    @POST("ApiData/getAllOrder")
    Call<OrderListResponse> getAllOrderDelivery(@Part("delivery_id") RequestBody outlet_id);


    @Multipart
    @POST("ApiData/getAllOrder")
    Call<OrderListResponse> getAllOrderOutlet(@Part("outlet_id") RequestBody outlet_id);


    @Multipart
    @POST("ApiData/get_order_outlet_detail_by_id")
    Call<GetorderdetailbyidResp> GetorderdetailbyidResp(@Part("outlet_id") RequestBody customer_id, @Part("order_id") RequestBody order_id);


    @Multipart
    @POST("ApiData/get_order_delivery_detail_by_id")
    Call<GetorderdetailbyidResp> getorderdetailbyidRespDelivery(@Part("delivery_id") RequestBody customer_id, @Part("order_id") RequestBody order_id);


    @Multipart
    @POST("ApiData/updateOrderStatus")
    Call<ResponseBody> updateOrderStatus(@Part("outlet_id") RequestBody outletId, @Part("order_id") RequestBody customer_id, @Part("order_status") RequestBody order_status);


    @Multipart
    @POST("ApiData/updateOutletProduct")
    Call<ResponseBody> updateProductAvailability(@Part("outlet_id") RequestBody outletId,
                                                 @Part("product_id") RequestBody product_id,
                                                 @Part("mrp") RequestBody mrp,
                                                 @Part("discount_price") RequestBody discount_price,
                                                 @Part("discount") RequestBody discount,
                                                 @Part("availability") RequestBody availability);


    @Multipart
    @POST("ApiData/update_order")
    Call<ResponseBody> update_order(@Part("order_id") RequestBody customer_id, @Part("order_status") RequestBody order_status, @Part("otp") RequestBody otp, @Part("comment") RequestBody comment);


//    @Multipart
//    @POST("ApiData/getAllOrder")
//    Call<GettingOrderResponseForOutLet> GETTING_ORDER_RESPONSE_FOR_OUT_LET_CALL (@Part("outlet_id") RequestBody outlet_id);


}
