package io.picarete.picarete.model.container.userdata;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.game_logics.ia.EIA;
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.model.EMode;

/**
 * Created by iem on 16/01/15.
 */
public class UserAccessor{
    private static User user;
    private static UserNotifications userNotifications;

    public static User getUser(Context context){
        if(user == null){
            user = load(context);
            userNotifications = new UserNotifications(context);
        }

        return user;
    }

    public void save(Context context) {
        getUser(context).listener = null;
        Gson gson = new Gson();
        String userJson = gson.toJson(this);
        setPref(context, Constants.FILE_USER, Constants.PREFERENCES_USER, userJson);
        getUser(context).listener = userNotifications;
    }

    public void setPref(Context context, String file, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(file, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static User load(Context context){
        String userJson = getPref(context, Constants.FILE_USER, Constants.PREFERENCES_USER, "");
        Gson gson = new Gson();
        if(!userJson.equalsIgnoreCase(""))
            return gson.fromJson(userJson, User.class);
        else
            return new User(context);
    }

    public static String getPref(Context context, String file, String key, String defaultValue) {
        String s = key;
        SharedPreferences preferences = context.getSharedPreferences(file, 0);
        return preferences.getString(s, defaultValue);
    }

    public void userFinishedAGame(Context context, EMode mode, EGameMode gameMode, EIA difficulty, int tilesP1, int tilesP2, int tilesNeutral, int scoreP1, int scoreP2, int result) {
        getUser(context).userFinishedAGame(mode, gameMode, difficulty, tilesP1, tilesP2, tilesNeutral, scoreP1, scoreP2, result);
        getUser(context).MAJStats(context, mode, gameMode, difficulty, tilesP1, tilesP2, tilesNeutral, scoreP1, scoreP2, result);
        save(context);
    }
}
