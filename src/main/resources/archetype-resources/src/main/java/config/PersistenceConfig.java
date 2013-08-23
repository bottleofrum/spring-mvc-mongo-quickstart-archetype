package ${package}.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

@Configuration
public class PersistenceConfig {

	@Value("${redis.host}")
	private String host;

	@Value("${redis.port}")
	private int port;

	@Value("${redis.password}")
	private String password;

	@Value("${redis.db}")
	private int db;


	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(host);
		jedisConnectionFactory.setPort(port);
		jedisConnectionFactory.setPassword(password);
		jedisConnectionFactory.setDatabase(db);
		return jedisConnectionFactory;
	}

	@Bean
	public StringRedisTemplate redisTemplate(){
		return new StringRedisTemplate(jedisConnectionFactory());
	}

	@Bean
	public RedisAtomicLong accountIdGenerator(){
		return new RedisAtomicLong("account:id", jedisConnectionFactory());
	}

}
