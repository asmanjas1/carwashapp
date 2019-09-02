package resources;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.util.Map;

import beans.Consumer;
import beans.ConsumerFirebase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import studio.carwash.com.carwash.CarActivity;


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

    public static void saveFirebaseTokenToserver(Context ctx){
        ConsumerFirebase cf =  new ConsumerFirebase();
        cf.setConsumerFirebaseId(getConsumerFromGson(ctx).getConsumerId());
        String token = getFirebaseToken(ctx);

        if(token == null){
            token = FirebaseInstanceId.getInstance().getToken();
        }

        cf.setFirebaseToken(token);
        if (token != null){
            RestInvokerService restInvokerService = RestClient.getClient().create(RestInvokerService.class);
            Call<Map<String, Object>> call = restInvokerService.saveFirevaseToken(cf);
            call.enqueue(new Callback<Map<String, Object>>() {
                @Override
                public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                    Map<String, Object> map = response.body();
                    if( map.get("resCode").equals(200.0)){
                        Log.d("Firebase Token Store: ",map.toString());
                    } else {
                        Log.d("Firebase Token Store: ","Failed");
                    }
                }
                @Override
                public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                }
            });
        }
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
        String token = getFirebaseToken(ctx);
        Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.apply();
        storeFirebaseToken(token, ctx);
    }

    public static Consumer getConsumerFromGson(Context ctx){
        Consumer consumer = gson.fromJson(getConsumerObj(ctx), Consumer.class);
        return consumer;
    }
}
