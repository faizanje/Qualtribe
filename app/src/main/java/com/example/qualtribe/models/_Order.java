//package com.example.qualtribe.models;
//
//import com.example.qualtribe.activities.seller_order;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//
//public class Order implements Serializable {
//
//    String requirements;
//    String orderId;
//    String buyerEmail;
//    String attachmentUrl;
//    String status;
//    String modifications;
//    String sellerID;
//    String price;
//
//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }
//
//    public Order(seller_order seller_order, ArrayList<Order> orderArrayList1) {
//
//    }
//
//    public String getSellerID() {
//        return sellerID;
//    }
//
//    public void setSellerID(String sellerID) {
//        this.sellerID = sellerID;
//    }
//
//    public Order(String orderId) {
//        this.orderId = orderId;
//    }
//
//
//    public Order() {
//
//    }
//
//
//    public String getModifications() {
//        return modifications;
//    }
//
//    public void setModifications(String modifications) {
//        this.modifications = modifications;
//    }
//
//    public String getRequirements() {
//        return requirements;
//    }
//
//    public void setRequirements(String requirements) {
//        this.requirements = requirements;
//    }
//
//    public String getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(String orderId) {
//        this.orderId = orderId;
//    }
//
//    public String getBuyerEmail() {
//        return buyerEmail;
//    }
//
//    public void setBuyerEmail(String buyerEmail) {
//        this.buyerEmail = buyerEmail;
//    }
//
//    public String getAttachmentUrl() {
//        return attachmentUrl;
//    }
//
//    public void setAttachmentUrl(String attachmentUrl) {
//        this.attachmentUrl = attachmentUrl;
//    }
//
//    @Override
//    public String toString() {
//        return "SubmittedOrder{" +
//                "requirements='" + requirements + '\'' +
//                ", orderId='" + orderId + '\'' +
//                ", buyerEmail='" + buyerEmail + '\'' +
//                ", attachmentUrl='" + attachmentUrl + '\'' +
//                ", status='" + status + '\'' +
//                ", modifications='" + modifications + '\'' +
//                ", sellerID='" + sellerID + '\'' +
//                '}';
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public Order(String requirements, String orderId, String buyerEmail, String attachmentUrl, String status, String modifications, String sellerID) {
//        this.requirements = requirements;
//        this.orderId = orderId;
//        this.buyerEmail = buyerEmail;
//        this.attachmentUrl = attachmentUrl;
//        this.status = status;
//        this.modifications = modifications;
//        this.sellerID = sellerID;
//    }
//}
