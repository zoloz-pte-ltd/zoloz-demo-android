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

-keep class com.alipay.zbase.demo.init.** { *; }
-keepclasseswithmembers class com.alipay.zbase.demo.init.EkycInitResponse

#-optimizationpasses 5
#-dontusemixedcaseclassnames
#-dontskipnonpubliclibraryclasses
#-dontpreverify
#-ignorewarnings
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#system
-keep public class * extends android.app.Activity{
    !private <fields>;
    !private <methods>;
}
-keep public class * extends android.app.Application{
    !private <fields>;
    !private <methods>;
}
-keep public class * extends android.app.Service{
    !private <fields>;
    !private <methods>;
}
-keep public class * extends android.view.View{
    !private <fields>;
    !private <methods>;
}
-keep public class * extends android.content.BroadcastReceiver{
    !private <fields>;
    !private <methods>;
}
-keep public class * extends android.content.ContentProvider{
    !private <fields>;
    !private <methods>;
}
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keep class * extends android.os.IInterface{
    !private <fields>;
    !private <methods>;
}
-keep class * extends android.os.Binder{
    !private <fields>;
    !private <methods>;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep public class com.android.vending.licensing.ILicensingService

#root class, don't know any
#-keepnames public class *

-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses

-keepclasseswithmembernames class * {
    native <methods>;
}

#classes may be referenced in xml
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class * extends java.lang.annotation.Annotation { *; }


-keep public class com.alipay.mobile.security.bio.api.**{
     public <fields>;
    !private <methods>;
}

-keep public class  com.alipay.mobile.security.bio.widget.**{
	public <fields>;
    public <methods>;
}

-keep public class com.alipay.mobile.security.bio.**{
   public <fields>;
   public <methods>;
}

-keep public class com.alipay.mobile.security.bio.service.BioAppDescription{
    *;
}

-keep public class com.alipay.mobile.security.bio.workspace.BioTransfer{
    !private <fields>;
    !private <methods>;
}


-keep class * extends com.alipay.mobile.security.bio.workspace.BioFragmentContainer{
    !private <fields>;
    !private <methods>;
}

-keep class * extends com.alipay.mobile.security.bio.workspace.BioFragment{
    !private <fields>;
    !private <methods>;
}

-keep public class com.alipay.mobile.security.bio.service.impl.BioStoreServiceImpl{
    !private <fields>;
    !private <methods>;
}


-keep class com.alipay.bis.common.service.facade.gw.**{
    *;
}

-keep class com.zoloz.bis.common.service.facade.gw.**{
    *;
}

-keep class com.zoloz.android.phone.zdoc.**{
    *;
}

-keep class com.alipay.bis.common.service.facade.gw.upload.**{
    *;
}

-keep class com.alipay.mobile.security.bio.workspace.ProtocolConfig{
    *;
}

-keep class com.alipay.bis.upload.gw.**{
    *;
}
-keep class com.alipay.bis.upload.model.**{
    *;
}

-assumenosideeffects class android.util.Log {
	public static boolean isLoggable(java.lang.String, int);
	public static int println(...);
	public static int v(...);
	public static int i(...);
	public static int w(...);
	public static int d(...);
	public static int e(...);
	public static int wtf(...);
}
-assumenosideeffects class com.alipay.mobile.command.util.CommandLogUtil {
	public static void logD(...);
	public static void logE(...);
	public static void logV(...);
}
-assumenosideeffects class com.alipay.mobile.quinox.utils.LogUtil {
	public static int v(...);
	public static int i(...);
	public static int w(...);
	public static int d(...);
	public static int e(...);
}

#export-patch start
-keep class android.net.http.**{
	!private <fields>;
    !private <methods>;
}

-keep class android.util.**{
	!private <fields>;
    !private <methods>;
}

-keep class android.webkit.**{
	!private <fields>;
    !private <methods>;
}

-keep class com.alipay.mobile.common.** {
	!private <fields>;
    !private <methods>;
}

-keep class com.squareup.wire.** {
    *;
}

-keep class okio.** {
    *;
}

-keep  class com.alipay.biometrics.ui.widget.** { *; }

-keep class com.alipay.android.phone.mobilecommon.biometric.BioMetricValve {
    *;
}

-keep class * implements com.alipay.biometrics.common.proguard.INotProguard {
    !private <fields>;
    !private <methods>;
}

# keep annotated by NotProguard
-keep @com.alipay.biometrics.common.annotation.NotProguard class * {
    !private <fields>;
    !private <methods>;
}

-keepclassmembers class * {
    @com.alipay.biometrics.common.annotation.NotProguard <fields>;
}

-keepclassmembers class * {
    @com.alipay.biometrics.common.annotation.NotProguard <methods>;
}

#zim
-keep public class com.alipay.mobile.security.zim.api.**{
    public <fields>;
    public <methods>;
}

-keep class com.alipay.mobile.security.zim.biz.ZIMFacadeBuilder {
  !private <fields>;
   !private <methods>;
}

-keep public class com.alipay.mobile.security.zim.plugin.**{
    public <fields>;
    public <methods>;
}

-keep class com.alipay.android.phone.zoloz.h5.ZolozValve {
    *;
}

-keep class com.alipay.android.phone.zoloz.h5.ZIMIdentityPlugin {
    public <fields>;
    public <methods>;
}

-keep class com.alipay.android.phone.zoloz.rpc.HKRpcService {
    *;
}

-keep class com.alipay.android.phone.zoloz.logging.HkLoggingService {
    *;
}

-keep class com.alipay.android.phone.zoloz.apsecurity.HKSecurityService {
    *;
}

#system
-keep public class * extends android.app.Activity{
    !private <fields>;
    !private <methods>;
}
-keep public class * extends android.app.Application{
    !private <fields>;
    !private <methods>;
}
-keep public class * extends android.app.Service{
    !private <fields>;
    !private <methods>;
}
-keep public class * extends android.view.View{
    !private <fields>;
    !private <methods>;
}
-keep public class * extends android.content.BroadcastReceiver{
    !private <fields>;
    !private <methods>;
}
-keep public class * extends android.content.ContentProvider{
    !private <fields>;
    !private <methods>;
}
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keep class * extends android.os.IInterface{
    !private <fields>;
    !private <methods>;
}
-keep class * extends android.os.Binder{
    !private <fields>;
    !private <methods>;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep public class com.android.vending.licensing.ILicensingService

#root class, don't know any
#-keepnames public class *

-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses

#face module
-keepclasseswithmembernames class * {
    native <methods>;
}

#classes may be referenced in xml
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#logging
-keepclassmembers class * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * extends java.lang.annotation.Annotation { *; }

#face
-keep class com.alipay.mobile.security.faceauth.algorithm.LiveFaceDetect{
     public native <methods>;
}
-keep class com.alipay.mobile.security.faceauth.graph.Jpeg{
     public native <methods>;
}
-keep public class com.alipay.mobile.security.faceauth.api.**{
     public <fields>;
    !private <methods>;
}
-keep public class com.alipay.fc.custprod.**{
    *;
}
-keep public class com.alipay.mobile.security.faceauth.service.**{
    *;
}
-keep public class com.alipay.mobile.security.faceauth.**{
    public <fields>;
    public <methods>;
}
-keep public class com.alipay.mobile.security.faceauth.model.InspectorItem{
    *;
}
-keep class com.megvii.livenessdetection.**{
    *;
}

-keep public class com.oppo.camera.RotationUtils{
    *;
}
-keep public class com.alipay.fc.bid.common.service.facade.gw.alive.**{
    *;
}
-keep public class com.alipay.bid.common.service.facade.gw.**{
	*;
}
-keep public class com.alipay.bis.core.pbmodel.**{
	*;
}
-keep public class com.alipay.bis.core.protocol.**{
	*;
}

-keep public class com.alipay.mobile.security.bio.api.**{
     public <fields>;
    !private <methods>;
}

-keep public class  com.alipay.mobile.security.bio.widget.**{
	public <fields>;
    public <methods>;
}

-keep public class com.alipay.mobile.security.bio.**{
    public <fields>;
   public <methods>;
}

-keep public class com.alipay.mobile.security.bio.service.BioAppDescription{
    *;
}

-keep public class com.alipay.mobile.security.bio.eye.Config{
	*;
}

-keep public class com.alipay.mobile.security.bio.workspace.BioTransfer{
    !private <fields>;
    !private <methods>;
}


-keep class * extends com.alipay.mobile.security.bio.workspace.BioFragmentContainer{
    !private <fields>;
    !private <methods>;
}

-keep class * extends com.alipay.mobile.security.bio.workspace.BioFragment{
    !private <fields>;
    !private <methods>;
}

-keep public class com.alipay.mobile.security.bio.service.impl.BioStoreServiceImpl{
    !private <fields>;
    !private <methods>;
}

-keep public class com.alipay.mobile.security.faceauth.ui.uniform.**{
    public <fields>;
    public <methods>;
}

-keep class android.net.http.**{
	!private <fields>;
    !private <methods>;
}

-keep class android.webkit.**{
	!private <fields>;
    !private <methods>;
}

-keep class com.alipay.bis.common.service.facade.gw.**{
    *;
}

-keep class com.alipay.android.phone.falcon.**{
    *;
}

-keep class com.alipay.bis.common.service.facade.gw.upload.**{
    *;
}
-keep class com.alipay.mobile.security.faceeye.workspace.EyeRemoteConfig{
    *;
}
-keep public class com.alipay.mobile.security.faceeye.protocol.**{
    *;
}
-keep class com.alipay.mobile.security.faceeye.workspace.EyeDRMConfig{
    *;
}
-keep class com.alipay.mobile.security.faceeye.workspace.SceneEnv{
    *;
}
-keep class com.alipay.mobile.security.bio.workspace.ProtocolConfig{
    *;
}

-keep class com.alipay.bis.upload.gw.**{
    *;
}
-keep class com.alipay.bis.upload.model.**{
    *;
}
-keep class com.alipay.mobile.security.faceauth.FaceDetectType{
    *;
}

#falcon
-keep class com.alipay.android.phone.falcon.manager.FalconIdcardApp{
    *;
}
-keep class com.alipay.android.phone.falcon.cardmanager.**{*;}

#rds
-keep class com.alipay.mobile.security.senative.***{
    *;
}
-keep class com.alipay.mobile.security.utils.***{
    *;
}
-keep class com.alipay.rdssecuritysdk.***{
    *;
}
-keep class com.alipay.security.mobile.module.***{
    *;
}

-keep  class com.alipay.apmobilesecuritysdk.otherid.** { *; }

#keep all cert api class
-keep  class com.alipay.security.mobile.cert.** { *; }

#keep all cert sdk class
-keep  class com.ccit.SecureCredential.** { *; }


-assumenosideeffects class android.util.Log {
	public static boolean isLoggable(java.lang.String, int);
	public static int println(...);
	public static int v(...);
	public static int i(...);
	public static int w(...);
	public static int d(...);
	public static int e(...);
	public static int wtf(...);
}
-assumenosideeffects class com.alipay.mobile.command.util.CommandLogUtil {
	public static void logD(...);
	public static void logE(...);
	public static void logV(...);
}
-assumenosideeffects class com.alipay.mobile.quinox.utils.LogUtil {
	public static int v(...);
	public static int i(...);
	public static int w(...);
	public static int d(...);
	public static int e(...);
}

#export-patch start
-keep class android.net.http.**{
	!private <fields>;
    !private <methods>;
}

-keep class android.util.**{
	!private <fields>;
    !private <methods>;
}

-keep class android.webkit.**{
	!private <fields>;
    !private <methods>;
}

-keep class com.alipay.mobile.common.** {
	!private <fields>;
    !private <methods>;
}

-keep  class com.alipay.biometrics.ui.widget.** { *; }

-keep class com.alipay.android.phone.mobilecommon.biometric.BioMetricValve {
    *;
}

-keepnames class * implements com.alipay.biometrics.common.proguard.INotProguard

# keep annotated by NotProguard
-keep @com.alipay.biometrics.common.annotation.NotProguard class * {
    !private <fields>;
    !private <methods>;
}

-keepclassmembers class * {
    @com.alipay.biometrics.common.annotation.NotProguard <fields>;
}

-keepclassmembers class * {
    @com.alipay.biometrics.common.annotation.NotProguard <methods>;
}

#zim
-keep public class com.alipay.mobile.security.zim.api.**{
    public <fields>;
    public <methods>;
}

-keep class com.alipay.mobile.security.zim.biz.ZIMFacadeBuilder {
  !private <fields>;
   !private <methods>;
}

-keep class com.alipay.zoloz.toyger.bean.ToygerMetaInfo {
    !private <fields>;
    !private <methods>;
}

-keep class com.alipay.zoloz.toyger.algorithm.** { *; }

-keep class com.zoloz.ekyc.hongkong.rpc.HkRpcService {
    !private <fields>;
    !private <methods>;
}

-keep public class com.alipay.mobile.security.zim.plugin.**{
    public <fields>;
    public <methods>;
}

-keep class com.zoloz.ekyc.hongkong.h5.ZIMIdentityPlugin {
    !private <fields>;
    !private <methods>;
}

-keep public class com.zoloz.ekyc.hongkong.solution.api.**{
    public <fields>;
    public <methods>;
}

-keep public class com.zoloz.ekyc.hongkong.solution.gateway.**{
    public <fields>;
    public <methods>;
}


-keep  class com.alipay.biometrics.ui.widget.** { *; }

-keep class com.alipay.android.phone.mobilecommon.biometric.BioMetricValve {
    *;
}

-keep class * implements com.alipay.biometrics.common.proguard.INotProguard {
    !private <fields>;
    !private <methods>;
}

# keep annotated by NotProguard
-keep @com.alipay.biometrics.common.annotation.NotProguard class * {
    !private <fields>;
    !private <methods>;
}

-keepclassmembers class * {
    @com.alipay.biometrics.common.annotation.NotProguard <fields>;
}

-keepclassmembers class * {
    @com.alipay.biometrics.common.annotation.NotProguard <methods>;
}

#zim
-keep public class com.alipay.mobile.security.zim.api.**{
    public <fields>;
    public <methods>;
}

-keep class com.alipay.mobile.security.zim.biz.ZIMFacadeBuilder {
  !private <fields>;
   !private <methods>;
}

-keep class com.alipay.zoloz.toyger.bean.ToygerMetaInfo {
    !private <fields>;
    !private <methods>;
}

-keep class com.alipay.zoloz.toyger.bean.ZFaceMetaInfo {
    !private <fields>;
    !private <methods>;
}

-keep class com.alipay.zoloz.toyger.algorithm.** { *; }

-keep public class com.alipay.mobile.security.zim.plugin.**{
    public <fields>;
    public <methods>;
}

#EKYC
-keep class com.ap.zoloz.ekyc.dana.h5.ZIMIdentityPlugin {
    !private <fields>;
    !private <methods>;
}
-keep class com.ap.zoloz.ekyc.dana.h5.ZIMFoundationPlugin {
    !private <fields>;
    !private <methods>;
}

-keep public class com.alipay.zoloz.zhubid.endpoint.gateway.**{
    public <fields>;
    public <methods>;
}

#debug tool
-keep class com.zoloz.android.debug.provider.** {
*;
}
-keep public class * extends android.content.ContentProvider
-keep public class * extends LocalService

#toolkit
-keep class  zoloz.ap.com.toolkit.ui.** {*;}
-keep class com.ap.zhubid.endpoint.gateway.facade.EkycGwFacade

#class invoke rpc impl
-keep class * implements java.lang.reflect.InvocationHandler

-keep class com.ap.zoloz.hummer.api.** {*;}
-keep class com.zoloz.builder.EkycGwFacadeV2{*;}
-keep class com.ap.zhubid.endpoint.gateway.facade.EkycGwFacade{*;}


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
#class invoke rpc impl
-keep class * implements java.lang.reflect.InvocationHandler

-keep class com.ap.zoloz.hummer.api.** {*;}
-keep class com.zoloz.builder.EkycGwFacadeV2{*;}
-keep class com.ap.zhubid.endpoint.gateway.facade.EkycGwFacade{*;}

# keep annotated by NotProguard
-keep @com.alipay.biometrics.common.annotation.NotProguard class * {
    !private <fields>;
    !private <methods>;
}

-keepclassmembers class * {
    @com.alipay.biometrics.common.annotation.NotProguard <fields>;
}

-keepclassmembers class * {
    @com.alipay.biometrics.common.annotation.NotProguard <methods>;
}
-keepnames class * implements com.alipay.biometrics.common.proguard.INotProguard

-keep class com.alibaba.fastjson.JSON

-assumenosideeffects class android.util.Log{
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

-keep class com.zoloz.builder.plugin.* {*;}
-keep class com.alipay.ma.decode.*{*;}

#-keep @com.alibaba.taffy.bus.annotation.Subscribe public class *
#-keepclassmembers class * {
#    @com.alibaba.taffy.bus.annotation.Subscribe *;
#}

-keep class com.alibaba.taffy.** {
    *;
}