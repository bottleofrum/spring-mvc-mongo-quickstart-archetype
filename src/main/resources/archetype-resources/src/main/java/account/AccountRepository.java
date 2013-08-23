package ${package}.account;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class AccountRepository {

	@Inject
	private PasswordEncoder passwordEncoder;

	@Inject
	private StringRedisTemplate redisTemplate;

	@Inject
	private RedisAtomicLong accountIdGenerator;

	private String createAccountKey(Long id){
		return "user:"+id;
	}

	private String createAccountEmailKey(String email){
		return "user:email:"+email;
	}

	private Long generateUserId() {
		return redisTemplate.opsForValue().increment("user:id",1);
	}

	public Account save(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		account.setId(accountIdGenerator.incrementAndGet());
		String accountKey = createAccountKey(account.getId());
		storeAccount(accountKey, account);
		redisTemplate.opsForValue().set(createAccountEmailKey(account.getEmail()), accountKey);
		return account;
	}

	private void storeAccount(String accountKey, Account account) {
		BoundHashOperations<String,Object,Object> hashOperations = redisTemplate.boundHashOps(accountKey);
		hashOperations.put("id", account.getId().toString());
		hashOperations.put("email", account.getEmail());
		hashOperations.put("password", account.getPassword());
		hashOperations.put("role", account.getRole());
	}

	public Account findByEmail(String email) {
		String accountKey = redisTemplate.opsForValue().get(createAccountEmailKey(email));
		if(accountKey == null) return null;
		return readAccount(accountKey);
	}

	private Account readAccount(String accountKey) {
		BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps(accountKey);
		Long id = Long.parseLong(hashOperations.get("id"));
		String email = hashOperations.get("email");
		String password = hashOperations.get("password");
		String role = hashOperations.get("role");
		Account account = new Account(email,password,role);
		account.setId(id);

		return account;
	}
}
