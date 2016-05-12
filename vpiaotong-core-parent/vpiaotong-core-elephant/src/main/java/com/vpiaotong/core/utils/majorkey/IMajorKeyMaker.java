package com.vpiaotong.core.utils.majorkey;

/**
 * 主键生成器
 * 
 * @author ZTH
 */
public interface IMajorKeyMaker {

    /**
     * 生成长度为35位的流水号
     * 
     * @return 长度为35位的流水号
     */
    public String generateLongKey();

    /**
     * 生成18位长度主键
     * 
     * @return 长度为18位的主键
     */
    public String generateShotKey();

}
