package multicampus.project.multigo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefManager {
    private static SharedPreferences mSf;

    public static void writeToken(final Context context, final String token) {
        mSf = context.getSharedPreferences("FCM", MODE_PRIVATE);
        SharedPreferences.Editor editor =  mSf.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static void writeUserInfo(final Context context, final String id) {
        mSf = context.getSharedPreferences("INFO", MODE_PRIVATE);
        SharedPreferences.Editor editor =  mSf.edit();
        editor.putString("id", id);
        editor.apply();
    }

    public static String getFCMToken(final Context context) {
        mSf = context.getSharedPreferences("FCM", MODE_PRIVATE);
        return mSf.getString("token", "");
    }

    public static String getUserID(final Context context) {
        mSf = context.getSharedPreferences("INFO", MODE_PRIVATE);
        return mSf.getString("id", "");
    }
}
