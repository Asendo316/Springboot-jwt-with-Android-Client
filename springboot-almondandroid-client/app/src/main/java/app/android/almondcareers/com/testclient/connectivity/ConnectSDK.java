package app.android.almondcareers.com.testclient.connectivity;

import android.content.Context;


public class ConnectSDK {

    private JsonCaller jsonCaller;

    private Context context;

    private OnResponseListener onResponseListener;

    private final OnErrorListener onErrorListener;


    public ConnectSDK(Context context, OnResponseListener onResponseListener, OnErrorListener onErrorListener) {
        //initializing service caller
        jsonCaller = new JsonCaller();
        this.context = context;
        this.onResponseListener = onResponseListener;
        this.onErrorListener = onErrorListener;
    }


    public void getAccessToken(String TAG) {
        jsonCaller.serve(this.context, TAG, "oauth/token", "Data", "POST", this.onResponseListener, this.onErrorListener);
    }


}
