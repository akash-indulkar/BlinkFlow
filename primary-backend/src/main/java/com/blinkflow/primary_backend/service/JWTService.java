package com.blinkflow.primary_backend.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
private String secretKey;
	
	public JWTService() {
		secretKey = generateSecretKey();
	}
	
	public String generateSecretKey () {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey secretKey = keyGen.generateKey();
			return Base64.getEncoder().encodeToString(secretKey.getEncoded());
		} catch(NoSuchAlgorithmException e){
			throw new RuntimeException("Error generating secret key", e);
		}
	}

	public String generateToken(Long id) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return Jwts.builder()
					.setClaims(claims)
					.setSubject(id.toString())
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000*60*15))
					.signWith(getKey(), SignatureAlgorithm.HS256)
					.compact();
	}
	
	public Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public Claims extractAllClaims (String token) {
		return Jwts.parser()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	public Long extractUserID(String token) {
		String userStrID = extractClaim(token, Claims :: getSubject);
		return Long.valueOf(userStrID);
	}
	
	public Date extractExpiration (String token) {
		return extractClaim(token, Claims :: getExpiration);
	}
	
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public boolean validateToken(String token, Long userID) {
		final Long extractedUserID = extractUserID(token);
		return (extractedUserID.equals(userID) && !isTokenExpired(token));
	}
	
}
