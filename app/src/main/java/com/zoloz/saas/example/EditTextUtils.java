/**
 * MIT License
 *
 * Copyright (c) 2020 ZOLOZ-PTE-LTD
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.zoloz.saas.example;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.EditText;

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
