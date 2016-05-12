package com.vpiaotong.core.utils.security;

import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;

/**
 * 处理加密的数据库密文账号、密码
 * 
 * @author ZTH
 */
public class PropertiesEncryptFactoryBean implements FactoryBean<Object> {

    /** 文件配置属性 */
    private Properties properties;

    @Override
    public Object getObject() throws Exception {
        return getProperties();
    }

    @Override
    public Class<?> getObjectType() {
        return java.util.Properties.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties inProperties) {
        this.properties = inProperties;
        String originalUsername = properties.getProperty("user");
        String originalPassword = properties.getProperty("password");
        if (originalUsername != null) {
            String newUsername = deEncryptUsername(originalUsername);
            properties.put("user", newUsername);
        }
        if (originalPassword != null) {
            String newPassword = deEncryptPassword(originalPassword);
            properties.put("password", newPassword);
        }
    }

    /**
     * 解密用户名
     * 
     * @param originalUsername
     *            密文用户名
     * @return 用户名
     */
    private String deEncryptUsername(String originalUsername) {
        return deEncryptString(originalUsername);
    }

    /**
     * 解密密码
     * 
     * @param originalPassword
     *            密文密码
     * @return 密码
     */
    private String deEncryptPassword(String originalPassword) {
        return deEncryptString(originalPassword);
    }

    /**
     * 解密加密的密文
     * 
     * @param originalString
     *            密文
     * @return 解密后的数据
     */
    private String deEncryptString(String originalString) {
        return SecurityUtil.DESDecrypt(originalString, seedEnum.SEED.getSeed());
    }

    /** 密钥种子枚举类 */
    private enum seedEnum {
        /** 密钥种子 */
        SEED("VPIAOTONG");

        private final String seed;

        seedEnum(String seed) {
            this.seed = seed;
        }

        public String getSeed() {
            return SecurityUtil.generateDESKey(seed);
        }
    }

    public static void main(String[] args) {
        System.out.println(SecurityUtil.DESEncrypt("mysql", seedEnum.SEED.getSeed()));
        System.out.println(SecurityUtil.DESEncrypt("v12345", seedEnum.SEED.getSeed()));
    }
}
