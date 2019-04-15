package com.blgg.permission.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * @ClassName BLGGException
 * @Description  自定义异常
 * @Author xiaobo
 * @Date 2018/10/13/013 2:35
 */
public class BLGGException extends RuntimeException{
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public BLGGException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BLGGException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public BLGGException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BLGGException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
