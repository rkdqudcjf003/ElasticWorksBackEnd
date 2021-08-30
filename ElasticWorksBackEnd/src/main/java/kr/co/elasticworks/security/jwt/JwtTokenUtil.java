package kr.co.elasticworks.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kr.co.elasticworks.api.domain.User;

import java.util.function.Function;

@Component
public class JwtTokenUtil {
	private Logger log = LogManager.getLogger(this.getClass());

	private Key getSigningKey(String secretKey) {
		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public Claims getAllClaimsFromToken(String token) throws ExpiredJwtException {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey(JwtProperties.JWT_SECRET_KEY))
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateAccessToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, user.getUserId(), JwtProperties.ACTK_EXPIRATION_TIME);
	}

	public String generateRefreshToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, user.getUserId(), JwtProperties.RFTK_EXPIRATION_TIME);
	}

	public String doGenerateToken(Map<String, Object> claims, String userId, long expireTime) {
		String jwt = Jwts.builder()
				.setClaims(claims)
				.setSubject(userId)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expireTime))
				.signWith(getSigningKey(JwtProperties.JWT_SECRET_KEY), SignatureAlgorithm.HS256).compact();
		log.info(jwt);
		return jwt;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userId = getUsernameFromToken(token);

		return (userId.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
