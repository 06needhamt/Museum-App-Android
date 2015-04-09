package riskybusiness.riskybusinessmuseumapp.root.classes;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tom on 16/03/2015.
 */
public class SharedPreferencesHandler {

    private SharedPreferences preferences;

    public SharedPreferencesHandler(SharedPreferences prefs)
    {
        this.preferences = prefs;
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(SharedPreferences sp) {
        this.preferences = sp;
    }

    public String getLanguage() {
        return preferences.getString("Language","Not Chosen");
    }

    public void setLanguage(String language) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Language",language);
        editor.apply();
        editor.commit();
    }
}
