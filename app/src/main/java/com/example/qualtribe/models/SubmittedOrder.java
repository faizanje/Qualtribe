package com.example.qualtribe.models;

public class SubmittedOrder {

    String requirements;
    String orderId;
    String buyerEmail;
    String attachmentUrl;
    String status;
    String modifications;

    public SubmittedOrder(String orderId) {
        this.orderId = orderId;
    }


    public SubmittedOrder() {

    }

    @Override
    public String toString() {
        return "SubmittedOrder{" +
                "requirements='" + requirements + '\'' +
                ", orderId='" + orderId + '\'' +
                ", buyerEmail='" + buyerEmail + '\'' +
                ", attachmentUrl='" + attachmentUrl + '\'' +
                ", status='" + status + '\'' +
                ", modifications='" + modifications + '\'' +
                '}';
    }

    public String getModifications() {
        return modifications;
    }

    public void setModifications(String modifications) {
        this.modifications = modifications;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SubmittedOrder(String requirements, String orderId, String buyerEmail, String attachmentUrl, String status, String modifications) {
        this.requirements = requirements;
        this.orderId = orderId;
        this.buyerEmail = buyerEmail;
        this.attachmentUrl = attachmentUrl;
        this.status = status;
        this.modifications = modifications;
    }
}
