package com.vpiaotong.core.utils.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json转换工具类
 * 
 * @author zhaoTh
 */
public final class JsonConvertUtil {

    /**
     * 私有构造函数
     */
    private JsonConvertUtil() {

    }

    /**
     * Object转字符串工具(使用Jackson:实体有关联关系时使用)
     * 
     * @param objectModel
     * @return json字符串
     * @throws ObjectConvertStringException
     * @throws JsonProcessingException
     */
    public static String objectToJson(Object objectModel) throws ObjectConvertStringException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(objectModel);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ObjectConvertStringException(e);
        }
    }

    /**
     * 字符串转Object(使用Jackson)
     * 
     * @param jsonStringData
     *            json字符串
     * @param targetClass
     *            形如：UserEntity.class
     * @return Object
     * @throws StringConvertObjectException
     */
    public static <T> T jsonToObject(String jsonStringData, Class<T> targetClass) throws StringConvertObjectException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonStringData, targetClass);
        }
        catch (JsonParseException e) {
            e.printStackTrace();
            throw new StringConvertObjectException(e);
        }
        catch (JsonMappingException e) {
            e.printStackTrace();
            throw new StringConvertObjectException(e);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new StringConvertObjectException(e);
        }
    }

    /**
     * 获取泛型的Collection Type（例如：(ArrayList.class, List.class, String.class);）
     * 
     * @param parametrized
     *            构造类的实例
     * @param parametersFor
     *            构造类或接口的类型
     * @param elementClasses
     *            泛型类型参数
     * @return JavaType Java类型
     */
    public static JavaType getCollectionType(Class<?> parametrized, Class<?> parametersFor,
            Class<?>... elementClasses) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.getTypeFactory().constructParametrizedType(parametrized, parametersFor, elementClasses);
    }

    /**
     * JSON串转泛型的对象
     * 
     * @param jsonStringData
     * @param collectionType
     * @return
     */
    public static <T> T jsonToObject(String jsonStringData, JavaType collectionType)
            throws StringConvertObjectException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonStringData, collectionType);
        }
        catch (JsonParseException e) {
            e.printStackTrace();
            throw new StringConvertObjectException(e);
        }
        catch (JsonMappingException e) {
            e.printStackTrace();
            throw new StringConvertObjectException(e);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new StringConvertObjectException(e);
        }
    }
}
