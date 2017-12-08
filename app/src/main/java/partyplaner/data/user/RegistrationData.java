package partyplaner.data.user;


import partyplaner.api.LoginHandler;

/**
 * Created by André on 08.12.2017.
 */

public class RegistrationData extends LoginData {

    private String name;

    private String passwordRepeated;

    public RegistrationData() {}

    public RegistrationData (String email, String password, String name, String passwordRepeated) {
        super(email, password);
        this.passwordRepeated = passwordRepeated;
    }

    public void setData(String email, String password, String name, String passwordRepeated) {
        super.setData(email, password);
        this.passwordRepeated = passwordRepeated;
    }

    public String getName() {
        return name;
    }

    public String getPasswordRepeated() {
        return passwordRepeated;
    }

    public boolean register() {
        if (super.getEmail() == null || super.getPassword() == null
                || name == null || passwordRepeated == null) {
            return false;
        }
        return LoginHandler.registerUser(this);
    }
}
