package com.vpiaotong.core.utils.majorkey.impl;

import java.util.Date;

import com.vpiaotong.core.utils.calendar.CalendarUtil;
import com.vpiaotong.core.utils.majorkey.IMajorKeyMaker;

/**
 * 分布式主键生成器
 * 
 * @author ZTH
 */
public final class DistributedKeyMaker implements IMajorKeyMaker {

    /** 系统||机器前缀标识 */
    private final static String SYSPREFIX = "A01";

    private final static long twepoch = 1288834974657L;

    // 机器标识位数
    private final static long workerIdBits = 5L;

    // 数据中心标识位数
    private final static long datacenterIdBits = 5L;

    // 机器ID最大值
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);

    // 数据中心ID最大值
    private final static long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    // 毫秒内自增位
    private final static long sequenceBits = 12L;

    // 机器ID偏左移12位
    private final static long workerIdShift = sequenceBits;

    // 数据中心ID左移17位
    private final static long datacenterIdShift = sequenceBits + workerIdBits;

    // 时间毫秒左移22位
    private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

    private static long lastTimestamp = -1L;

    private long sequence = 0L;

    private final long workerId;

    private final long datacenterId;

    /** 初始化分布式主键生成器 */
    private static class KeyMakerHolder {

        private static final DistributedKeyMaker instance = new DistributedKeyMaker(0, 1);
    }

    /** 取生成器实例 */
    public static final DistributedKeyMaker getInstance() {
        return KeyMakerHolder.instance;
    }

    private DistributedKeyMaker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("worker Id can't be greater than %d or less than 0");
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("datacenter Id can't be greater than %d or less than 0");
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 创建ID
     * 
     * @return majorKey
     */
    private String nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            try {
                throw new Exception("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp)
                        + " milliseconds");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        long nextId = ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;
        return String.valueOf(nextId);
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    /**
     * 取系统当前时间的毫秒数
     * 
     * @return Long毫秒数
     */
    private long timeGen() {
        return CalendarUtil.getCurrentTimeMillis();
    }

    @Override
    public synchronized String generateLongKey() {
        String perfix = CalendarUtil.convertDateTimeToString(new Date(), false);
        String key = perfix + SYSPREFIX + nextId();
        return key;
    }

    @Override
    public synchronized String generateShotKey() {
        return nextId();
    }
}
