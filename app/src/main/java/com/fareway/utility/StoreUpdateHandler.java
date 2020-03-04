package com.fareway.utility;

public class StoreUpdateHandler {

    public interface StoreUpdateListenr {
        void userDidUpdateStore(String storeId);
    }

    private static StoreUpdateHandler mInstance;
    private StoreUpdateListenr mListener;
    private String storeId;

    private StoreUpdateHandler() {}

    public static StoreUpdateHandler getInstance() {
        if (mInstance == null) {
            mInstance = new StoreUpdateHandler();
        }
        return mInstance;
    }

    public void setStoreUpdateListener(StoreUpdateListenr mListener) {
        this.mListener = mListener;
    }

    public void setStoreId(String storeId) {
        if (mListener != null) {
            this.storeId = storeId;
        }
    }
}
