package com.yxproj.yxproject.common.contanst.Util;


import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

public class SM3Security {

    public static byte[] fromHexString(String data) {
        return ByteUtils.fromHexString(data);
    }

    public static String toHexString(String data) {
        return ByteUtils.toHexString(data.getBytes());
    }

    public static String toHexString(byte[] data) {
        return ByteUtils.toHexString(data);
    }

    public static String hashToHexString(@NotNull String data) {
        return ByteUtils.toHexString(hash(data.getBytes()));
    }

    public static String hashToHexString(@NotNull String data, @NotNull String salt) {
        return ByteUtils.toHexString(hash(data.getBytes(), salt.getBytes()));
    }

    public static byte[] hash(@NotNull String data) {
        return hash(data.getBytes());
    }

    public static byte[] hash(@NotNull String data, @NotNull String salt) {
        return hash(data.getBytes(), salt.getBytes());
    }

    public static byte[] hash(@NotNull byte[] data) {
        return SM3Util.hash(data);
    }

    public static byte[] hash(@NotNull byte[] data, @NotNull byte[] salt) {
        byte[] mergeResult = new byte[data.length + salt.length];
        System.arraycopy(data, 0, mergeResult, 0, data.length);
        System.arraycopy(salt, 0, mergeResult, data.length, salt.length);
        return SM3Util.hash(mergeResult);
    }

    public static boolean verifyFromHexString(@NotNull String sourceData, @NotNull String sm3HashHexString) {
        return verify(sourceData.getBytes(), ByteUtils.fromHexString(sm3HashHexString));
    }

    public static boolean verifyFromHexString(@NotNull String sourceData, @NotNull String salt, @NotNull String sm3HashHexString) {
        return verify(sourceData.getBytes(), salt.getBytes(), ByteUtils.fromHexString(sm3HashHexString));
    }

    public static boolean verify(@NotNull byte[] sourceData, @NotNull byte[] sm3HashData) {
        byte[] newHash = hash(sourceData);
        return Arrays.equals(newHash, sm3HashData);
    }

    public static boolean verify(@NotNull byte[] sourceData, @NotNull byte[] salt, @NotNull byte[] sm3HashData) {
        byte[] newHash = hash(sourceData, salt);
        return Arrays.equals(newHash, sm3HashData);
    }

    public static void main(String[] args) {
        String password = "123456";
        String salt = "000000";

        System.out.println(SM3Security.hashToHexString(SM3Security.hashToHexString(password) + salt));
        System.out.println(SM3Security.hashToHexString(password));


    }

}
