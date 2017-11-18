package hugh.catopuma.constant;

import hugh.catopuma.utils.DoubleKeyValueMap;

/**
 * 程序配置，静态变量
 * Created by on 2016/8/4
 */
public class Constant {

    //---------Memory
    /**
     * 内存数据
     */
    private static final DoubleKeyValueMap data = new DoubleKeyValueMap();

    public static final String DATA_KEY = "data";

    public static DoubleKeyValueMap getData() {
        return data;
    }

    //token
    public static String TOKEN = "";

    //----------逻辑控制相关
    //app是否运行状态
    public static boolean appRuning = false;
    //用户是否已登录
    public static boolean userLoged = false;

    //---友盟分享内容设置
    //分享内容标题
    public static String SHARE_TITLE = "";
    //分享正文内容
    public static String SHARE_CONTENT = "";
    //分享链接
    public static String SHARE_URL = "#";
    //分享图片
    public static String SHARE_PICURL = "";
    //用户邀请码
    public static String SHARE_PROMOTE = "";

}
