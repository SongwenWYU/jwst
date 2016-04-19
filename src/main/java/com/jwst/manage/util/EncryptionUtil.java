package com.jwst.manage.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility to encrypt, decrypt, and hash
 */
public class EncryptionUtil {

    private static Logger log = LoggerFactory.getLogger(EncryptionUtil.class);

    //secret key
    private static final byte[] key = new byte[]{'d', '3', '2', 't', 'p', 'd', 'M', 'o', 'I', '8', 'x', 'z', 'a', 'P', 'o', 'd'};

    /**
     * generate salt for hash
     *
     * @return salt
     */
    public static String generateSalt() {
        byte[] salt = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return new String(Base64.encodeBase64(salt));
    }

    /**
     * return hash value of string
     *
     * @param str  unhashed string
     * @param salt salt for hash
     * @return hash value of string
     */
    public static String hash(String str, String salt) {
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            if (StringUtils.isNotEmpty(salt)) {
                md.update(Base64.decodeBase64(salt.getBytes()));
            }
            md.update(str.getBytes("UTF-8"));
            hash = new String(Base64.encodeBase64(md.digest()));
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return hash;
    }

    /**
     * return hash value of string
     *
     * @param str unhashed string
     * @return hash value of string
     */
    public static String hash(String str) {
        return hash(str, null);
    }

    /**
     * return encrypted value of string
     *
     * @param str unencrypted string
     * @return encrypted string
     */
    public static String encrypt(String str) {

        String retVal = null;
        if (str != null && str.length() > 0) {
            try {
                Cipher c = Cipher.getInstance("AES");
                c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
                byte[] encVal = c.doFinal(str.getBytes());
                retVal = new String(Base64.encodeBase64(encVal));
            } catch (Exception ex) {
                log.error(ex.toString(), ex);
            }

        }
        return retVal;
    }

    /**
     * return decrypted value of encrypted string
     *
     * @param str encrypted string
     * @return decrypted string
     */
    public static String decrypt(String str) {
        String retVal = null;
        if (str != null && str.length() > 0) {
            try {
                Cipher c = Cipher.getInstance("AES");
                c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
                byte[] decodedVal = Base64.decodeBase64(str.getBytes());
                retVal = new String(c.doFinal(decodedVal));
            } catch (Exception ex) {
                log.error(ex.toString(), ex);
            }

        }
        return retVal;
    }


}
