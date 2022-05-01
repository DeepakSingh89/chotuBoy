package com.chotupartner.modelClass.orderlist;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderInfo {

    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("order_customer_id")
    @Expose
    private String orderCustomerId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("shipping_id")
    @Expose
    private Integer shippingId;
    @SerializedName("outlet_id")
    @Expose
    private Integer outletId;
    @SerializedName("delivery_id")
    @Expose
    private Integer deliveryId;
    @SerializedName("razorpay_order_id")
    @Expose
    private Object razorpayOrderId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("outlet_name")
    @Expose
    private String outlet_name;
    @SerializedName("delivery_name")
    @Expose
    private String delivery_name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("state_id")
    @Expose
    private Object stateId;
    @SerializedName("delivery_charge")
    @Expose
    private Integer deliveryCharge;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("slot_book")
    @Expose
    private String slotBook;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;

    public String getSub_service_area() {
        return sub_service_area;
    }

    public void setSub_service_area(String sub_service_area) {
        this.sub_service_area = sub_service_area;
    }

    @SerializedName("sub_service_area")
    @Expose
    private String sub_service_area;


    public String getOutlet_otp() {
        return outlet_otp;
    }

    public void setOutlet_otp(String outlet_otp) {
        this.outlet_otp = outlet_otp;
    }

    @SerializedName("outlet_otp")
    @Expose
    private String outlet_otp;
    @SerializedName("order_product_id")
    @Expose
    private Integer orderProductId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("price_id")
    @Expose
    private Integer priceId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    public String getDelivery_name() {
        return delivery_name;
    }

    public void setDelivery_name(String delivery_name) {
        this.delivery_name = delivery_name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    @SerializedName("contact_no")
    @Expose
    private String contact_no;

    public String getOutlet_name() {
        return outlet_name;
    }

    public void setOutlet_name(String outlet_name) {
        this.outlet_name = outlet_name;
    }

    public String getOutlet_no() {
        return outlet_no;
    }

    public void setOutlet_no(String outlet_no) {
        this.outlet_no = outlet_no;
    }

    @SerializedName("outlet_no")
    @Expose
    private String outlet_no;
    @SerializedName("order_product")
    @Expose
    private List<OrderProduct> orderProduct = null;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderCustomerId() {
        return orderCustomerId;
    }

    public void setOrderCustomerId(String orderCustomerId) {
        this.orderCustomerId = orderCustomerId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public Integer getOutletId() {
        return outletId;
    }

    public void setOutletId(Integer outletId) {
        this.outletId = outletId;
    }

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Object getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(Object razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Object getStateId() {
        return stateId;
    }

    public void setStateId(Object stateId) {
        this.stateId = stateId;
    }


    public Integer getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(Integer deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getSlotBook() {
        return slotBook;
    }

    public void setSlotBook(String slotBook) {
        this.slotBook = slotBook;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Integer getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<OrderProduct> getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(List<OrderProduct> orderProduct) {
        this.orderProduct = orderProduct;
    }

}
