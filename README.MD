<!-- @formatter:off -->
## ZOLOZ Android Example

### Background

This repository provide an example to help customer to integrate ZOLOZ SDK with ease.



### Getting started

#### Prequisites

+ Android SDK and adb



#### Building from Source

```shell
git clone https://github.com/zoloz-pte-ltd/zoloz-demo-android
cd zoloz-demo-android
./gradlew :app:installDebug
```

### Example usage

1. Refer to [this example](https://github.com/zoloz-pte-ltd/zoloz-integration-examples/tree/master/basic/realid-native) to setup a minimum web server to act as your biz server bridging the demo app and the RealId service.

2. Open the application, fill the inbox of "HOST" with "http://lan_ip:lan_port"

![example homepage](https://gw.alicdn.com/tfs/TB1ZKLS1FY7gK0jSZKzXXaikpXa-818-730.png)

2. Click "START ZOLOZ" button to start the eKYC flow.



### SDK Integration

1. Add mavenCentral repository to your build.gradle:

   ```groovy
   mavenCentral()
   ```

2. In your module's (app-level) Gradle file (usually app/build.gradle), add Zoloz sdk as dependecy.

   ```groovy
   implementation "com.zoloz.android.build:zolozcore:${zolozKitVersion}"
   implementation "com.zoloz.android.build:doc:${zolozKitVersion}"
   implementation "com.zoloz.android.build:face:${zolozKitVersion}"
   implementation "com.zoloz.android.build:facebase:${zolozKitVersion}"
   implementation "com.zoloz.android.build:facedeepfake:${zolozKitVersion}"
   implementation "com.zoloz.android.build:xnn:${zolozKitVersion}"
   implementation "com.zoloz.android.build:faceguard:${zolozKitVersion}"
   implementation "com.zoloz.android.build:apsecurity:${zolozKitVersion}"
   implementation "com.zoloz.android.build:znfc:${zolozKitVersion}"  //Optional. nfc reader support
   implementation "com.squareup.okio:okio:1.17.4@jar"
   implementation 'com.alibaba:fastjson:2.0.21.android'
   ```

  important: The new SDK version internally depends on the libc++_shared.so library.
  You need to integrate this library into their projects yourselves; failing to do so may lead to runtime exceptions.

3. Get meta info

   ```groovy
   String metaInfo = ZLZFacade.getMetaInfo(applicationContext);
   ```

4. Send the meta info to your biz server, and call RealId intialize API with meta info from your server to obtain the runtime configurations.

5. Construct `ZLZRequest` object with `clientCfg` returned from RealId server:

   **Java:**
   ```java
   ZLZRequest request = new ZLZRequest();
   request.bizConfig = new HashMap<>();
   request.bizConfig.put(ZLZConstants.CONTEXT, this);
   request.bizConfig.put(ZLZConstants.LOCALE, locale);
   request.zlzConfig = clientCfg;
   return request;
   ```

   **Kotlin:**
   ```kotlin
   val request = ZLZRequest()
   request.bizConfig[ZLZConstants.CONTEXT] = context
   request.bizConfig[ZLZConstants.LOCALE] = locale
   request.bizConfig[ZLZConstants.CHAMELEON_CONFIG_PATH] = "config_realId.zip"
   request.zlzConfig = clientCfg
   ```

6. Start the ZOLOZ SDK with  `ZLZRequest`  and you need to implement the callback functions to handle the eKYC outcome.

   **Java:**
   ```java
   ZLZFacade.getInstance().start(request, new IZLZCallback() {
       @Override
       public void onCompleted(ZLZResponse response) {
       }

       @Override
       public void onInterrupted(ZLZResponse response) {
       }
   });
   ```

   **Kotlin:**
   ```kotlin
   ZLZFacade.getInstance().start(request, object : IZLZCallback {
       override fun onCompleted(response: ZLZResponse?) {
           // Handle completion
       }

       override fun onInterrupted(response: ZLZResponse?) {
           // Handle interruption
       }
   })
   ```

   > The eKYC outcome only contains a simple code demostrating the status of the eKYC process, you have to call RealId checkResult API to get detailed information of the eKYC process.

