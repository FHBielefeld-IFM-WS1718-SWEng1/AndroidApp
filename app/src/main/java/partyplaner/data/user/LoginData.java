package partyplaner.data.user;

import partyplaner.api.LoginHandler;

/**
 * LoginData speichert Login Informationen des Nutzers und ermöglicht gleichzeitig ein einfaches
 * Einloggen.
 *
 * @author André
 * @since 08.12.2017
 */

public class LoginData {

    private String email;
    private String password;

    /**
     * Standard Konstruktor für ein LoginData Objekt, falls noch keine Daten über den
     * Login bekannt sind.
     */
    public LoginData() {}

    /**
     * Konstruktor für ein LoginData Objekt, falls alle Informationen zum Login
     * schon bekannt sind.
     *
     * @param email Email Adresse des Nutzers
     * @param password Passwort des Nutezrs
     */
    public LoginData (String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * setData setzt alle Informationen zum Login neu.
     *
     * @param email Email Adresse des Nutzers
     * @param password Passwort des Nutezrs
     */
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

    /**
     * Einfache Funktion zum Einloggen eines Nutzers. ACHTUNG: Login Daten müssen vorher gesetzt
     * worden sein.
     *
     * @return I Objekt des eingeloggten Nutzers, falls nicht erfolgreich, dann null
     */
    public I login() {
        if (email == null || password == null) {
            return null;
        }
        if (I.getMyself() != null) {
            return null;
        }
        I i = LoginHandler.loginUser(this);
        I.setMyself(i);
        return i;
    }
}
