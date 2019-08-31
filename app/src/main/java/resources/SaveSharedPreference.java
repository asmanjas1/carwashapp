package resources;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import com.google.gson.Gson;

import beans.Consumer;


public class SaveSharedPreference {

    public static Gson gson = new Gson();

    //Make this to private mode
    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setIsUserLoggedIn(Context ctx){
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(CarConstant.LOGGED_IN,true);
        editor.apply();
    }

    public static void storeFirebaseToken(String token, Context ctx){
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("token",token);
        editor.apply();
    }

    public static String getFirebaseToken(Context ctx){
        return getSharedPreferences(ctx).getString("token",null);
    }

    public static boolean getIsUserLoggedIn(Context ctx){
        return getSharedPreferences(ctx).getBoolean(CarConstant.LOGGED_IN,false);
    }

    public static void setConsumerObj(Context ctx, String consumer){
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(CarConstant.CONSUMER,consumer);
        editor.apply();
    }

    public static String getConsumerObj(Context ctx){
        return getSharedPreferences(ctx).getString(CarConstant.CONSUMER,null);
    }
    public static void logOut(Context ctx){
        Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.apply();
    }

    public static Consumer getConsumerFromGson(Context ctx){
        Consumer consumer = gson.fromJson(getConsumerObj(ctx), Consumer.class);
        return consumer;
    }
}
