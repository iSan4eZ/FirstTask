package FirstTask.dbConfings;

import FirstTask.transaction.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EnableCassandraRepositories(repositoryBaseClass = TransactionRepository.class)
public class CassandraDBConfig extends AbstractCassandraConfiguration {

    private String propertiesName = "cassandra";

    @Autowired
    private Environment env;

    @Bean
    @Override
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(env.getProperty(propertiesName + ".contact-points"));
        cluster.setPort(Integer.parseInt(env.getProperty(propertiesName + ".port")));
        return cluster;
    }

    @Override
    protected String getKeyspaceName() {
        return env.getProperty(propertiesName + ".keyspace-name");
    }

    @Bean
    @Override
    public CassandraMappingContext cassandraMapping() throws  ClassNotFoundException {
        return new CassandraMappingContext();
    }

}
