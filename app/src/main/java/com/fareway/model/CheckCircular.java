package com.fareway.model;

import com.google.gson.annotations.SerializedName;

public class CheckCircular {

    @SerializedName("errorcode")
    public String errorCode;
    @SerializedName("message")
    public String message;
    @SerializedName("ResId")
    public String resId;

    @Override
    public String toString() {
        return "CheckCircular{" +
                "errorCode='" + errorCode + '\'' +
                ", message='" + message + '\'' +
                ", resId='" + resId + '\'' +
                '}';
    }
}
