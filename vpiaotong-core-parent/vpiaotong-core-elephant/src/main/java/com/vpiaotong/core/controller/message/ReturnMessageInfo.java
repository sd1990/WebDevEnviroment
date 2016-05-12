package com.vpiaotong.core.controller.message;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * MVC返回处理描述信息
 * 
 * @author ZTH
 */
public final class ReturnMessageInfo {

    /** 初始化状态信息容器 */
    private static final Map<Message, Map.Entry<String, String>> MESSAGE_MAP =
            new LinkedHashMap<Message, Map.Entry<String, String>>();

    /** 信息Key值 */
    private static final String MESSAGE_KEY_CODE = "message";

    /** 处理状态为200返回信息 */
    private static final String MESSAGE_200 = "处理成功";

    /** 处理状态为400返回信息 */
    private static final String MESSAGE_400 = "参数异常";

    /** 处理状态为500返回信息 */
    private static final String MESSAGE_500 = "服务器内部错误";

    static {
        addStatusMessage(Message.KEY200, new AbstractMap.SimpleEntry<String, String>(MESSAGE_KEY_CODE, MESSAGE_200));
        addStatusMessage(Message.KEY400, new AbstractMap.SimpleEntry<String, String>(MESSAGE_KEY_CODE, MESSAGE_400));
        addStatusMessage(Message.KEY500, new AbstractMap.SimpleEntry<String, String>(MESSAGE_KEY_CODE, MESSAGE_500));
    }

    /**
     * 私有化构造函数:防止被实例化
     */
    private ReturnMessageInfo() {
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
    private static void addStatusMessage(Message status, Map.Entry<String, String> message) {
        ReturnMessageInfo.MESSAGE_MAP.put(status, message);
    }

    /**
     * 根据指定代码获取完整描述
     * 
     * @param key
     *            代码
     * @return 该代码的完整描述
     */
    private static Map.Entry<String, String> getMessage(Message key) {
        return MESSAGE_MAP.get(key);
    }

    /**
     * 根据指定代码获取数据值
     * 
     * @param key
     *            代码
     * @return 该代码对应的数据值
     */
    public static String getValue(Message key) {
        return getMessage(key).getValue();
    }

    /**
     * 根据指定代码获取数据键
     * 
     * @param key
     *            代码
     * @return 该代码对应的数据键
     */
    public static String getKey(Message key) {
        return getMessage(key).getKey();
    }

    /**
     * 返回信息枚举类
     * 
     * @author ZTH
     */
    public enum Message {
        /** 处理正常 */
        KEY200,
        /** 提交参数错误 */
        KEY400,
        /** 服务异常 */
        KEY500
    }
}
