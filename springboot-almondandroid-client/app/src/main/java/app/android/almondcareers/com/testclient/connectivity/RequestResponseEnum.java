package app.android.almondcareers.com.testclient.connectivity;

public enum RequestResponseEnum {

    _00("00", "Success", "yes", "success"),
    _99("99", "Failed", "yes", "failed"),
    _33("33", "Error", "yes", "failed"),
    _22("22", "Invalid Token", "yes", "failed"),
    _11("11", "Token is Valid", "yes", "success"),
    _77("77", "Access Authorized", "yes", "success"),
    _101("101", "Email does not exist", "yes", "failed"),
    _102("101", "Password Incorrect", "yes", "failed"),
    UNKNOWN("", "Password Incorrect", "yes", "failed"),
    _88("88", "Access Denied", "yes", "failed");

    private final String respCode;
    private final String respDescription;
    private final String definite;
    private final String tranStatus;

    private RequestResponseEnum(String respCode, String respDescription, String definite, String tranStatus) {

        this.respCode = respCode;
        this.respDescription = respDescription;
        this.definite = definite;
        this.tranStatus = tranStatus;

    }

    /**
     * @return the respCode
     */
    public String getRespCode() {
        return respCode;
    }

    /**
     * @return the respDescription
     */
    public String getRespDescription() {
        return respDescription;
    }

    /**
     * @return the definite
     */
    public String getDefinite() {
        return definite;
    }

    /**
     * @return the tranStatus
     */
    public String getTranStatus() {
        return tranStatus;
    }
}
