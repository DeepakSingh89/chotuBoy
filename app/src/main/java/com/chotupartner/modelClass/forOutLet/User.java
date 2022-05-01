package com.chotupartner.modelClass.forOutLet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

@SerializedName("outlet_id")
@Expose
private String outletId;
@SerializedName("outlet_name")
@Expose
private String outletName;

@SerializedName("delivery_name")
@Expose
private String delivery_name;
@SerializedName("phone")
@Expose
private String phone;
@SerializedName("address")
@Expose
private String address;
@SerializedName("assigned_city")
@Expose
private String assignedCity;

    public String getDelivery_name() {
        return delivery_name;
    }

    public void setDelivery_name(String delivery_name) {
        this.delivery_name = delivery_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @SerializedName("city")
@Expose
private String city;
@SerializedName("zipcode")
@Expose
private String zipcode;
@SerializedName("image")
@Expose
private String image;

public String getOutletId() {
return outletId;
}

public void setOutletId(String outletId) {
this.outletId = outletId;
}

public String getOutletName() {
return outletName;
}

public void setOutletName(String outletName) {
this.outletName = outletName;
}

public String getPhone() {
return phone;
}

public void setPhone(String phone) {
this.phone = phone;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
}

public String getAssignedCity() {
return assignedCity;
}

public void setAssignedCity(String assignedCity) {
this.assignedCity = assignedCity;
}

public String getZipcode() {
return zipcode;
}

public void setZipcode(String zipcode) {
this.zipcode = zipcode;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

}