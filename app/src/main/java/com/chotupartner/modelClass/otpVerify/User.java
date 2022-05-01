
package com.chotupartner.modelClass.otpVerify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("firstname")
    @Expose
    private Object firstname;
    @SerializedName("lastname")
    @Expose
    private Object lastname;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("address")
    @Expose
    private Object address;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Object getFirstname() {
        return firstname;
    }

    public void setFirstname(Object firstname) {
        this.firstname = firstname;
    }

    public Object getLastname() {
        return lastname;
    }

    public void setLastname(Object lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

}
