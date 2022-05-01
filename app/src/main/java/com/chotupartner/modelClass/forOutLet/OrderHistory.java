
package com.chotupartner.modelClass.forOutLet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistory {

    @SerializedName("order_history_id")
    @Expose
    private String orderHistoryId;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("comment")
    @Expose
    private Object comment;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;

    public String getOrderHistoryId() {
        return orderHistoryId;
    }

    public void setOrderHistoryId(String orderHistoryId) {
        this.orderHistoryId = orderHistoryId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

}
