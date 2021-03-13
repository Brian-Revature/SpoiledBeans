package com.revature.util;

import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.Base64;

/**
 * This is a singleton class that will encrypt a string that is passed
 * to it
 */
@Component
public class Encryption {

    /**
     * This method will encrypt the message passed to it with AES/ECB encryption
     * using 128 bits and padded with PKCS5
     * @param message the message to be encrypted
     * @return the encrypted message string
     */
    public static String encrypt(String message){

        byte[] encryptionKeyBytes = System.getenv("private_key").getBytes();
        byte[] encryptedMessage;
        Cipher cipher;

        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new ResourceNotFoundException();
        }

        SecretKey secretKey = new SecretKeySpec(encryptionKeyBytes, "AES");

        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            if(message == null || message.trim().equals("")){
                throw new InvalidRequestException();
            }
            encryptedMessage = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new ResourceNotFoundException();
        }

        return Base64.getEncoder().encodeToString(encryptedMessage);
    }
}