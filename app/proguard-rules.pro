# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
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
-keepattributes *Annotation*
-keepattributes Signature
-dontwarn com.squareup.okhttp.*
-dontwarn rx.**
-dontwarn javax.xml.stream.**
-dontwarn com.google.appengine.**

-repackageclasses
-allowaccessmodification
-useuniqueclassmembernames
-keeppackagenames doNotKeepAThing

-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

#*************************************** GSON (START) *********************************************#
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }
-keep public class com.google.gson.** {public private protected *;}
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
#*************************************** GSON (END) *********************************************#



#************************************* OKHTTP3 (START) *******************************************#
-dontwarn okhttp3.**
-dontwarn org.codehaus.mojo.animal_sniffer.*
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
#************************************* OKHTTP3 (END) ********************************************#


# JSONObject
-keep class org.json.** { *; }
-keep class com.google.code.gson.** { *;}
-keep class com.google.gson.** { *; }
-keep public class com.google.gson.** {public private protected *;}
-keepattributes Signature


#AndroidX rules
-keep class com.google.android.material.** { *; }

-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**

-dontwarn androidx.**
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

#Databinding rules
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }
-keep class com.sendbizcard.models.** { *; }