package hugh.catopuma.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串校验
 * Created by on 2016/8/15.
 */
public class StringCheckUtil {

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        /**
         * 国内手机号码验证规则：
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186
         * 电信：133、153、180、189、（1349卫通）
         * 新增的4G手机号段：
         * 中国电信分到新号段170,177,联通分到了176,移动分到了178号段.
         */
        String regExp = "^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0,6-8])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
       // return m.matches();
        return true;
    }

    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 6位数字密码校验
     *
     * @param str
     * @return
     */
    public static boolean isSixNumPassword(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^\\d{6}$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 密码8到20位必须包含字母和数字
     */
    public static boolean isPassword(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    //-------------身份证号码校验
    static final int[] WEIGHT = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    static final char[] VALID = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    static final SimpleDateFormat YYMMdd = new SimpleDateFormat("yyMMdd", Locale.getDefault());

    /**
     * 身份证号码校验
     *
     * @param notEmptyInput
     * @return
     */
    public static boolean performIdCard(String notEmptyInput) {
        final int length = notEmptyInput.length();
        if (15 == length) {
            try {
                return isOldCNIDCard(notEmptyInput);
            } catch (Exception e) {
                return false;
            }
        } else if (18 == length) {
            return isNewCNIDCard(notEmptyInput.toUpperCase());
        } else {
            return false;
        }
    }

    public static boolean isNewCNIDCard(String numbers) {
        int sum = 0;
        for (int i = 0; i < WEIGHT.length; i++) {
            final int cell = Character.getNumericValue(numbers.charAt(i));
            sum += WEIGHT[i] * cell;
        }
        int index = sum % 11;
        return VALID[index] == numbers.charAt(17);
    }

    public static boolean isOldCNIDCard(String numbers) {
        final String abcdef = numbers.substring(0, 5);
        final String yymmdd = numbers.substring(6, 11);
        final String xxx = numbers.substring(12, 14);
        final boolean aPass = abcdef.equals(String.valueOf(Integer.parseInt(abcdef)));
        boolean yPass = true;
        try {
            YYMMdd.parse(yymmdd);
        } catch (ParseException e) {
            e.printStackTrace();
            yPass = false;
        }
        boolean xPass = Pattern.compile("\\d{2}[\\dxX]").matcher(xxx).matches();
        return aPass && yPass && xPass;
    }

    //------------------------------银行卡号校验

    public static boolean performBlankCard(String input) {
        LogUtil.v("performBlankCard()"+input);
        // accept only spaces, digits and dashes
        if (!Pattern.compile("[\\d -]*").matcher(input).matches()) {
            return false;
        }
        String value = input.replaceAll("\\D", "");
        final int length = value.length();
        if ( 13 > length || 19 < length){
            return false;
        }else{
            return matchLuhn(value, length);
        }
    }

    private static boolean matchLuhn(String rawCardNumbers, int length){
        char cDigit;
        int nCheck = 0, nDigit;
        boolean bEven = false;
        for ( int n = length - 1; n >= 0; n--) {
            cDigit = rawCardNumbers.charAt(n);
            nDigit = Integer.parseInt(String.valueOf(cDigit), 10);
            if ( bEven ) {
                if ( (nDigit *= 2) > 9 ) {
                    nDigit -= 9;
                }
            }
            nCheck += nDigit;
            bEven = !bEven;
        }
        return (nCheck % 10) == 0;
    }

}
