package com.vpiaotong.core.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vpiaotong.core.controller.message.ReturnMessageInfo;
import com.vpiaotong.core.controller.message.ReturnMessageInfo.Message;
import com.vpiaotong.core.controller.message.ReturnStatusInfo;
import com.vpiaotong.core.controller.message.ReturnStatusInfo.MessageStatus;
import com.vpiaotong.core.utils.jackson.JsonConvertUtil;
import com.vpiaotong.core.utils.jackson.ObjectConvertStringException;

/**
 * 控制层积累文件
 * 
 * @author ZTH
 */
@Controller
@RequestMapping("/base")
public class BaseController {

    /** 返回数据标识 */
    private static final String RESPONSE_DATA_CODE = "response";

    /** 返回数据描述信息标识 */
    protected static final String RESPONSE_MESSAGE_CODE = "message";

    /** 返回数据行标识 */
    protected static final String RESPONSE_DATALIST_CODE = "rows";

    /**
     * 用户跳转JSP页面
     * 
     * @param folder
     *            路径
     * @param jspName
     *            JSP名称(不加后缀)
     * @return 指定JSP页面
     */
    @RequestMapping("/{folder}/{jspName}")
    protected ModelAndView redirectJsp(@PathVariable String folder, @PathVariable String jspName) {
        String redirectURL = folder + "/" + jspName;
        return new ModelAndView(redirectURL);
    }

    /**
     * 封装返回数据
     * 
     * @param dataMap
     *            数据
     * @return JSON字符串
     * @throws ObjectConvertStringException
     *             转换异常
     */
    protected String packagingResult(Map<String, Object> dataMap) throws ObjectConvertStringException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(ReturnStatusInfo.getKey(MessageStatus.KEY200), ReturnStatusInfo.getValue(MessageStatus.KEY200));
        resultMap.put(ReturnMessageInfo.getKey(Message.KEY200), ReturnMessageInfo.getValue(Message.KEY200));
        resultMap.put(RESPONSE_DATA_CODE, dataMap);
        return JsonConvertUtil.objectToJson(resultMap);
    }

    /**
     * 自定义返回信息(status:200:处理成功)
     * 
     * @param dataMap
     *            数据
     * @param message
     *            自定义处理信息
     * @return JSON字符串
     * @throws ObjectConvertStringException
     *             转换异常
     */
    protected String packagingResult(String message, Map<String, Object> dataMap) throws ObjectConvertStringException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(ReturnStatusInfo.getKey(MessageStatus.KEY200), ReturnStatusInfo.getValue(MessageStatus.KEY200));
        resultMap.put(ReturnMessageInfo.getKey(Message.KEY200), message);
        resultMap.put(RESPONSE_DATA_CODE, dataMap);
        return JsonConvertUtil.objectToJson(resultMap);
    }

    /**
     * 自定义返回状态码及返回信息
     * 
     * @param status
     *            返回信息状态码枚举类
     * @param message
     *            返回信息枚举类
     * @param dataMap
     *            数据
     * @return JSON字符串
     * @throws ObjectConvertStringException
     *             转换异常
     */
    protected String packagingResult(MessageStatus status, Message message, Map<String, Object> dataMap)
            throws ObjectConvertStringException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(ReturnStatusInfo.getKey(status), ReturnStatusInfo.getValue(status));
        resultMap.put(ReturnMessageInfo.getKey(message), ReturnMessageInfo.getValue(message));
        resultMap.put(RESPONSE_DATA_CODE, dataMap);
        return JsonConvertUtil.objectToJson(resultMap);
    }

}
