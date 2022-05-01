package com.chotupartner.commonBase.fire_base_service;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NotificationPushData implements Parcelable {

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("outLetID")
    @Expose
    private String outLetID;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("customerId")
    @Expose
    private String customerId;
    @SerializedName("userType")
    @Expose
    private String userType;
    @SerializedName("customer")
    @Expose
    private String customer;

    public NotificationPushData(Parcel in) {
        mobile = in.readString();
        message = in.readString();
        outLetID = in.readString();
        orderId = in.readString();
        customerId = in.readString();
        userType = in.readString();
        customer = in.readString();
    }

    public static final Creator<NotificationPushData> CREATOR = new Creator<NotificationPushData>() {
        @Override
        public NotificationPushData createFromParcel(Parcel in) {
            return new NotificationPushData(in);
        }

        @Override
        public NotificationPushData[] newArray(int size) {
            return new NotificationPushData[size];
        }
    };

    public NotificationPushData() {

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOutLetID() {
        return outLetID;
    }

    public void setOutLetID(String outLetID) {
        this.outLetID = outLetID;
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

    public String userType() {
        return userType;
    }

    public void setuserType(String userType) {
        this.userType = userType;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mobile);
        dest.writeString(message);
        dest.writeString(outLetID);
        dest.writeString(orderId);
        dest.writeString(userType);
        dest.writeString(customerId);
        dest.writeString(customer);
    }

    public boolean getUser() {
        return false;
    }
}