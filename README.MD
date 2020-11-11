## ZOLOZ iOS Example

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

#### Example useage

1. open the application, Fill the inbox of "HOST" with "http://lan_ip:lan_port"，the `ip` and the `port` are values get from [server example](https://github.com/zoloz-pte-ltd/zoloz-integration-examples/tree/master/basic/realid-native) .

   ![example homepage](https://gw.alicdn.com/tfs/TB1ZKLS1FY7gK0jSZKzXXaikpXa-818-730.png)

2. click "start zoloz" button to start the eKYC flow.



### SDK Integration

1. Add private maven to your build.gradle:

```groovy
maven {
    url  "https://dl.bintray.com/zolozclient/release"
}
```

2.  In your module's (app-level) Gradle file (usually app/build.gradle), add Zoloz sdk as dependecy.

```groovy
implementation 'com.zoloz.android.build:zoloz-kit:1.0.0'
implementation "com.squareup.okio:okio:1.7.0@jar"
implementation "com.alibaba:fastjson:1.1.68.android"
```

3. Start the SDK

   To start SDK, some runtime configurations need to be obtained from server first, refer to step 4 in the [sequence diagram](https://docs.zoloz.com/zoloz/saas/docs/fgnasg#WY0gq), and in this step, meta info must be sent to server.

   To get the meta info from our SDK, you can simply do this:

   ```java
   String metaInfo = ZLZFacade.getMetaInfo(applicationContext);
   ```

   If server initialized the  eKYC flow successfully, and returned with the configurations clientCfg, you can then construct a ZLZRequest object and start the SDK in this way. You need to implement the callback functions to handle the eKYC outcome.

   ```objective-c
   public ZLZRequest createZLZRequest(String clientCfg) {
     ZLZRequest request = new ZLZRequest();
     request.bizConfig = new HashMap<>();
     request.bizConfig.put(ZLZConstants.CONTEXT, this);
     request.bizConfig.put(ZLZConstants.PUBLIC_KEY, publickey);
     request.bizConfig.put(ZLZConstants.LOCALE, locale);
     request.zlzConfig = clientCfg;
     return request;
   }

   public void startZolozSDK(ZLZRequest request) {
     ZLZFacade.getInstance().start(request, new IZLZCallback() {
         @Override
         public void onCompleted(ZLZResponse response) {
           // to be implemented by you
           // will be called by our SDK when eKYC is completed.
         }

         @Override
         public void onInterrupted(ZLZResponse response) {
           // to be implemented by you
           // will be called by our SDK when eKYC is interrupted.
         }
       });
   }
   ```

