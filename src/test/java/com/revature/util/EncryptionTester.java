package com.revature.util;


import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.crypto.Cipher;

import static org.mockito.Mockito.when;

public class EncryptionTester {

    @Test(expected = InvalidRequestException.class)
    public void testNullMessage(){
        String message = null;
        Encryption.encrypt(message);
    }

    @Test(expected = InvalidRequestException.class)
    public void testEmptyMessage(){
        String message = "";
        Encryption.encrypt(message);
    }

    @Test
    public void testGoodMessage(){
        String message = "password";

        String encrypted = Encryption.encrypt(message);

        Assert.assertEquals(encrypted, Encryption.encrypt(message));
    }
}
