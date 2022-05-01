package com.chotupartner.modelClass.forOutLet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderProduct {

    @SerializedName("order_product_id")
    @Expose
    private String orderProductId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("price_id")
    @Expose
    private String priceId;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("regular_price")
    @Expose
    private String regularPrice;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("main_image")
    @Expose
    private String mainImage;

    public String getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(String orderProductId) {
        this.orderProductId = orderProductId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

}
