package com.vpiaotong.core.utils.file;

import java.io.InputStream;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * 配置文件读取类
 * 
 * @author ZTH
 */
public final class PropertiesConfigurationUtil {

    /**
     * 私有化构造函数，防止没构建
     */
    private PropertiesConfigurationUtil() {
        throw new IllegalAccessError();
    }

    /**
     * 获取配置文件属性实例
     * 
     * @param fileName
     *            配置文件名称
     * @return PropertiesConfiguration
     */
    public static PropertiesConfiguration initConfig(String fileName) {
        PropertiesConfiguration config = new PropertiesConfiguration();
        try {
            config.load(fileName);
            // 自动重载
            config.setReloadingStrategy(new FileChangedReloadingStrategy());
        }
        catch (ConfigurationException e) {
            e.printStackTrace();
            InputStream in = PropertiesConfigurationUtil.class.getClassLoader().getResourceAsStream(fileName);
            try {
                config.load(in);
            }
            catch (ConfigurationException e1) {
                e1.printStackTrace();
                return null;
            }
        }
        return config;
    }
}
