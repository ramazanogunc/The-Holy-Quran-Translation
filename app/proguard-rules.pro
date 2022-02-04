# for R file for fonts
-keepattributes InnerClasses
 -keep class **.R
 -keep class **.R$* {
    <fields>;
}

-keepclassmembers class **.R$layout {
    public static <fields>;
}

-keepclassmembers class ** implements androidx.viewbinding.ViewBinding {
    public static ** bind(***);
    public static ** inflate(***);
}

-keep class * implements androidx.viewbinding.ViewBinding {
    public static *** bind(android.view.View);
    public static *** inflate(android.view.LayoutInflater);
    public static *** inflate(android.view.LayoutInflater, android.view.ViewGroup, boolean);
}