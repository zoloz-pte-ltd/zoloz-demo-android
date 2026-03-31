package com.zoloz.saas.example.composeapp

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alibaba.fastjson2.JSON
import com.ap.zoloz.hummer.api.IZLZCallback
import com.ap.zoloz.hummer.api.ZLZConstants
import com.ap.zoloz.hummer.api.ZLZFacade
import com.ap.zoloz.hummer.api.ZLZRequest
import com.ap.zoloz.hummer.api.ZLZResponse
import com.zoloz.builder.BuildConfig
import com.zoloz.saas.example.composeapp.ui.theme.ZolozdemoandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZolozDemo()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun ZolozDemo() {
        ZolozdemoandroidTheme {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(stringResource(R.string.app_name)) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = Color.White,
                        ),
                    )
                }
            ) { innerPadding ->
                val context = LocalContext.current
                val coroutineScope = rememberCoroutineScope()
                val host = rememberTextFieldState("")
                val api = rememberTextFieldState("")
                val doc = rememberTextFieldState("")
                val level = rememberTextFieldState("")

                LaunchedEffect(Unit) {
                    val preference = context.dataStore.data.first()
                    host.setTextAndPlaceCursorAtEnd(
                        preference.get(stringPreferencesKey("host")) ?: "http://lan_ip:lan_port"
                    )
                    api.setTextAndPlaceCursorAtEnd(
                        preference.get(stringPreferencesKey("api")) ?: "/api/realid/initialize"
                    )
                    doc.setTextAndPlaceCursorAtEnd(
                        preference.get(stringPreferencesKey("doc")) ?: "00000001003"
                    )
                    level.setTextAndPlaceCursorAtEnd(
                        preference.get(stringPreferencesKey("level")) ?: "REALID0002"
                    )
                }

                Column(
                    Modifier
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    ZolozDemoContent("HOST:", host)
                    ZolozDemoContent("API:", api)
                    ZolozDemoContent("DOC:", doc)
                    ZolozDemoContent("LEVEL:", level)
                    Spacer(Modifier.height(16.dp))
                    ElevatedButton(onClick = {
                        coroutineScope.launch {
                            context.dataStore.updateData {
                                it.toMutablePreferences().also { preferences ->
                                    preferences[stringPreferencesKey("host")] = host.text.toString()
                                    preferences[stringPreferencesKey("api")] = api.text.toString()
                                    preferences[stringPreferencesKey("doc")] = doc.text.toString()
                                    preferences[stringPreferencesKey("level")] =
                                        level.text.toString()
                                }
                            }

                            startZoloz(
                                context,
                                coroutineScope,
                                host.text.toString(),
                                api.text.toString(),
                                doc.text.toString(),
                                level.text.toString()
                            )
                        }
                    }) { Text("START ZOLOZ") }
                }
            }
        }
    }


    @Composable
    fun ZolozDemoContent(title: String, textFieldState: TextFieldState) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Text(title, modifier = Modifier.width(60.dp))
            Spacer(Modifier.width(8.dp))
            TextField(
                textFieldState, modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
                )
            )
        }

    }


    suspend fun startZoloz(
        context: Context,
        coroutineScope: CoroutineScope,
        host: String,
        api: String,
        doc: String,
        level: String
    ) {
        val response = mockInitRequest(context, host, api, doc, level)
        if (response == null) {
            Toast.makeText(
                context,
                "network exception, please try again later.",
                Toast.LENGTH_SHORT
            )
                .show()
            return
        }

        val zlzFacade = ZLZFacade.getInstance()
        val request = ZLZRequest()
        request.zlzConfig = response.clientCfg
        request.bizConfig.put(ZLZConstants.CONTEXT, context)
        request.bizConfig.put(ZLZConstants.CHAMELEON_CONFIG_PATH, "config_realId.zip")
        request.bizConfig.put(ZLZConstants.LOCALE, "en")
        println("request success:")

        zlzFacade.start(request, object : IZLZCallback {
            override fun onCompleted(p0: ZLZResponse?) {
                coroutineScope.launch { checkResult(context, host, api, response.transactionId) }
            }

            override fun onInterrupted(p0: ZLZResponse?) {
                coroutineScope.launch {
                    showResponse(
                        context,
                        response.transactionId,
                        com.alibaba.fastjson.JSON.toJSONString(response)
                    )
                    Toast.makeText(context, "interrupted", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    suspend fun checkResult(
        context: Context,
        host: String,
        api: String,
        transactionId: String
    ) {

        val response = withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val requestBody = JSON.toJSONString(
                    CheckResultRequestBody(transactionId)
                ).toRequestBody("application/json".toMediaTypeOrNull())
                val request =
                    Request.Builder().url("$host$api".replace("initialize", "checkresult"))
                        .post(requestBody).build()
                val newCall = client.newCall(request)
                val response = newCall.execute()
                response.body?.toString()
            } catch (e: Exception) {
                println(e)
                null
            }
        }

        if (response != null) {
            showResponse(context, transactionId, response)
        }
    }

    suspend fun showResponse(context: Context, transactionId: String, response: String) {
        val alertDialog: AlertDialog = AlertDialog.Builder(context)
            .setTitle("Check Result")
            .setMessage(response)
            .setNegativeButton("Ok", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    val myClipboard =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val myClip: ClipData
                    val text: String? = transactionId
                    myClip = ClipData.newPlainText("text", text)
                    myClipboard.setPrimaryClip(myClip)
                    dialog.dismiss()
                }
            })
            .create()
        alertDialog.setCancelable(false)
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
    }

    suspend fun mockInitRequest(
        context: Context,
        host: String,
        api: String,
        doc: String,
        level: String
    ): InitResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val requestBody = JSON.toJSONString(
                    InitRequestBody(
                        metaInfo = ZLZFacade.getMetaInfo(context),
                        serviceLevel = level,
                        docType = doc,
                        v = BuildConfig.VERSION_NAME
                    )
                ).toRequestBody("application/json".toMediaTypeOrNull())
                val request = Request.Builder().url("$host$api").post(requestBody).build()
                val newCall = client.newCall(request)
                val response = newCall.execute()
                val str = response.body?.string()
                com.alibaba.fastjson.JSON.parseObject(str, InitResponse::class.javaObjectType)
            } catch (e: Exception) {
                println(e)
                null
            }
        }
    }
}

data class InitRequestBody(
    val metaInfo: String,
    val serviceLevel: String,
    val docType: String,
    val v: String
)

data class InitResponse(val clientCfg: String, val transactionId: String)

data class CheckResultRequestBody(val transactionId: String)