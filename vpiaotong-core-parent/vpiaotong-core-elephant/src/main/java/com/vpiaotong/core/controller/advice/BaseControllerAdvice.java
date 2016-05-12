package com.vpiaotong.core.controller.advice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpiaotong.core.controller.interceptor.DateEditor;
import com.vpiaotong.core.controller.interceptor.StringEscapeEditor;
import com.vpiaotong.core.controller.message.ReturnMessageInfo;
import com.vpiaotong.core.controller.message.ReturnMessageInfo.Message;
import com.vpiaotong.core.controller.message.ReturnStatusInfo;
import com.vpiaotong.core.controller.message.ReturnStatusInfo.MessageStatus;
import com.vpiaotong.core.utils.jackson.ObjectConvertStringException;
import com.vpiaotong.core.utils.jackson.StringConvertObjectException;

/**
 * MVC控制器增强类
 * <p>
 * ControllerAdvice注解内部使用的@ExceptionHandler、@InitBinder、
 * 
 * @ModelAttribute注解的方法都会应用到所有的@RequestMapping注解的方法
 *                                                  </p>
 * @author ZTH
 */
@ControllerAdvice
public class BaseControllerAdvice {

    private static Logger log = LoggerFactory.getLogger(BaseControllerAdvice.class);

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        /** 防止XSS攻击 */
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
        /** 日期转换 */
        binder.registerCustomEditor(Date.class, new DateEditor());
    }

    /**
     * MVC返回json转换异常
     * 
     * @param response
     *            http响应
     * @throws IOException
     */
    @ExceptionHandler({ ObjectConvertStringException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void processJson2StringException(Exception e, HttpServletResponse response) throws IOException {
        log.error(e.getMessage(), e);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 返回500错误
        resultMap.put(ReturnStatusInfo.getKey(MessageStatus.KEY500), ReturnStatusInfo.getValue(MessageStatus.KEY500));
        // 返回500描述信息
        resultMap.put(ReturnMessageInfo.getKey(Message.KEY500), ReturnMessageInfo.getValue(Message.KEY500));
        writeValue(response, resultMap);
    }

    /**
     * 请求出现语法错误（参数转JSON对象异常）
     * 
     * @param response
     *            http响应
     * @throws IOException
     */
    @ExceptionHandler({ StringConvertObjectException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void processString2JsonException(Exception e, HttpServletResponse response) throws IOException {
        log.error(e.getMessage(), e);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 返回400错误
        resultMap.put(ReturnStatusInfo.getKey(MessageStatus.KEY400), ReturnStatusInfo.getValue(MessageStatus.KEY400));
        // 返回400描述信息
        resultMap.put(ReturnMessageInfo.getKey(Message.KEY400), ReturnMessageInfo.getValue(Message.KEY400));
        writeValue(response, resultMap);
    }

    /**
     * 处理运行期异常
     * 
     * @param response
     * @throws IOException
     */
    @ExceptionHandler({ RuntimeException.class, IllegalArgumentException.class, SQLException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void processRunTimeException(Exception e, HttpServletResponse response) throws IOException {
        log.error(e.getMessage(), e);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 返回500错误
        resultMap.put(ReturnStatusInfo.getKey(MessageStatus.KEY500), ReturnStatusInfo.getValue(MessageStatus.KEY500));
        // 返回500描述信息
        resultMap.put(ReturnMessageInfo.getKey(Message.KEY500), ReturnMessageInfo.getValue(Message.KEY500));
        writeValue(response, resultMap);
    }

    /**
     * 返回信息
     * 
     * @param response
     * @param value
     * @throws IOException
     */
    private void writeValue(HttpServletResponse response, Object value) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), value);
    }
}
