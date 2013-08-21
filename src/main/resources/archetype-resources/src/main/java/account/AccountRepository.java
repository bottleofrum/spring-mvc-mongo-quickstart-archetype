package test.account;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@Transactional(readOnly = true)
public class AccountRepository {

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private MongoOperations mongoOperations;

    @Transactional
    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        mongoOperations.save(account);
        return account;
    }

    public Account findByEmail(String email) {
        return mongoOperations.findOne(query(where("email").is(email)),Account.class);
    }


}
