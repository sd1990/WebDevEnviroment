package com.vpiaotong.core.utils.exception;

/**
 * 上传异常
 * 
 * @author ZTH
 */
public class UploadException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3562330451208811139L;

    public UploadException() {
        super();
    }

    public UploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public UploadException(String message) {
        super(message);
    }

    public UploadException(Throwable cause) {
        super(cause);
    }
}
