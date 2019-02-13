package app.android.almondcareers.com.testclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import app.android.almondcareers.com.testclient.connectivity.Config;
import app.android.almondcareers.com.testclient.connectivity.ConnectSDK;
import app.android.almondcareers.com.testclient.connectivity.OnErrorListener;
import app.android.almondcareers.com.testclient.connectivity.OnResponseListener;
import app.android.almondcareers.com.testclient.models.response.AccessTokenResponse;

public class MainActivity extends AppCompatActivity implements OnErrorListener, OnResponseListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView accessTkn,tokenType,expiry,scope,jti;
    private ConnectSDK connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIComponents();
    }

    private void initUIComponents() {
        accessTkn = findViewById(R.id.accessTkn);
        tokenType = findViewById(R.id.tokenType);
        expiry = findViewById(R.id.expiry);
        scope = findViewById(R.id.scope);
        jti = findViewById(R.id.jti);
    }


    @Override
    protected void onStart() {
        super.onStart();
        connect = new ConnectSDK(this, this, this);
        Log.i(TAG, "STARTING CALL");
        connect.getAccessToken(TAG);
    }

    @Override
    public void OnResponseListener(String response, String Tag) {
        Log.i(TAG, "response is " + response);
        if(response != null || response.isEmpty()){
            Gson gson = new Gson();
            try {
                AccessTokenResponse accessToken = gson.fromJson(response, AccessTokenResponse.class);
                System.out.println("Saving Access Token " + accessToken.toString());
                inflateUIWithResponserDetails(accessToken);

                Config.saveStringToLocal(this, accessToken.getAccess_token());
            } catch (Exception ex) {
                ex.printStackTrace();
                connect.getAccessToken(TAG);
            }
        }

    }

    private void inflateUIWithResponserDetails(AccessTokenResponse accessToken) {
        accessTkn.setText("Access Token : \t"+accessToken.getAccess_token());
        tokenType.setText("Token Type : \t"+accessToken.getToken_type());
        expiry.setText("Expiry :  \t"+accessToken.getExpires_in());
        scope.setText("Scope : \t"+accessToken.getScope());
        jti.setText("Jti : \t"+accessToken.getJti());
    }

    @Override
    public void OnErrorListener(String error, String Tag) {
        Log.i(TAG, "response is " + error);
        connect.getAccessToken(TAG);
    }
}
