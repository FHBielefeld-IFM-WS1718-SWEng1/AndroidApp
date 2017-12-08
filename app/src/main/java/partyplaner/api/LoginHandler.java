package partyplaner.api;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import partyplaner.data.user.I;
import partyplaner.data.user.LoginData;
import partyplaner.data.user.RegistrationData;

/**
 * Created by André on 01.12.2017.
 */

public class LoginHandler {

    public static final String emailRegEx = "^[A-Za-z0-9._%+-]+@[A-Za-zZ0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String usernameRegEx = "^[a-zA-ZÀ-ÿ&&[^÷×]][\\w- À-ÿ&&[^÷×]]{2,49}$";
    public static final String passwordRegEx = "^[\\w!-]{5,50}$";

    public static boolean checkEmail (String email) {
        Pattern pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean checkUsername (String username) {
        Pattern pattern = Pattern.compile(usernameRegEx);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static boolean checkPassword (String password) {
        Pattern pattern = Pattern.compile(passwordRegEx);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean checkPasswordEquality(String password, String passwordRepeated) {
        if (checkPassword(password) && checkPassword(passwordRepeated)) {
            return password.equals(passwordRepeated);
        }
        return false;
    }

    public static I loginUser (LoginData data) {
        if (checkEmail(data.getEmail()) && checkPassword(data.getPassword())) {
            try {
                return APIConnectionHandler.getAPIConnectionHandler().login(data);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    public static boolean registerUser (RegistrationData data) {
        if (checkPasswordEquality(data.getPassword(), data.getPasswordRepeated())) {
            if (checkEmail(data.getEmail()) && checkUsername(data.getName()) && checkPassword(data.getPassword())) {
                try {
                    return APIConnectionHandler.getAPIConnectionHandler().register(data);
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return false;
    }
}
