# ProGuard & R8 Configuration for WeatherF1t

# --- Keep Network DTOs & Models ---
-keep class shahzod.projects.data.remote.** { *; }
-keepclassmembers class shahzod.projects.data.remote.** { *; }

# --- Retrofit ---
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers enum * { *; }
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# --- Gson ---
-keepattributes *Annotation*
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# --- Moshi ---
-keep class com.squareup.moshi.** { *; }
-keepclasseswithmembers class * {
    @com.squareup.moshi.* <fields>;
}

# --- Dagger / Hilt ---
-keep class com.google.dagger.** { *; }
-dontwarn com.google.dagger.**
-keep class dagger.** { *; }
-dontwarn dagger.**

# --- Kotlin Coroutines ---
-dontwarn kotlinx.coroutines.**
-keepclassmembers class kotlinx.coroutines.** { *; }

# --- Keep Line Numbers for Crash Logs ---
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
