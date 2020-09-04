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


public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();


    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    public static final String DEFAULT_URL = "http://192.168.3.8:8080/api/realid/initialize";

    //public static final String DEFAULT_PUBKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr+xWyrMYViLrdV8VAh4BrHbFyKbY8rcaSZA5C7eajHH/3oYZQUexvwTFd3LmoeATfNlZS4xxVWhKZXxSIeH7MogrDB//vDIWviHT3/5cW/dHqqK2SU6hpsDiOyxxXLhqRhyKMxs7gLfg1WvMUlhOoJuVtyFQC4/501cf2Z/4PIVaHh6xq7v9Ot5RFmOP2n+H2NaJyDl1vtRU5wdJZM+X1iP/hEA+Ms2riRCU+vf3020BNWNsw09qYvJIqTS1IE3wle6z/H+5teN0alBEJlNVqcGXvyzw2hqhbQLW+G+eONW99S+8nNqub7V11TwSLzmgVDm9IQZ1P8mARzDhlqfpVwIDAQAB";

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(this,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        EditText editText = findViewById(R.id.init_url);
        editText.setText(getSaveUrl(getDefaultUrl()));

    }


    protected String getDefaultUrl() {
        return DEFAULT_URL;
    }


    private String getSaveUrl(String defaultUrl) {
        SharedPreferences preferences = getSharedPreferences("url", MODE_PRIVATE);
        return preferences.getString(this.getClass().getName(), defaultUrl);
    }

    private void saveUrl(String url) {
        SharedPreferences preferences = getSharedPreferences("url", MODE_PRIVATE);
        preferences.edit().putString(this.getClass().getName(), url).commit();
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
                Log.d(TAG, "request success:");
                mHandler.postAtFrontOfQueue(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "start zoloz");
                        zlzFacade.start(request, new IZLZCallback() {
                            @Override
                            public void onCompleted(ZLZResponse response) {
                                showResponse(initResponse.transactionId, response);
                            }

                            @Override
                            public void onInterrupted(ZLZResponse response) {
                                showResponse(initResponse.transactionId, response);
                                Toast.makeText(MainActivity.this, "interrupted", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

    private void showResponse(final String flowId, ZLZResponse response) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Result")
                .setMessage(JSON.toJSONString(response)
                )
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
        IRequest request = generateRequest();
        String result = request.request(getUrl(), generateRequestData());
        return result;
    }

    protected IRequest generateRequest() {
        return new LocalRequest();
    }

    protected String getUrl() {
        EditText editText = findViewById(R.id.init_url);
        String url = editText.getText().toString();
        if (TextUtils.isEmpty(url)) {
            return getDefaultUrl();
        } else {
            saveUrl(url);
            return url;
        }
    }

    public String generateRequestData() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bizId", "test-bizId");
            jsonObject.put("userId", "216610000001376836453");
            jsonObject.put("sceneCode", "registration");
            jsonObject.put("metaInfo", ZLZFacade.getMetaInfo(this));
            JSONObject envData = new JSONObject();
            envData.put("envName", "default");
            jsonObject.put("envData", envData);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

}
