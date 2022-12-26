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

-keep class okio.**{
    <fields>;
    <methods>;
}
-dontwarn com.zoloz.**
-keep class com.zoloz.zhub.**{
  <fields>;
   <methods>;
}
-keep class com.alipay.zoloz.**{
   <fields>;
   <methods>;
}
-keep class com.alipay.android.phone.zoloz.**{
   <fields>;
   <methods>;
}
-keep class com.alipay.biometrics.**{
   <fields>;
   <methods>;
}
-keep class com.alipay.bis.**{
   <fields>;
   <methods>;
}
-keep class com.alipay.mobile.security.**{
   <fields>;
   <methods>;
}
-keep class com.ap.zoloz.**{
   <fields>;
   <methods>;
}
-keep class com.ap.zhubid.endpoint.**{
   <fields>;
   <methods>;
}
-keep class com.zoloz.android.phone.zdoc.**{
   <fields>;
   <methods>;
}
-keep class zoloz.ap.com.toolkit.**{
   <fields>;
   <methods>;
}
-keep class com.zoloz.builder.** {
   <fields>;
   <methods>;
}