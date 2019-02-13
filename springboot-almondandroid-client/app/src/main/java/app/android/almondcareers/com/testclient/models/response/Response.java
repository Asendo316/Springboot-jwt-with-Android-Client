/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.android.almondcareers.com.testclient.models.response;

/**
 * Created by Ibikunle Adeoluwa on 12/21/2018.
 */
public class Response {

    private String responseCode;

    private String responseDescription;

    private String hash;

    /**
     * @return the responseCode
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * @param responseCode the responseCode to set
     */
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * @return the responseDescription
     */
    public String getResponseDescription() {
        return responseDescription;
    }

    /**
     * @param responseDescription the responseDescription to set
     */
    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    /**
     * @return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    public String toDataString() {
        return "Response{" + "responseCode=" + responseCode + ", responseDescription=" + responseDescription + '}';
    }

    @Override
    public String toString() {
        return "Response{" + "responseCode=" + responseCode + ", responseDescription=" + responseDescription + ", hash=" + hash + '}';
    }

}
