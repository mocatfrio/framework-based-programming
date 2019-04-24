package com.mocatfrio.service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;  
import io.jsonwebtoken.*;

@Service
public class SecurityServiceImpl implements SecurityService {
	// hardcode the secret key in this class
	public static final String secretKey = "4C8kum4LxyKWYLM78sKdXrzbBjDCFyfX";
	
	@Override
	public String createToken(String subject, long ttlMillis) {
		if (ttlMillis <= 0) {
			throw new RuntimeException("Expiry time must be greater than Zero :["+ttlMillis+"] ");
		}
		
		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		
		// Sign JWT with our ApiKey secret
		// Convert the secret key to a byte array 
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);

		// Pass the secret key in bytes to SecretKeySpec Java class to get a signingKey
		// While creating a signing key, we use JCA (Java Cryptography Architecture)
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		
		// Build the JWT token with signature algorithm and signing key above
		JwtBuilder builder = Jwts.builder().setSubject(subject).signWith(signatureAlgorithm, signingKey);
		
		// Set the expiration time
		long nowMillis = System.currentTimeMillis();
		builder.setExpiration(new Date(nowMillis + ttlMillis));
		
		return builder.compact();
	}
	
	@Override
	public String getSubject(String token) {
		Claims claims = Jwts.parser()
						.setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
						.parseClaimsJws(token)
						.getBody();
		return claims.getSubject();
	}
}
