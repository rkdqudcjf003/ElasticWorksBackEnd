package kr.co.elasticworks.security.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisServiceImpl implements RedisService {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;


	public String getData(String key) {
//		ValueOperations<String, Object> valueOperations = (String) redisTemplate.opsForValue().get(key);
		String valueOperations = (String) redisTemplate.opsForValue().get(key);
		return valueOperations;
	}

	public void setData(String key, Object value) {
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(key, value);
	}

	public void setDataExpire(String key, Object value, long duration) {
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		Duration expireDuration = Duration.ofSeconds(duration);
		valueOperations.set(key, value, expireDuration);
	}

	public void deleteData(String key) {
		redisTemplate.delete(key);
	}

}
