package com.statics.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class Encriptacion {

    private static final String alg = "AES";
    private static final String cI = "AES/CBC/PKCS5Padding";

    public String encrypt(String cleartext)
        
            throws Exception { 
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec skeySpec = new SecretKeySpec("DAVEGOMEZ230992".getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("0123456789ABCDEF".getBytes());
        cipher.init(1, skeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(cleartext.getBytes());
        
        return new String(Base64.encodeBase64(encrypted));
    }

    public String decrypt(String encrypted)
            throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec skeySpec = new SecretKeySpec("DAVEGOMEZ230992".getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("0123456789ABCDEF".getBytes());
        byte[] enc = Base64.decodeBase64(encrypted);
        cipher.init(2, skeySpec, ivParameterSpec);
        byte[] decrypted = cipher.doFinal(enc);
        return new String(decrypted);
    }
    

}
