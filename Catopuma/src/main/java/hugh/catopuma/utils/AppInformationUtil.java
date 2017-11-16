package hugh.catopuma.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import hugh.catopuma.MyApplication;

/**
 * Created by Hugh on 2016/5/27.
 */
public class AppInformationUtil {

    /**
     * 获取版本名，展示给用户的
     *
     * @return
     */
    public static String getVersionName() {
        return getPackageInfo(MyApplication.getInstance()).versionName;
    }

    /**
     * 获取代码版本号
     *
     * @return
     */
    public static int getVersionCode() {
        return getPackageInfo(MyApplication.getInstance()).versionCode;
    }

    /**
     * 获取包信息
     *
     * @param context
     * @return
     */
    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    /**
     * 获取程序debug状态
     *
     * @return
     */
    public static boolean isApkDebugable() {
        try {
            ApplicationInfo info = MyApplication.getInstance().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 获取友盟渠道信息
     *
     * @return
     */
    public static String getUMChannel() {
        try {
            ApplicationInfo appInfo = MyApplication.getInstance().getPackageManager()
                    .getApplicationInfo(MyApplication.getInstance().getPackageName(),
                            PackageManager.GET_META_DATA);
            System.out.print("H0 channel:" + appInfo.metaData.getString("UMENG_CHANNEL"));
            Log.e("H0 channel:", appInfo.metaData.getString("UMENG_CHANNEL"));
            return appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

}
