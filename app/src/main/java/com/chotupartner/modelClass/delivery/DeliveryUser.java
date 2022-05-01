package com.chotupartner.modelClass.delivery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryUser {

@SerializedName("delivery_id")
@Expose
private String deliveryId;
@SerializedName("delivery_name")
@Expose
private String deliveryName;
@SerializedName("address")
@Expose
private String address;
@SerializedName("city")
@Expose
private String city;
@SerializedName("zipcode")
@Expose
private String zipcode;

public String getDeliveryId() {
return deliveryId;
}

public void setDeliveryId(String deliveryId) {
this.deliveryId = deliveryId;
}

public String getDeliveryName() {
return deliveryName;
}

public void setDeliveryName(String deliveryName) {
this.deliveryName = deliveryName;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
}

public String getCity() {
return city;
}

public void setCity(String city) {
this.city = city;
}

public String getZipcode() {
return zipcode;
}

public void setZipcode(String zipcode) {
this.zipcode = zipcode;
}

}