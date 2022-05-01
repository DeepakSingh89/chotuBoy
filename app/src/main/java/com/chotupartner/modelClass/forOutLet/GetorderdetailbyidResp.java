package com.chotupartner.modelClass.forOutLet;
import com.chotupartner.modelClass.orderDetailsModel.OrderInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetorderdetailbyidResp {

    @SerializedName("order_info")
    @Expose
    private OrderInfo_ orderInfo;
    @SerializedName("outlet")
    @Expose
    private Outlet outlet;
    @SerializedName("order_product")
    @Expose
    private List<OrderProduct> orderProduct = null;
    @SerializedName("order_history")
    @Expose
    private List<OrderHistory> orderHistory = null;
    @SerializedName("customer_info")
    @Expose
    private CustomerInfo customerInfo;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public OrderInfo_ getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo_ orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Outlet getOutlet() {
        return outlet;
    }

    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }

    public List<OrderProduct> getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(List<OrderProduct> orderProduct) {
        this.orderProduct = orderProduct;
    }

    public List<OrderHistory> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<OrderHistory> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

