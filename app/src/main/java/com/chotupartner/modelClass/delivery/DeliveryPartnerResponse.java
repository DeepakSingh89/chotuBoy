package com.chotupartner.modelClass.delivery;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryPartnerResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("user")
@Expose
private DeliveryUser deliveryUser;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public DeliveryUser getDeliveryUser() {
return deliveryUser;
}

public void setDeliveryUser(DeliveryUser deliveryUser) {
this.deliveryUser = deliveryUser;
}

}
