# for R file for fonts
-keepattributes InnerClasses
 -keep class **.R
 -keep class **.R$* {
    <fields>;
}

-keepclassmembers class **.R$layout {
    public static <fields>;
}