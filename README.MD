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
   implementation "com.zoloz.android.build:zolozkit:${zolozKitVersion}"
   implementation "com.squareup.okio:okio:1.17.4@jar"
   implementation 'com.alibaba:fastjson:2.0.21.android'
   ```

3. Get meta info

   ```groovy
   String metaInfo = ZLZFacade.getMetaInfo(applicationContext);
   ```

4. Send the meta info to your biz server, and call RealId intialize API with meta info from your server to obtain the runtime configurations.

5. Construct `ZLZRequest` object with `clientCfg` returned from RealId server:

   ```java
   ZLZRequest request = new ZLZRequest();
   request.bizConfig = new HashMap<>();
   request.bizConfig.put(ZLZConstants.CONTEXT, this);
   request.bizConfig.put(ZLZConstants.LOCALE, locale);
   request.zlzConfig = clientCfg;
   return request;
   ```

6. Start the ZOLOZ SDK with  `ZLZRequest`  and you need to implement the callback functions to handle the eKYC outcome. 

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

   > The eKYC outcome only contains a simple code demostrating the status of the eKYC process, you have to call RealId checkResult API to get detailed information of the eKYC process.

