package hugh.catopuma.utils;

import android.content.ClipboardManager;
import android.content.Context;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hugh.catopuma.MyApplication;

/**
 * 字符串处理，判断字符串是否为空；金额转换千分符，千分符转金额，天标收益计算；身份证，银行卡，手机号加*；字符串UTF-8转码；从html中提取字符串；
 * Created by on 2016/5/27.
 */
public class StringUtil {

    /**
     * 判断是否为空字符串
     *
     * @param src
     * @return
     */
    public static boolean isEmpty(String src) {
        return src == null || src.trim().length() == 0;
    }

    /**
     * 判断是否是空字符串(包括null, 长度为0, 只包含空格)
     *
     * @return
     */
    public static boolean isNotEmpty(String src) {
        return !isEmpty(src);
    }
    //---------------------------------------------------------------

    /**
     * 实现文本复制功能
     */
    public static void copy(String content) {
        //得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) MyApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    /**
     * 实现粘贴功能
     *
     * @return
     */
    public static String paste() {
        //得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) MyApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }

    //---------------------------------------------------------------------

    /**
     * 将字符转Double
     *
     * @param text
     * @return
     */
    public static double string2Double(String text) {
        try {
            return Double.parseDouble(text);
        } catch (Exception e) {
            return 0D;
        }
    }
    //----------------------------------------------------------------------

    /**
     * 身份证号加星号显示，前三位和后四位正常显示，其他显示星号
     *
     * @param srcNum 原身份证号
     * @return 加密后的身份证号
     */
    public static String getMaskedIdNum(String srcNum) {
        if (StringUtil.isEmpty(srcNum) || srcNum.length() < 15) {
            return srcNum;
        }
        StringBuffer dstIdNum = new StringBuffer();

        dstIdNum.append(srcNum.substring(0, 3));
        int max = 9;
        for (int i = 0; i < max; i++) {
            dstIdNum.append('*');
        }
        dstIdNum.append(srcNum.substring(srcNum.length() - 4));

        return dstIdNum.toString();
    }

    /**
     * 手机号加星号显示，第四位到第七位显示“*”
     *
     * @param srcNum 原手机号
     * @return 加密后的手机号
     */
    public static String getMaskedPhone(String srcNum) {
        String dstNum = null;
        if (null != srcNum && srcNum.length() > 7) {
            dstNum = srcNum.substring(0, 3) + "****"
                    + srcNum.substring(7, srcNum.length());
        } else {
            dstNum = srcNum;
        }
        return dstNum;
    }

    /**
     * 银行卡号加星号显示，四个星号加银行卡后四位 (****123456)
     *
     * @param bankName 银行名
     * @param srcNum   银行卡号
     * @return 加密后的银行卡号
     */
    public static String getMaskedCardNum(String bankName, String srcNum) {
        String dstNum = null;
        if (null != srcNum && srcNum.length() > 4) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(bankName);
            buffer.append(" （");
            buffer.append(getMaskedCardNum(srcNum));
            buffer.append("）");
            dstNum = buffer.toString();
        } else {
            dstNum = srcNum;
        }
        return dstNum;
    }

    /**
     * 银行卡号加星号显示，四个星号加银行卡后四位  ****123456
     *
     * @param srcNum 银行卡号
     * @return 加密后的银行卡号
     */
    public static String getMaskedCardNum(String srcNum) {
        String dstNum = null;
        if (null != srcNum && srcNum.length() > 4) {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < 4; i++) {
                buffer.append('*');
            }
            buffer.append(srcNum.substring(srcNum.length() - 4));
            dstNum = buffer.toString();
        } else {
            dstNum = srcNum;
        }
        return dstNum;
    }

    /**
     * 银行卡号保留前后4位，中间加星号
     *
     * @param card
     * @return
     */
    public static String getMaskedCardId(String card) {
        String dstNum = "";
        if (null != card && card.length() > 8) {
            int tl = card.length() - 8;
            StringBuffer strb = new StringBuffer();
            for (int i = 1; i < tl+1; i++) {
                strb.append("*");
                if (i != 1 && i % 4 == 0) {
                    strb.append("\t");
                }
            }
            dstNum = card.substring(0, 4) + "\t" + strb.toString() + "\t" + card.substring(card.length() - 4, card.length());
        }
        return dstNum;
    }

    /**
     * 银行卡号保留后4位，每4位加一个空格
     *
     * @param card
     * @return
     */
    public static String getMaskedBankNum(String card) {
        String dstNum = "";
        if (null != card && card.length() > 8) {
            int tl = card.length() - 8;
            StringBuffer strb = new StringBuffer();
            for (int i = 0; i < tl; i++) {
                strb.append("*");
                if (i != 0 && i % 4 == 0) {
                    strb.append("\t");
                }
            }
            dstNum = "****\t" + strb.toString() + "\t" + card.substring(card.length() - 4, card.length());
        }
        return dstNum;
    }

    /**
     * 身份证号保留前后4位，中间加星号
     *
     * @param card
     * @return
     */
    public static String getIdenityCardId(String card) {
        String dstNum = "";
        if (card != null && card.length() == 18) {
            dstNum = card.substring(0, 4) + "\t" + "***** *****" + "\t" + card.substring(card.length() - 4, card.length());
        }
        return dstNum;
    }
    //----------------------------------------------------------------------

    /**
     * Get String UTF-8编码
     * String UTF8编码
     */
    public static String getStringToUTF8(String str) {
        String utf8Str = "";
        try {
            utf8Str = URLEncoder.encode(new String(str.getBytes("UTF-8")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return utf8Str;
    }

    /**
     * UTF8 解码
     *
     * @param utf8Str
     */
    public static String getUTF8ToString(String utf8Str) {
        String str = "";
        try {
            str = URLDecoder.decode(utf8Str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    //-----------------------------------------------------------------------
    //从html中获取文本
    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符

    /**
     * @param htmlStr
     * @return 删除Html标签
     */
    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
        return htmlStr.trim(); // 返回文本字符串
    }

    /**
     * 从html中取得文本
     *
     * @param htmlStr
     * @return
     */
    public static String getTextFromHtml(String htmlStr) {
        htmlStr = delHTMLTag(htmlStr);
        htmlStr = htmlStr.replaceAll("&nbsp;", "");
        htmlStr = htmlStr.substring(0, htmlStr.indexOf("。") + 1);
        return htmlStr;
    }

}
