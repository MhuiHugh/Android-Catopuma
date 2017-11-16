package hugh.catopuma.constant;

/**
 * Created by Hugh on 2017/10/25.
 */

public class Api {

    //------Local
    // public static final String BASE_URL="http://192.168.13.58";

    //------Dev

    //-------Test

    public static final String BASE_URL = "http://ierfa.vicp.io/app/";
    //------Product

    //public static final String BASE_URL = "http://172.16.10.230:8080/app/";//Api
    //public static final String BASE_URL = "http://1m857464n9.imwork.net/app/";
    //public static final String BASE_URL = "http://android.ierfa.cn/";

    //--------------------------
    public static final String TOKEN = BASE_URL + "login/token";//获取token get
    public static final String APP_SEND_MESSAGE = BASE_URL + "regist/appSendMessage";//获取短信 post
    public static final String APP_SMS_SAVE = BASE_URL + "regist/appSMSSave";//注册 post

}
