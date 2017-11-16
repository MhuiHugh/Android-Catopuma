package hugh.catopuma;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.TimeZone;

import hugh.catopuma.utils.LogUtil;
import hugh.catopuma.utils.ServiceAPI;

/**
 * Created by Hugh on 2016/5/7.
 */
public class MyApplication extends Application {

    private static MyApplication app;
    //api
    private ServiceAPI serviceAPI;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.v("onCreate()");
        init();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtil.w("onLowMemory()");
    }

    //--------------------------------------

    /**
     * 饿汉式线程安全单例模式
     *
     * @return
     */
    public static MyApplication getInstance() {
        return app;
    }

    //----------------------------------------------

    /**
     * init
     */
    private void init() {
        app = this;
        //自动布局初始化
        AutoLayoutConifg.getInstance().useDeviceSize().init(this);
        //设置时区为北京
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        //友盟统计初始化
        //UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    /**
     * 获取serviceAPI
     */
    public ServiceAPI getServiceAPI() {
        if (serviceAPI == null) {
            serviceAPI = new ServiceAPI();
        }
        return serviceAPI;
    }

    /**
     * 初始化友盟分享
     */
    private void initUMShare(Application app) {
        PlatformConfig.setQQZone("1106175801", "vZaCtV1WdELyQXgH");
        PlatformConfig.setWeixin("wxa3feab9b3f465335", " 9919a3ffad61cc793b1c7017fcd3e1db");
        PlatformConfig.setSinaWeibo("2270018795", "a42647c1fb01e083b94dd6336c1211a1", "http://www.ierfa.cn");
        UMShareAPI.get(app);
    }

}
