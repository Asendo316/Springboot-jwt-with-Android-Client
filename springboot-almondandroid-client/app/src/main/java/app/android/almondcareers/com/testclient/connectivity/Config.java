package app.android.almondcareers.com.testclient.connectivity;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {

    public final static String AUTHENTICATE_TAG = "AUTH";

    /**
     * Converts text to a hashed string
     *
     * @param text
     * @return
     */
    public static String HashString(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.trim().getBytes("UTF-8"));
            Log.i("CONFIG", "HASHING FILE = " + ToBase64Encode(hash));
            return ToBase64Encode(hash);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } catch (UnsupportedEncodingException e) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, e);
            return "";
        }
    }

    /**
     * Converts an original text to base 64 string
     *
     * @param originalInput
     * @return
     */
    public static String ToBase64Encode(String originalInput) {
        try {
            return new String(Base64.encodeToString(originalInput.getBytes("UTF-8"),  Base64.NO_PADDING | Base64.NO_WRAP));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String ToBase64Encode(byte[] originalInput) {
        return new String(Base64.encodeToString(originalInput, Base64.NO_PADDING | Base64.NO_WRAP ));
    }

    /**
     * Converts an bas64 text to original text
     *
     * @param base64Text
     * @return
     */
    public static String FromBase64Decode(String base64Text) {
        return new String(Base64.decode(base64Text, Base64.NO_PADDING));
    }

    /**
     * Gets the android Secure Id
     *
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }


    /**
     * Copys a text item to the clipboad to be used
     *
     * @param context
     * @param key
     * @param text
     */
    public static void copyToClipBoard(Context context, String key, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(key, text);
        if (clipboard == null) return;
        clipboard.setPrimaryClip(clip);
    }

    /**
     * Converts a hash map to url encoded string format
     *
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        Log.d("Config", "getDataString: " + result.toString());
        return result.toString();
    }

    /**
     * Saves a String to Android Local Store
     *
     * @param context
     * @param data
     */
    public static void saveStringToLocal(Context context, String data) {
        SharedPreferences prefs = context.getSharedPreferences(
                "mobile.cordiscorp.com.sheeft", Context.MODE_PRIVATE);

        prefs.edit().putString("tkn", data).apply();
    }

    /**
     * Override method Saves a String to Android Local Store
     *
     * @param context
     * @param key
     * @param data
     */
    public static void saveStringToLocal(Context context, String key, String data) {
        SharedPreferences prefs = context.getSharedPreferences(
                "mobile.cordiscorp.com.sheeft", Context.MODE_PRIVATE);

        prefs.edit().putString(key, data).apply();
    }

    /**
     * Gets the String from android local store
     *
     * @param context
     * @param key
     * @return
     */
    public static String getStringFromLocal(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(
                "mobile.cordiscorp.com.sheeft", Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

}
