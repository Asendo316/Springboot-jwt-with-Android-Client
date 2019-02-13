package app.android.almondcareers.com.testclient.models.response;

/**
 * Created by Ibkunle Adeoluwa on 2/13/2019.
 */
public class AccessTokenResponse {

    private String jti;
    private String scope;
    private long expires_in;
    private String token_type;
    private String access_token;

    public AccessTokenResponse() {
    }

    public AccessTokenResponse(String jti, String scope, long expires_in, String token_type, String access_token) {
        this.jti = jti;
        this.scope = scope;
        this.expires_in = expires_in;
        this.token_type = token_type;
        this.access_token = access_token;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "AccessTokenResponse{" +
                "jti='" + jti + '\'' +
                ", scope='" + scope + '\'' +
                ", expires_in=" + expires_in +
                ", token_type='" + token_type + '\'' +
                ", access_token='" + access_token + '\'' +
                '}';
    }
}
