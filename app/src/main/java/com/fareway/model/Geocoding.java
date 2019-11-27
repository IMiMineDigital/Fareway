package com.fareway.model;

import androidx.annotation.StringRes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Geocoding implements Serializable {

    @SerializedName("results")
    private List<Result> result;

    @SerializedName("status")
    private String status;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class Result {
        @SerializedName("address_components")
        public List<AddressComponent> addressComponent;

        @SerializedName("formatted_address")
        public String formattedAddress;

        @SerializedName("geometry")
        public Geometry geometry;

        @SerializedName("place_id")
        public String placeId;

        @SerializedName("types")
        public List<String> types;

    }

    class AddressComponent {

        @SerializedName("long_name")
        public String longName;
        @SerializedName("short_name")
        public String shortName;
        @SerializedName("types")
        public List<String> types;


    }

    public class Geometry {
        @SerializedName("bounds")
        public Bounds bounds;

        @SerializedName("location")
        public Location location;

        @SerializedName("location_type")
        public String locationType;

        @SerializedName("viewport")
        public ViewPort viewPort;

    }

    public class Bounds {
        @SerializedName("northeast")
        public NorthEast northEast;
        @SerializedName("southwest")
        public SouthWest southWest;

    }

    public class Location {
        @SerializedName("lat")
        public String lat;
        @SerializedName("lng")
        public String lng;
    }

    public class ViewPort {
        @SerializedName("northeast")
        public NorthEast northEast;
        @SerializedName("southwest")
        public SouthWest southWest;

    }

    public class NorthEast {
        @SerializedName("lat")
        public String lat;
        @SerializedName("lng")
        public String lng;
    }

    public class SouthWest {
        @SerializedName("lat")
        public String lat;
        @SerializedName("lng")
        public String lng;
    }
}
