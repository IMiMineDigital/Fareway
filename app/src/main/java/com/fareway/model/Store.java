package com.fareway.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Store {

    @SerializedName("errorcode")
    @Expose
    private String errorcode;
    @SerializedName("message")
    @Expose
    private List<Message> message = null;

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Store{" +
                "errorcode='" + errorcode + '\'' +
                ", message=" + message +
                '}';
    }

    public class Message {

        @SerializedName("StoreID")
        @Expose
        private String storeID;
        @SerializedName("StoreName")
        @Expose
        private String storeName;
        @SerializedName("StoreAddress")
        @Expose
        private String storeAddress;
        @SerializedName("StoreCity")
        @Expose
        private String storeCity;
        @SerializedName("StoreState")
        @Expose
        private String storeState;
        @SerializedName("StoreCount")
        @Expose
        private String storeCount;
        @SerializedName("distance")
        @Expose
        private String distance;

        public String getStoreID() {
            return storeID;
        }

        public void setStoreID(String storeID) {
            this.storeID = storeID;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreAddress() {
            return storeAddress;
        }

        public void setStoreAddress(String storeAddress) {
            this.storeAddress = storeAddress;
        }

        public String getStoreCity() {
            return storeCity;
        }

        public void setStoreCity(String storeCity) {
            this.storeCity = storeCity;
        }

        public String getStoreState() {
            return storeState;
        }

        public void setStoreState(String storeState) {
            this.storeState = storeState;
        }

        public String getStoreCount() {
            return storeCount;
        }

        public void setStoreCount(String storeCount) {
            this.storeCount = storeCount;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "Message{" +
                    "storeID='" + storeID + '\'' +
                    ", storeName='" + storeName + '\'' +
                    ", storeAddress='" + storeAddress + '\'' +
                    ", storeCity='" + storeCity + '\'' +
                    ", storeState='" + storeState + '\'' +
                    ", storeCount='" + storeCount + '\'' +
                    ", distance='" + distance + '\'' +
                    '}';
        }
    }
}
