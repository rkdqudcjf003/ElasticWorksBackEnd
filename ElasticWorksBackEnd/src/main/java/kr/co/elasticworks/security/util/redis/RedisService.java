package kr.co.elasticworks.security.util.redis;

public interface RedisService {
	String getData(String key);

	void setData(String key, Object value);

	void setDataExpire(String key, Object value, long duration);

	void deleteData(String key);
}
