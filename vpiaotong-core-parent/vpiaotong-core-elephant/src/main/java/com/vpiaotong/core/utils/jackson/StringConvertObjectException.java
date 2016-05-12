package com.vpiaotong.core.utils.jackson;

/**
 * JSON串转对象异常
 * 
 * @author ZTH
 */
public class StringConvertObjectException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3201187150638208832L;

    public StringConvertObjectException() {
        super();
    }

    public StringConvertObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public StringConvertObjectException(String message) {
        super(message);
    }

    public StringConvertObjectException(Throwable cause) {
        super(cause);
    }
}
