/*
 * Licensed Materials - Property of Tecnics Integration Technologies Pvt Ltd.
 *   (C) Copyright Tecnics Corp. 2021
 */

package com.tecnics.einvoice.util;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.exceptions.InternalException;
//import com.tecnics.einvoice.log.BaseLogger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * <p>This class is implemented to have all encryption & decryption techniques</p>
 * @version 1.0
 * @since  1.0
 */
@Component
public class HashingUtil  {

   // BaseLogger log = BaseLogger.getBaseLogger(HashingUtil.class);
    private String SECRET_HASHING_KEY = "t6w9z$B&E)H@McQf";

    /**
     *
     * @param param
     * @return
     */
    public String encryptKey(@NonNull  String param) {
        String methodName = "encryptKey";
        //log.logMethodEntryTrace(methodName, new String[]{param});
        String encryptionKey = null;
        try {
            Security.setProperty("crypto.policy", "unlimited");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_HASHING_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptionKey =  Base64Utils.encodeToString(cipher.doFinal(param.getBytes()));
        } catch (Exception e) {
            //log.logErrorMessage(methodName,e);
            throw new InternalException(Ex.HASH_ENCRYPT_FAIL.getKey(),Ex.formatMessage(Ex.HASH_ENCRYPT_FAIL.getKeyMessage(), new String[]{param}));
        }
        return  encryptionKey;
    }

    /**
     *<p> This method decrypt the message key with secret key - (128 bit hash key)</p>
     * @param param
     * @return decrypt the message key
     * @since  1.0
     */
    public String decryptKey(@NonNull  String param) {
        String methodName = "decryptKey";
      //  log.logMethodEntryTrace(methodName, new String[]{param});
        String decryptionKey = null;
        try {
            Security.setProperty("crypto.policy", "unlimited");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_HASHING_KEY.getBytes(),"AES"); // AES is base algorithm and using 128bit seceret key
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedParam = Base64Utils.decodeFromString(param);
            byte[] decValue = c.doFinal(decodedParam);
            decryptionKey = new String(decValue);
        } catch (Exception e) {
     //       log.logErrorMessage(methodName,e);
            throw new InternalException(Ex.HASH_DECRYPT_FAIL.getKey(),Ex.formatMessage(Ex.HASH_DECRYPT_FAIL.getKeyMessage(), new String[]{param}));
        }
        return decryptionKey;
    }
}
