package com.chotupartner.activity.ui.store;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductOnOutlet {

@SerializedName("id")
@Expose
private String id;
@SerializedName("outletid")
@Expose
private String outletid;
@SerializedName("productid")
@Expose
private String productid;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @SerializedName("product_id")
@Expose
private String product_id;
@SerializedName("mrp")
@Expose
private String mrp;

@SerializedName("price")
@Expose
private String price;
@SerializedName("discount")
@Expose
private String discount;
@SerializedName("discount_price")
@Expose
private String discountPrice;
@SerializedName("availability")
@Expose
private String availability;
@SerializedName("product_name")
@Expose
private String productName;
@SerializedName("product_images")
@Expose
private String productImages;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getOutletid() {
return outletid;
}

public void setOutletid(String outletid) {
this.outletid = outletid;
}

public String getProductid() {
return productid;
}

public void setProductid(String productid) {
this.productid = productid;
}

public String getMrp() {
return mrp;
}

public void setMrp(String mrp) {
this.mrp = mrp;
}

public String getDiscount() {
return discount;
}

public void setDiscount(String discount) {
this.discount = discount;
}

public String getDiscountPrice() {
return discountPrice;
}

public void setDiscountPrice(String discountPrice) {
this.discountPrice = discountPrice;
}

public String getAvailability() {
return availability;
}

public void setAvailability(String availability) {
this.availability = availability;
}

public String getProductName() {
return productName;
}

public void setProductName(String productName) {
this.productName = productName;
}

public String getProductImages() {
return productImages;
}

public void setProductImages(String productImages) {
this.productImages = productImages;
}

}