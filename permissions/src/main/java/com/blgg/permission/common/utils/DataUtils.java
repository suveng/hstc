package com.blgg.permission.common.utils;

import com.blgg.permission.common.exception.BLGGException;
import org.apache.commons.lang.StringUtils;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 *
 * @ClassName DataUtils
 * @Description 数据校验
 * @Author xiaobo
 * @Date 2018/10/14/014 3:34
 */
public abstract class DataUtils {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new BLGGException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new BLGGException(message);
        }
    }
}
