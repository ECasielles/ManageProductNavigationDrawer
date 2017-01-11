package com.example.usuario.manageproductsnavigationdrawer.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.usuario.manageproductsnavigationdrawer.interfaces.Preferences;

public class AccountPreferencesImpl implements Preferences {

    private static Preferences accountPreferences;
    // Id de la app (en Project Structure)
    //public static final String FILE = "com.example.usuario.manageproductsrecycler_preferences";

    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    private SharedPreferences sharedPreferences;

    private AccountPreferencesImpl(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // Singleton de la clase
    public static Preferences getInstance(Context context) {
        if(accountPreferences == null)
            accountPreferences = new AccountPreferencesImpl(context);
        return accountPreferences;
    }

    // Recuerda que apply no notifica los fallos
    // Android nos permite no preocuparnos por el ciclo de vida de la aplicación
    public void putUser(String user) {
        getEditor().putString(USER, user).apply();
    }
    public void putPassword(String password) {
        getEditor().putString(PASSWORD, password).apply();
    }
    public void putEmail(String email) {
        getEditor().putString(EMAIL, email).apply();
    }

    private SharedPreferences.Editor getEditor() {
        //SharedPreferences sharedPreferences = new SharedPreferences(FILE);

        // Con el editor pongo los diferentes campos
        return sharedPreferences.edit();
    }

}
