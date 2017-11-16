package hugh.catopuma.constant;

/**
 * 常量配置
 */
public class Config {

    //---------------SharedPreferences配置
    /**
     * SharedPreferences 文件名
     */
    public static final String KEY_SHARED_PREFERENCES_NAME = "Data";

    //---------网络请求状态
    //请求成功
    public static final int WEB_STATUS_SUCCESS = 200;
    //请求取消
    public static final int WEB_STATUS_CANCLE = 300;
    //请求失败
    public static final int WEB_STATUS_ERROR = 400;

    //--------------Final String
    //请求平台
    public static final String WEB_PLATFROM = "Android";

    //--------------Action
    /**
     * 退出所有Activity
     */
    public static final String ACT_EXIT_APP = "actExitApp";

    //---------------Other
    /**
     * 判断键盘显示隐藏状态，高度低于300为隐藏
     */
    public static final int KEYBOARD_HIDE_HEIGHT = 300;

}
