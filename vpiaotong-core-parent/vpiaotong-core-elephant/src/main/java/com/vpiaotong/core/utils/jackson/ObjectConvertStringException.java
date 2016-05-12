package com.vpiaotong.core.utils.jackson;

/**
 * 对象转JSON串异常
 * 
 * @author ZTH
 */
public class ObjectConvertStringException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6707528344698092468L;

    public ObjectConvertStringException() {
        super();
    }

    public ObjectConvertStringException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectConvertStringException(String message) {
        super(message);
    }

    public ObjectConvertStringException(Throwable cause) {
        super(cause);
    }
}
