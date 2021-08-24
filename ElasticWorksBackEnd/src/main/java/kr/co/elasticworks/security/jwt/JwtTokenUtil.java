package kr.co.elasticworks.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import kr.co.elasticworks.api.model.UserVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
//	String jwtSecretKey = JwtProperties.JWT_SECRET_KEY;

//	private Key jwtSecretKey;
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

	public String generateAccessToken(UserVO user) {
		return doGenerateToken(user.getUserId(), JwtProperties.ACTK_EXPIRATION_TIME);
	}

	public String generateRefreshToken(UserVO user) {
		return doGenerateToken(user.getUserId(), JwtProperties.RFTK_EXPIRATION_TIME);
	}

	public String doGenerateToken(String userId, long expireTime) {

		Claims claims = Jwts.claims();
		claims.put("userId", userId);

		String jwt = Jwts.builder()
				.setSubject(userId)
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expireTime))
				.signWith(getSigningKey(JwtProperties.JWT_SECRET_KEY), SignatureAlgorithm.HS256).compact();

		System.out.println(Jwts.parserBuilder());
		return jwt;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userId = getUsernameFromToken(token);

		return (userId.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
