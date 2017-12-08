package partyplaner.data.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by André on 01.12.2017.
 */

public class Login {

    public static final String emailRegEx = "^[A-Za-z0-9._%+-]+@[A-Za-zZ0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String usernameRegEx = "^[a-zA-ZÀ-ÿ&&[^÷×]][\\w- À-ÿ&&[^÷×]]{2,49}$";
    public static final String passwordRegEx = "^[\\w!-]{5,50}$";

    private String apiKey;
    private User user;

    public Login() {

    }

    public Login(String apiKey, User user) {
        this.apiKey = apiKey;
        this.user = user;
    }

    public static boolean checkEmail (String email) {
        Pattern pattern = Pattern.compile(emailRegEx);
        Matcher matcher =pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean checkUsername (String username) {
        Pattern pattern = Pattern.compile(usernameRegEx);
        Matcher matcher =pattern.matcher(username);
        return matcher.matches();
    }

    public static boolean checkPassword (String password) {
        Pattern pattern = Pattern.compile(passwordRegEx);
        Matcher matcher =pattern.matcher(password);
        return matcher.matches();
    }

    public boolean loginUser (String email, String password) {
        if (checkEmail(email) && checkPassword(password)) {
            //TODO: API Kram
            apiKey = "TIMtim718mitMIT817";
            user = User.createTestUser();
            return true;
        }
        return false;
    }

    public static boolean checkPasswordEquality(String password, String passwordRepeated) {
        if (checkPassword(password) && checkPassword(passwordRepeated)) {
            return password.equals(passwordRepeated);
        }
        return false;
    }

    public boolean registerUser (String email, String username, String password, String passwordRepeated) {
        if (checkPasswordEquality(password, passwordRepeated)) {
            if (checkEmail(email) && checkUsername(username) && checkPassword(password)) {
                //TODO: API Kram
                apiKey = "TIMtim718mitMIT817";
                user = User.createTestUser();
                return true;
            }
        }
        return false;
    }
}
