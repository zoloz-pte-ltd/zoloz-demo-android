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


import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ap.zoloz.hummer.api.IZLZCallback;
import com.ap.zoloz.hummer.api.ZLZConstants;
import com.ap.zoloz.hummer.api.ZLZFacade;
import com.ap.zoloz.hummer.api.ZLZRequest;
import com.ap.zoloz.hummer.api.ZLZResponse;
import com.zoloz.builder.BuildConfig;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();


    private static final int REQUEST_EXTERNAL_STORAGE = 1;


    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        EditTextUtils.setup(this, R.id.init_host);
        EditTextUtils.setup(this, R.id.init_ref);
        EditTextUtils.setup(this, R.id.init_doc_type);
        EditTextUtils.setup(this, R.id.init_service_level);
    }


    private void runOnIoThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
    }


    public void startZoloz(View view) {
        runOnIoThread(new Runnable() {
            @Override
            public void run() {
                String result = mockInitRequest();
                if (TextUtils.isEmpty(result)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "network exception, please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                final InitResponse initResponse = JSON.parseObject(result, InitResponse.class);
                final ZLZFacade zlzFacade = ZLZFacade.getInstance();
                final ZLZRequest request = new ZLZRequest();
                request.zlzConfig = initResponse.clientCfg;
                request.bizConfig.put(ZLZConstants.CONTEXT, MainActivity.this);
                request.bizConfig.put(ZLZConstants.PUBLIC_KEY, initResponse.rsaPubKey);
                request.bizConfig.put(ZLZConstants.LOCALE, "en-US");
                Log.d(TAG, "request success:");
                mHandler.postAtFrontOfQueue(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "start zoloz");
                        zlzFacade.start(request, new IZLZCallback() {
                            @Override
                            public void onCompleted(ZLZResponse response) {
                                checkResult(initResponse.transactionId);
                            }

                            @Override
                            public void onInterrupted(ZLZResponse response) {
                                showResponse(initResponse.transactionId, JSON.toJSONString(response));
                                Toast.makeText(MainActivity.this, "interrupted", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

    private void checkResult(final String transactionId) {
        runOnIoThread(new Runnable() {
            @Override
            public void run() {
                IRequest request = new LocalRequest();
                String requestUrl = EditTextUtils.getAndSave(MainActivity.this, R.id.init_host) + "/api/realid/checkresult";
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("transactionId", transactionId);
                String requestData = jsonObject.toString();
                final String result = request.request(requestUrl, requestData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showResponse(transactionId, result);
                    }
                });
            }
        });
    }

    private void showResponse(final String flowId, String response) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Check Result")
                .setMessage(response)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData myClip;
                        String text = flowId;
                        myClip = ClipData.newPlainText("text", text);
                        myClipboard.setPrimaryClip(myClip);
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    protected String mockInitRequest() {
        IRequest request = new LocalRequest();
        String requestUrl = EditTextUtils.getAndSave(this, R.id.init_host) + EditTextUtils.getAndSave(this, R.id.init_ref);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("metaInfo", ZLZFacade.getMetaInfo(this));
        jsonObject.put("serviceLevel", EditTextUtils.getAndSave(this, R.id.init_service_level));
        jsonObject.put("docType", EditTextUtils.getAndSave(this, R.id.init_doc_type));
        jsonObject.put("v", BuildConfig.VERSION_NAME);
        String requestData = jsonObject.toString();
        String result = request.request(requestUrl, requestData);
        return result;
    }
}
