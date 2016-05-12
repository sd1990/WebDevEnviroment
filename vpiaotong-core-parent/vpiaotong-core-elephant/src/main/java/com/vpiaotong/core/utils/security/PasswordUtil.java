package com.vpiaotong.core.utils.security;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * 密码hash操作 采用PBKDF2方案
 * 
 * @author ldh
 */
public class PasswordUtil {

    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    public static final int SALT_BYTE_SIZE = 64;

    public static final int HASH_BYTE_SIZE = 64;

    public static final int PBKDF2_ITERATIONS = 1000;

    public static final int ITERATION_INDEX = 0;

    public static final int SALT_INDEX = 1;

    public static final int PBKDF2_INDEX = 2;

    /**
     * 对密码明文进行摘要，返回 迭代次数:随机盐值:密码摘要
     * 
     * @param password
     *            密码明文
     * @return
     */
    public static String encryptPassword(String password) {
        String hashStr = null;
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_BYTE_SIZE];
            random.nextBytes(salt);
            byte[] hash = pbkdf2(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
            hashStr = PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return hashStr;
    }

    /**
     * 密码校验
     * 
     * @param password
     *            密码明文
     * @param correctHash
     *            要比对的摘要值，格式：迭代次数:随机盐值:密码摘要
     * @return
     */
    public static boolean validatePassword(String password, String correctHash) {
        boolean ret = false;
        try {
            String[] params = correctHash.split(":");
            int iterations = Integer.parseInt(params[ITERATION_INDEX]);
            byte[] salt = fromHex(params[SALT_INDEX]);
            byte[] hash = fromHex(params[PBKDF2_INDEX]);
            byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
            ret = slowEquals(hash, testHash);
            // ret = Arrays.equals(hash, testHash);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }

    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }

    public static void main(String[] args) {

        String password = "123456";
        String hash = encryptPassword(password);
        System.out.println(hash);
        if (!validatePassword(password, hash)) {
            System.out.println("密碼錯誤");
        }
        else {
            System.out.println("密碼正確");
        }
    }
}
