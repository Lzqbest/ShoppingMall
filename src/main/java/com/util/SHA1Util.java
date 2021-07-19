package com.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author Best-Traveler
 * @Date 2020/11/05
 * @Description
 **/
@Slf4j
public class SHA1Util {

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * SHA1加密
     * @param str
     * @return
     */
    public static String sha1(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("sha1");
            messageDigest.update(str.getBytes());
            byte[] digest = messageDigest.digest();

            StringBuilder builder = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                builder.append(HEX_DIGITS[(b >> 4) & 0x0f]);
                builder.append(HEX_DIGITS[b & 0x0f]);
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("sha1加密失败！[{}]", e.getMessage());
            return null;
        }
    }
}
