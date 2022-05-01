
package com.chotupartner.modelClass.getDeliveryBoyInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDeliveryResp {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("info")
    @Expose
    private Info info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}
