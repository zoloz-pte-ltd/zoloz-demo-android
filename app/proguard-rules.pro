# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#-dontshrink

-keep class com.zoloz.saas.example.** { *; }

-dontwarn com.zoloz.**

-keep class okio.** { *; }
-keep class com.alibaba.fastjson.** { *; }
-keep class com.alibaba.fastjson2.** { *; }
-keep class com.zoloz.zhub.** { *; }
-keep class com.alipay.zoloz.** { *; }
-keep class com.zoloz.zeta.** { *; }
-keep class com.zoloz.zcore.facade.common.** { *; }
-keep class com.alipay.android.phone.zoloz.** { *; }
-keep class com.alipay.biometrics.** { *; }
-keep class com.alipay.bis.** { *; }
-keep class com.alipay.mobile.security.** { *; }
-keep class com.ap.zoloz.** { *; }
-keep class com.ap.zhubid.endpoint.** { *; }
-keep class com.zoloz.android.phone.zdoc.** { *; }
-keep class zoloz.ap.com.toolkit.** { *; }
-keep class com.zoloz.builder.** { *; }
-keep class com.ant.phone.xmedia.** { *; }
-keep class com.alipay.alipaysecuritysdk.** { *; }
-keep class com.alipay.blueshield.** { *; }
-keep class com.alipay.deviceid.** { *; }
-keep class com.alipay.edge.** { *; }
-keep class com.alipay.softtee.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class face.security.device.api.** {*;}
-dontwarn face.security.device.api.**