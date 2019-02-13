package app.android.almondcareers.com.testclient.models.requests;

/**
 * Created by Ibkunle Adeoluwa on 2/13/2019.
 */
public class Authorization {
    private String grant_type;

    private String username;

    private String password;

//    private String hash;

    /**
     * @return the grant_type
     */
    public String getGrant_type() {
        return grant_type;
    }

    /**
     * @param grant_type the grant_type to set
     */
    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

//    /**
//     * @return the hash
//     */
//    public String getHash() {
//        return hash;
//    }
//
//    /**
//     * @param hash the hash to set
//     */
//    public void setHash(String hash) {
//        this.hash = hash;
//    }


    @Override
    public String toString() {
        return "Authorization{" +
                "grant_type='" + grant_type + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String toDataString() {
        return "Authorization{" + "grant_type=" + grant_type + ", username=" + username + ", password=" + password + '}';
    }

}
