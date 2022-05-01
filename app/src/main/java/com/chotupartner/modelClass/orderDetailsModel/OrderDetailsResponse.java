
package com.chotupartner.modelClass.orderDetailsModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailsResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("order_info")
    @Expose
    private List<OrderInfo> orderInfo = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<OrderInfo> getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(List<OrderInfo> orderInfo) {
        this.orderInfo = orderInfo;
    }

}
