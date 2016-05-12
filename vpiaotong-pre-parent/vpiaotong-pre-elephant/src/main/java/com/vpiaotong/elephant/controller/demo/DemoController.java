package com.vpiaotong.elephant.controller.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vpiaotong.core.controller.BaseController;
import com.vpiaotong.core.utils.jackson.JsonConvertUtil;
import com.vpiaotong.core.utils.jackson.ObjectConvertStringException;

@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController {

    @RequestMapping(value = "/test", method = { RequestMethod.GET })
    @ResponseBody
    public List<Object> demoTest() {
        List<Object> list = new ArrayList<Object>();
        list.add("3ww43w3");
        return list;
    }

    @RequestMapping(value = "/exceptionTest", method = { RequestMethod.GET })
    @ResponseBody
    public String demoExceptionTest() throws ObjectConvertStringException {
        throw new ObjectConvertStringException("测试");
    }

    @RequestMapping(value = "/stringTest", method = { RequestMethod.GET })
    @ResponseBody
    public String demoTest1() throws ObjectConvertStringException {
        List<Object> list = new ArrayList<Object>();
        list.add("11111222");
        String result = JsonConvertUtil.objectToJson(list);
        return result;
    }
}
