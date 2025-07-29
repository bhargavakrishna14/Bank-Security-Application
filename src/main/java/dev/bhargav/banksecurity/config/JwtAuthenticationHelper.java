package dev.bhargav.banksecurity.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtAuthenticationHelper {

	@Value("${application.security.jwt.secret-key}")
	private String secretKey;

	@Value("${application.security.jwt.expiration}")
	private long jwtTokenValidity; // one day in millis

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> function) {
		final Claims claims = extractAllClaims(token);
		return function.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parser()
				.verifyWith(getSignInKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public boolean isNotTokenValid(
			String token,
			UserDetails userDetails) {
		return !isTokenValid(token, userDetails);
	}

	public boolean isTokenValid(
			String token,
			UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())
				&& !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return getExpiryDate(token).before(new Date());
	}

	private Date getExpiryDate(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public String generateToken(UserDetails user) {
		return generateToken(user, new HashMap<>());
	}

	public String generateToken(
			UserDetails user,
			Map<String, Object> claims) {
		return buildToken(user, claims);
	}

	private String buildToken(
			UserDetails user,
			Map<String, Object> claims) {

		return Jwts
				.builder()
				.claims(claims)
				.subject(user.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + jwtTokenValidity))
				.signWith(getSignInKey())
				.compact();

	}

	private SecretKey getSignInKey() {
//		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		if (keyBytes.length < 32) {
			throw new IllegalArgumentException("Secret key must be at least 32 bytes for HS256 and 64 bytes for HS512.");
		}
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
