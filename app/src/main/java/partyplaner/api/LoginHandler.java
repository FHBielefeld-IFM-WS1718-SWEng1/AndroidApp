package partyplaner.api;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import partyplaner.data.user.I;
import partyplaner.data.user.LoginData;
import partyplaner.data.user.RegistrationData;

/**
 * Der LoginHandler kümmert sich um alle Login relevanten Dinge. Zum einen bietet er Methoden zum
 * Testen der User Eingaben. Zum anderen bietet er login und register Methoden an.
 *
 * @author André
 * @since 01.12.2017
 */

public class LoginHandler {

    /**
     * Regulärer Ausdruck zum Testen auf eine Email Adresse.
     */
    public static final String emailRegEx = "^[A-Za-z0-9._%+-]+@[A-Za-zZ0-9.-]+\\.[A-Za-z]{2,}$";
    /**
     * Regulärer Ausdruck zum Testen auf einen passenden Benutzernamen.
     */
    public static final String usernameRegEx = "^[a-zA-ZÀ-ÿ&&[^÷×]][\\w- À-ÿ&&[^÷×]]{2,49}$";
    /**
     * Regulärer Ausdruck zum Testen auf ein passendes Passwort.
     */
    public static final String passwordRegEx = "^[\\w!-]{5,50}$";

    private LoginHandler() {}

    /**
     * Testet, ob ein angegebener String eine gültige Email Adresse ist.
     *
     * @param email String, der getestet werden soll
     * @return true, wenn es eine gültige Email Adresse ist
     */
    public static boolean checkEmail (String email) {
        Pattern pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Testet, ob ein angegebener String ein gültiger Benutzername ist.
     *
     * @param username String, der getestet werden soll
     * @return true, wenn es ein gültiger Benutzername ist
     */
    public static boolean checkUsername (String username) {
        Pattern pattern = Pattern.compile(usernameRegEx);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }


    /**
     * Testet, ob ein angegebener String ein gültiges Passwort ist.
     *
     * @param password String, der getestet werden soll
     * @return true, wenn es ein gültiges Passwort ist
     */
    public static boolean checkPassword (String password) {
        Pattern pattern = Pattern.compile(passwordRegEx);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * Testet, ob zwei angegebene Passwörter/Strings gleich sind.
     *
     * @param password Das erste Passwort
     * @param passwordRepeated Das zweite Passwort, das gleich dem ersten sein soll
     * @return true, wenn beide Passwörter gleich sind
     */
    public static boolean checkPasswordEquality(String password, String passwordRepeated) {
        if (checkPassword(password) && checkPassword(passwordRepeated)) {
            return password.equals(passwordRepeated);
        }
        return false;
    }

    /**
     * loginUser testet auf gültige Login Daten und versucht dann, den Benutzer einzuloggen.
     *
     * @param data wichtige Login Informationen
     * @return I Objekt, das alle über die API empfangenen Daten enthält
     */
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

    /**
     * registerUser testet auf gültige Registrierungs Daten und versucht dann, den Benutzer zu
     * registrieren.
     *
     * @param data wichtige Registrierungs Informationen
     * @return true, wenn der Benutzer erfolgreich registriert wurde.
     */
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
