package com.vpiaotong.core.controller.message;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * MVC返回状态信息
 * 
 * @author ZTH
 */
public final class ReturnStatusInfo {

    /** 初始化状态信息容器 */
    private static final Map<MessageStatus, Map.Entry<String, String>> MESSAGE_STATUS_MAP =
            new LinkedHashMap<MessageStatus, Map.Entry<String, String>>();

    /** 状态码Key值 */
    private static final String STATUS_KEY_CODE = "code";

    /** 状态码200值 */
    private static final String STATUS_200 = "200";

    /** 状态码400值 */
    private static final String STATUS_500 = "400";

    /** 状态码500值 */
    private static final String STATUS_400 = "500";

    static {
        addStatusMessage(MessageStatus.KEY200,
                new AbstractMap.SimpleEntry<String, String>(STATUS_KEY_CODE, STATUS_200));
        addStatusMessage(MessageStatus.KEY400,
                new AbstractMap.SimpleEntry<String, String>(STATUS_KEY_CODE, STATUS_500));
        addStatusMessage(MessageStatus.KEY500,
                new AbstractMap.SimpleEntry<String, String>(STATUS_KEY_CODE, STATUS_400));
    }

    /**
     * 私有化构造函数:防止被实例化
     */
    private ReturnStatusInfo() {
        throw new IllegalAccessError();
    }

    /**
     * 添加状态信息
     * 
     * @param status
     *            状态码
     * @param message
     *            信息
     * @return
     */
    private static void addStatusMessage(MessageStatus status, Map.Entry<String, String> message) {
        ReturnStatusInfo.MESSAGE_STATUS_MAP.put(status, message);
    }

    /**
     * 根据指定代码获取完整描述
     * 
     * @param key
     *            代码
     * @return 该代码的完整描述
     */
    private static Map.Entry<String, String> getMessage(MessageStatus key) {
        return MESSAGE_STATUS_MAP.get(key);
    }

    /**
     * 根据指定代码获取数据值
     * 
     * @param key
     *            代码
     * @return 该代码对应的数据值
     */
    public static String getValue(MessageStatus key) {
        return getMessage(key).getValue();
    }

    /**
     * 根据指定代码获取数据键
     * 
     * @param key
     *            代码
     * @return 该代码对应的数据键
     */
    public static String getKey(MessageStatus key) {
        return getMessage(key).getKey();
    }

    /**
     * 返回信息状态码枚举类
     * 
     * @author ZTH
     */
    public enum MessageStatus {
        /** 处理正常 */
        KEY200,
        /** 提交参数错误 */
        KEY400,
        /** 服务异常 */
        KEY500
    }
}
