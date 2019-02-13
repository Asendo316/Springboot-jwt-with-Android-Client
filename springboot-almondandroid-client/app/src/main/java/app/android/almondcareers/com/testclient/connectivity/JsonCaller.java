package app.android.almondcareers.com.testclient.connectivity;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.util.HashMap;

import app.android.almondcareers.com.testclient.MainActivity;
import app.android.almondcareers.com.testclient.models.requests.Authorization;


/**
 * Created by Ibikunle on 9/28/2017.
 */

public class JsonCaller {

    private OnResponseListener onResponseListener;

    private OnErrorListener onErrorListener;

    private String response = "mock";

    private Context context;

    private String accessToken;

    private boolean isError;

   /* public static JsonCaller getInstance() {
        return jsoncaller;
    }*/

    /**
     * Pushes data to server on POST request Method
     *
     * @param context
     * @param Tag
     * @param Url
     * @param jsondata
     * @param onResponseListener
     * @param onErrorListener
     */
    public void serve(Context context, String Tag, String Url, String jsondata, String requestType, OnResponseListener onResponseListener, OnErrorListener onErrorListener) {
        this.onResponseListener = onResponseListener;
        this.onErrorListener = onErrorListener;
        this.context = context;
        if (Tag.contentEquals(MainActivity.class.getSimpleName())) {
            this.setAccessToken("");
        } else {
            System.out.println("Access Token " + Config.getStringFromLocal(this.context, "tkn"));
            this.setAccessToken(Config.getStringFromLocal(this.context, "tkn"));
        }
        new PostData(jsondata, ApiUtils.BASE_URL + Url, requestType, Tag).execute();
    }


    /**
     * Uploads files or file to server
     *
     * @param context
     * @param Tag
     * @param Url
     * @param jsondata
     * @param onResponseListener
     */
    public void servePostData(Context context, String Tag, String Url, String jsondata, OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
        this.context = context;
        new UploadFileToServer(jsondata, Url, Tag).execute();
    }


    public void servePostVnd(Context context, String Tag, String Url, String jsondata, OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
        this.context = context;
        new UploadFileToServer(jsondata, Url, Tag).execute();
    }

    private class PostData extends AsyncTask<String, Void, Void> {
        String request, Tag, Url, requestTpe;


        public PostData(String request, String Url, String requestTpe, String tag) {
            Log.i("DataRequest", "POST Data: " + request);

            this.request = request;
            this.Tag = tag;
            this.Url = Url;
            this.requestTpe = requestTpe;

        }

        protected void onPreExecute() {
            Log.i("DataRequest", "PRE EXECUTE");
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                Log.i("DataRequest", "IN EXECUTE");
                if (getAccessToken().contentEquals("")) {
                    Authorization auth = new Authorization();
                    auth.setGrant_type("password");
                    auth.setPassword("jwtpass");
                    auth.setUsername("admin.admin");
                    //auth.setHash(Config.HashString(auth.toDataString()));

                    HashMap<String, String> data = new HashMap<>();

                    data.put("grant_type", auth.getGrant_type());
                    data.put("password", auth.getPassword());
                    data.put("username", auth.getUsername());
                    //data.put("hash", auth.getHash());

                    response = multiFormPartUrlMethod(Config.getDataString(data), Url);
                } else {
                    if (requestTpe.contentEquals("POST") || requestTpe.contentEquals("PUT")) {
                        response = getPostMethods(request, Url, requestTpe);
                    } else if (requestTpe.contentEquals("GET")) {
                        response = GetMethod(request, Url, requestTpe);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = RequestResponseEnum._88.getRespCode();
            }
            return null;
        }

        protected void onPostExecute(Void unused) {
            if (isError) {
                onErrorListener.OnErrorListener(response, Tag);
            } else {
                onResponseListener.OnResponseListener(response, Tag);
            }
        }
    }

    /**
     * Uses the POST REST API method to access API url
     *
     * @param json
     * @param urlstr
     * @return
     * @throws MalformedURLException
     */
    public String getPostMethods(String json, String urlstr, String requestTpe) {
        StringBuilder response = new StringBuilder();
        try {
            Log.i("DataRequest", "STARTING POST " + json);
            URL url = new URL(urlstr);
            Log.i("DataRequest", "url:========" + url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.getRequestProperty("application/json");
            conn.setRequestMethod(requestTpe);
            conn.setConnectTimeout(5000);
            conn.addRequestProperty("accept", "application/json");
            conn.addRequestProperty("Content-Type", "application/json");
            conn.addRequestProperty("Authorization",""+ getAccessToken());
            // 30 sec = 30000 milliSecond
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json);
            wr.flush();
            wr.close();
            BufferedReader in = null;

            if ((200 <= conn.getResponseCode()) && (conn.getResponseCode() <= 299)) {
                if (conn.getInputStream() != null) {
                    in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    isError = false;
                }
            } else if (conn.getErrorStream() != null) {
                in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                isError = true;
            }
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            this.response = response.toString();
            Log.i("Post Response: ", this.response);
        } catch (Exception e) {
            Log.i("DataRequest", "error is " + e.getMessage());
            e.printStackTrace();
            this.response = RequestResponseEnum._88.getRespCode(); // e.toString();
            isError = true;
        }
        return this.response;
    }

    /**
     * Gets data from a particular url
     *
     * @param urlstr
     * @return
     * @throws MalformedURLException
     */
    public String GetMethod(String json, String urlstr, String requestTpe) throws MalformedURLException {
        HttpURLConnection conn = null;
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(urlstr);
            conn = (HttpURLConnection) url.openConnection();
            conn.getRequestProperty("application/json");
            conn.setConnectTimeout(5000);
            conn.setRequestMethod(requestTpe);
            conn.addRequestProperty("accept", "application/json");
            conn.addRequestProperty("Content-Type", "application/json");
            conn.addRequestProperty("Authorization", "Bearer " + getAccessToken());
            BufferedReader in = null;

            if ((200 <= conn.getResponseCode()) && (conn.getResponseCode() <= 299)) {
                if (conn.getInputStream() != null) {
                    in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    isError = false;
                }
            } else if (conn.getErrorStream() != null) {
                in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                isError = true;
            }
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            this.response = response.toString();
            Log.i("Post Response: ", this.response);
        } catch (Exception e) {
            Log.i("DataRequest", "error is " + e.getMessage());
            e.printStackTrace();
            this.response = RequestResponseEnum._88.getRespCode(); // e.toString();
            isError = true;
        }
        return this.response;
    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        String request, Tag, Url;

        public UploadFileToServer(String request, String Url, String tag) {
            Log.e("DataRequest", "UPLOAD Data: " + request.toString());
            this.request = request;
            this.Tag = tag;
            this.Url = Url;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected String doInBackground(Void... params) {
            if (Tag.equalsIgnoreCase("VerifyReference")) {
                return uploadRefre(Url, Tag, request);
            } else if (Tag.equalsIgnoreCase("POSTVND")) {
                return PostVnd(Url, Tag, request);
            } else {
                return uploadFile(Url, Tag, request);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("DataRequest", "onPostExecute: " + result);
            if (!result.equalsIgnoreCase("")) {
                onResponseListener.OnResponseListener(result, Tag);
            } else {
                Toast.makeText(context, "Your internet connection is weak", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String uploadFile(String Url, String Tag, String request) {
        String responseString = "";
        try {
            JSONObject object = new JSONObject(request);
            Log.e("JsonCallre", "uploadFile: " + Url);
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Url);
            MultipartEntity mpEntity = new MultipartEntity();
            mpEntity.addPart("ema", new StringBody(object.optString("ema")));
            mpEntity.addPart("amt", new StringBody(object.optString("amt")));
            mpEntity.addPart("vnd", new StringBody(object.optString("vnd")));
            mpEntity.addPart("dat", new StringBody(object.optString("dat")));
            mpEntity.addPart("itm", new StringBody(object.optString("itm")));
            mpEntity.addPart("imei", new StringBody(object.optString("imei")));
            mpEntity.addPart("product_ids", new StringBody(object.optString("product_ids")));
            httppost.setEntity(mpEntity);
            HttpResponse response;
            response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                responseString = "" + EntityUtils.toString(resEntity).toString();
                Log.e("JsonCaller", "uploadFile: " + responseString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;

    }

    public DefaultHttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            // registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    private String uploadRefre(String Url, String Tag, String request) {
        String responseString = "";
        try {
            JSONObject object = new JSONObject(request);
            Log.e("JsonCallre", "uploadFile: " + Url);
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Url);
            MultipartEntity mpEntity = new MultipartEntity();
            mpEntity.addPart("ref", new StringBody(object.optString("ref")));
            mpEntity.addPart("vnd", new StringBody(object.optString("vnd")));
            httppost.setEntity(mpEntity);
            HttpResponse response;
            response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                responseString = "" + EntityUtils.toString(resEntity).toString();
                Log.e("JsonCaller", "uploadFile: " + responseString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;

    }

    public String PostVnd(String Url, String Tag, String request) {
        String responseString = "";
        try {
            JSONObject object = new JSONObject(request);
            Log.e("JsonCallre", "uploadFile: " + Url + request);
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Url);
            MultipartEntity mpEntity = new MultipartEntity();
            mpEntity.addPart("vnd", new StringBody(object.optString("vnd")));
            httppost.setEntity(mpEntity);
            HttpResponse response;
            response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            Log.e("JsonCaller", "uploadFile: " + resEntity);
            if (resEntity != null) {
                responseString = "" + EntityUtils.toString(resEntity).toString();
                Log.e("JsonCaller", "uploadFile: " + responseString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;
    }

    public String multiFormPartUrlMethod(String form, String urlstr) {

        StringBuilder response = new StringBuilder();

        try {
            Log.i("DataRequest", "STARTING POST");
            URL url = new URL(urlstr);
            Log.i("DataRequest", "url:========" + url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.getRequestProperty("application/json");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.addRequestProperty("accept", "application/json");
            conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String auth = "dGVzdGp3dGNsaWVudGlkOlhZN2ttem9OemwxMDA=";
            //auth = Config.ToBase64Encode(auth);
            Log.d("AUTH", "multiFormPartUrlMethod: " + auth);
//            Log.i("CONFIG", "SETTING VALUE JSON 3 = " + auth);
            conn.addRequestProperty("Authorization", "Basic " + auth);
            // 30 sec = 30000 milliSecond
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(form);
            wr.flush();
            wr.close();
//            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());

            BufferedReader in = null;

            if ((200 <= conn.getResponseCode()) && (conn.getResponseCode() <= 299)) {
                if (conn.getInputStream() != null) {
                    in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    isError = false;
                }
            } else if (conn.getErrorStream() != null) {
                in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                isError = true;
            }

//            response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            this.response = response.toString();
            Log.i("Post Response: ", "response is " + response);
        } catch (Exception e) {
            Log.i("DataRequest", "error is " + e.getMessage());
            e.printStackTrace();
            this.response = RequestResponseEnum._88.getRespCode(); // e.toString();
            isError = true;
        }
        return this.response;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
