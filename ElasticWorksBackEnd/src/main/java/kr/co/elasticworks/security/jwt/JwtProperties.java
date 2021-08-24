package kr.co.elasticworks.security.jwt;

public interface JwtProperties {
	String JWT_SECRET_KEY = "E234fLH745K1KdfgNO768jR12eEzXcbAfghnS576hyE123fCzXcRb354E123fTzxCvKsfdgvE34qWgYasDfb";// 시크릿키

	String ACCESS_TOKEN = "accessToken";
	long ACTK_EXPIRATION_TIME = 1000L * 60 * 1; // 10분 (밀리세컨드)
	int ACCOOKIE_EXPIRATION_TIME = 60 * 1; // 10분 (밀리세컨드)

	String REFRESH_TOKEN = "refreshToken";
	long RFTK_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7; // 7일 (밀리세컨드)
	int RFCOOKIE_EXPIRATION_TIME = 60 * 60 * 24 * 7; // 1분 (밀리세컨드)

	long REDIS_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7; // 7일 (밀리세컨드)

	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
}