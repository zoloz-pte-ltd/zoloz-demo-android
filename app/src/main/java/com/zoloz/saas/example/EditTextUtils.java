package com.zoloz.saas.example;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.EditText;

/**
 * @author zhaocheng.luo
 * @since 2020/11/2
 */
public class EditTextUtils {
    public static final String SAAS_EXAMPLE_SHARED_PREFERENCES = "saas_example_data";

    public static void setup(Activity activity, int resId) {
        EditText textView = activity.findViewById(resId);
        SharedPreferences preferences = activity.getSharedPreferences(SAAS_EXAMPLE_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String savedValue = preferences.getString(String.valueOf(resId), "");
        if (!TextUtils.isEmpty(savedValue)) {
            textView.setText(savedValue);
        }
    }

    public static String getAndSave(Activity activity, int resId) {
        EditText textView = activity.findViewById(resId);
        String currentValue = textView.getText().toString();
        SharedPreferences preferences = activity.getSharedPreferences(SAAS_EXAMPLE_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putString(String.valueOf(resId), currentValue).commit();
        return currentValue;
    }
}
