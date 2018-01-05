package partyplaner.data.user;


import android.util.Log;

import partyplaner.api.LoginHandler;

/**
 * RegistrationData erweitert die LoginData Klasse um den Punkt, dass auch Registrierungen
 * ähnlich dem Einloggen durchgeführt werden können.
 *
 * @author André
 * @since 08.12.2017
 */

public class RegistrationData extends LoginData {

    private String name;
    private String passwordRepeated;

    /**
     * Standard Konstruktor für ein RegistrationData Objekt, falls noch keine Daten über die
     * Registrierung bekannt sind.
     */
    public RegistrationData() {}

    /**
     * Konstruktor für ein RegistrationData Objekt, falls alle Informationen zur Registrierung
     * schon bekannt sind.
     *
     * @param email Email Adresse des Nutzers
     * @param name Gewünschter Name des Nutezrs
     * @param password Gewünschtes Passwort des Nutezrs
     * @param passwordRepeated Passwortwiederholung des Nutzers
     */
    public RegistrationData (String email, String name, String password, String passwordRepeated) {
        super(email, password);
        this.name = name;
        this.passwordRepeated = passwordRepeated;
    }

    /**
     * setData setzt alle Informationen zur Registrieung neu.
     *
     * @param email Email Adresse des Nutzers
     * @param name Gewünschter Name des Nutezrs
     * @param password Gewünschtes Passwort des Nutezrs
     * @param passwordRepeated Passwortwiederholung des Nutzers
     */
    public void setData(String email, String password, String name, String passwordRepeated) {
        super.setData(email, password);
        this.name = name;
        this.passwordRepeated = passwordRepeated;
    }

    public String getName() {
        return name;
    }

    public String getPasswordRepeated() {
        return passwordRepeated;
    }

    /**
     * Einfache Funktion zur Registrierung eines Benutzers. ACHTUNG: Registrierungs Daten müssen
     * vorher gesetzt worden sein.
     *
     * @return true, wenn die Registrierung erfolgreich war
     */
    public boolean register() {
        if (super.getEmail() == null || super.getPassword() == null
                || name == null || passwordRepeated == null) {
            return false;
        }
        return LoginHandler.registerUser(this);
    }

    /**
     * Einfache Funktion zur Registrierung eines Benutzer und gleichzeitigem Einloggen.
     * ACHTUNG: Registrierungs Daten müssen vorher gesetzt worden sein.
     *
     * @return I Objekt des eingeloggten Nutzers, falls nicht erfolgreich, dann null
     */
    public I registerAndLogin() {
        if (register()) {
            Log.e("RegistrationData", "Registrierung erfolgreich");
            return new LoginData(this.getEmail(), this.getPassword()).login();
        }
        return null;
    }
}
