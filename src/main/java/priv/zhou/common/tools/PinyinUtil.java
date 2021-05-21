package priv.zhou.common.tools;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼音转换工具类
 *
 * @author zhou
 * @since 0.1.0
 */
public class PinyinUtil {

    /**
     * 获取拼写字符穿
     */
    public static String toPinyin(String str) {
        return toPinyin(str, false);
    }

    /**
     * 转为拼写字符
     * initials=false   :   吴彦祖 -> wuyanzu
     * initials=true    :   吴彦祖 -> wyz
     */
    public static String toPinyin(String str, boolean initials) {
        StringBuilder builder = new StringBuilder();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            if (c < 128) {
                builder.append(c);
                continue;
            }
            String pinyin = getPinyin(c, format);
            builder.append(initials ? pinyin.charAt(0) : pinyin);
        }

        return builder.toString();
    }

    private static String getPinyin(char c, HanyuPinyinOutputFormat format) {
        try {
            return PinyinHelper.toHanyuPinyinStringArray(c, format)[0];
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
            return "";
        }
    }

}
