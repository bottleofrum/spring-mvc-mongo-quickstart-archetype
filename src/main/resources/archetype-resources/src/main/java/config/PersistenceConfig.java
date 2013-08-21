package ${package}.config;

import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class PersistenceConfig {

    @Value("${mongo.user}")
    private String user;

    @Value("${mongo.password}")
    private String password;

    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.port}")
    private int port;

    @Value("${mongo.db}")
    private String db;


    @Bean
    public MongoFactoryBean mongoFactoryBean() {
        MongoFactoryBean mongoFactoryBean = new MongoFactoryBean();
        mongoFactoryBean.setHost(host);
        mongoFactoryBean.setPort(port);
        return mongoFactoryBean;
    }

    @Bean
    public MongoDbFactory mongoDbFactory(Mongo mongo) {
        return new SimpleMongoDbFactory(mongo,db,new UserCredentials(user,password));
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }

}
