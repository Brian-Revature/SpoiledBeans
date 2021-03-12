package com.revature.util;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

/**
 * This class is the configuration for the JWT, which is for encrypting the token information
 */
@Component
public class JwtConfig {

    private String secretKey = "revature";
    private final SignatureAlgorithm SIG_ALG = SignatureAlgorithm.HS256;
    private final Key SIGNING_KEY;

    /*
        Static block to initialize the signing key
     */
    {
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        SIGNING_KEY = new SecretKeySpec(secretBytes, SIG_ALG.getJcaName());
    }

    /**
     * Gets the secret key for the JWT configuration
     * @return the secret key as a string
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Sets the secret key for the JWT configuration
     * @param secretKey the secret key as a string
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Gets the algorithm that signs the JWTs
     * @return the algorithm to sign the JWTs
     */
    public SignatureAlgorithm getSignatureAlgorithm() {
        return SIG_ALG;
    }

    /**
     * Gets the signing key for our application
     * @return the signing key for the application
     */
    public Key getSigningKey() {
        return SIGNING_KEY;
    }
}
