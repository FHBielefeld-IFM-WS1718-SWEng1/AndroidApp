package partyplaner.data.user;

import partyplaner.api.LoginHandler;

/**
 * Created by André on 08.12.2017.
 */

public class LoginData {

    private String email;
    private String password;

    public LoginData() {}

    public LoginData (String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public I login() {
        if (email == null || password == null) {
            return null;
        }
        return LoginHandler.loginUser(this);
    }
}
