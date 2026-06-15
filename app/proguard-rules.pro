-keep class com.bussimulatorzim.game.** { *; }
-keep class com.bussimulatorzim.game.data.** { *; }
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
