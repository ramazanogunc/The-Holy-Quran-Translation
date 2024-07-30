# for R file for fonts
-keepattributes InnerClasses
 -keep class **.R
 -keep class **.R$* {
    <fields>;
}

-keepclassmembers class **.R$layout {
    public static <fields>;
}

# For firebase feedback model
-keepattributes Signature
-keepclassmembers class com.ramo.quran.model.Feedback {
    *;
}

-dontwarn com.ramo.core.VbAndVmFragment
-dontwarn com.ramo.core.ViewBindingActivity
-dontwarn com.ramo.core.ViewBindingFragment
-dontwarn com.ramo.core.ext.SharedPreferencesExtKt