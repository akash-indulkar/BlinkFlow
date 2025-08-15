package com.blinkflow.flowrun_listener.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignatureValidator {

	public static Boolean verifySignature(String receivedSignature, String secret, byte[] rawPayloadBytes) throws InvalidKeyException, NoSuchAlgorithmException {
		final SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        final Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(keySpec);
        final byte[] digest = mac.doFinal(rawPayloadBytes);
        final HexFormat hex = HexFormat.of();
        if(receivedSignature.startsWith("sha256")) {
        	final String calculatedSignature = "sha256=" + hex.formatHex(digest);
        	return (MessageDigest.isEqual(calculatedSignature.getBytes(), receivedSignature.getBytes()));
        }else {
        	final String calculatedSignature = hex.formatHex(digest);
        	return (MessageDigest.isEqual(calculatedSignature.getBytes(), receivedSignature.getBytes()));
        }
	}
}
