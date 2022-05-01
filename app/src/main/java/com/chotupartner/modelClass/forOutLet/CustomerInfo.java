
package com.chotupartner.modelClass.forOutLet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerInfo {

    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private Object password;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("postcode")
    @Expose
    private Object postcode;
    @SerializedName("state_id")
    @Expose
    private Object stateId;
    @SerializedName("verify_number")
    @Expose
    private String verifyNumber;
    @SerializedName("referred_id")
    @Expose
    private String referredId;
    @SerializedName("wallet_balence")
    @Expose
    private Object walletBalence;
    @SerializedName("wallet_deduct")
    @Expose
    private String walletDeduct;
    @SerializedName("wallet_status")
    @Expose
    private String walletStatus;
    @SerializedName("login_status")
    @Expose
    private Object loginStatus;
    @SerializedName("partner")
    @Expose
    private String partner;
    @SerializedName("partner_comm")
    @Expose
    private String partnerComm;
    @SerializedName("activation_status")
    @Expose
    private String activationStatus;
    @SerializedName("deletion_status")
    @Expose
    private String deletionStatus;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;
    @SerializedName("lastlogin")
    @Expose
    private String lastlogin;
    @SerializedName("fcm_token")
    @Expose
    private Object fcmToken;
    @SerializedName("token_updatetime")
    @Expose
    private Object tokenUpdatetime;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getPostcode() {
        return postcode;
    }

    public void setPostcode(Object postcode) {
        this.postcode = postcode;
    }

    public Object getStateId() {
        return stateId;
    }

    public void setStateId(Object stateId) {
        this.stateId = stateId;
    }

    public String getVerifyNumber() {
        return verifyNumber;
    }

    public void setVerifyNumber(String verifyNumber) {
        this.verifyNumber = verifyNumber;
    }

    public String getReferredId() {
        return referredId;
    }

    public void setReferredId(String referredId) {
        this.referredId = referredId;
    }

    public Object getWalletBalence() {
        return walletBalence;
    }

    public void setWalletBalence(Object walletBalence) {
        this.walletBalence = walletBalence;
    }

    public String getWalletDeduct() {
        return walletDeduct;
    }

    public void setWalletDeduct(String walletDeduct) {
        this.walletDeduct = walletDeduct;
    }

    public String getWalletStatus() {
        return walletStatus;
    }

    public void setWalletStatus(String walletStatus) {
        this.walletStatus = walletStatus;
    }

    public Object getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Object loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPartnerComm() {
        return partnerComm;
    }

    public void setPartnerComm(String partnerComm) {
        this.partnerComm = partnerComm;
    }

    public String getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(String activationStatus) {
        this.activationStatus = activationStatus;
    }

    public String getDeletionStatus() {
        return deletionStatus;
    }

    public void setDeletionStatus(String deletionStatus) {
        this.deletionStatus = deletionStatus;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }

    public Object getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(Object fcmToken) {
        this.fcmToken = fcmToken;
    }

    public Object getTokenUpdatetime() {
        return tokenUpdatetime;
    }

    public void setTokenUpdatetime(Object tokenUpdatetime) {
        this.tokenUpdatetime = tokenUpdatetime;
    }

}
