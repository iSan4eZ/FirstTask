package firsttask.confings;

import firsttask.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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
        cluster.setKeyspaceCreations(getKeyspaceCreations());
        cluster.setContactPoints(env.getProperty(propertiesName + ".contact-points"));
        cluster.setPort(Integer.parseInt(env.getProperty(propertiesName + ".port")));
        return cluster;
    }

    @Override
    protected String getKeyspaceName() {
        return env.getProperty(propertiesName + ".keyspace-name");
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return Collections.singletonList(CreateKeyspaceSpecification.createKeyspace(getKeyspaceName())
                .ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .withSimpleReplication());
    }

    @Override
    protected List<String> getStartupScripts() {
        List<String> scripts = new ArrayList<>();
        scripts.add("CREATE TABLE IF NOT EXISTS " + getKeyspaceName() + ".money_events_fix (" +
                "id uuid PRIMARY KEY," +
                "amount double," +
                "timestamp bigint," +
                "username text" +
                ");");
        scripts.add("CREATE TABLE IF NOT EXISTS " + getKeyspaceName() + ".money_events (" +
                "username text PRIMARY KEY," +
                "amount double," +
                "timestamp bigint" +
                ");");
        scripts.add("CREATE TABLE IF NOT EXISTS " + getKeyspaceName() + ".money_events_v2 (" +
                "username text," +
                "amount double," +
                "timestamp bigint," +
                "PRIMARY KEY(username, timestamp)" +
                ") WITH CLUSTERING ORDER BY (timestamp ASC);");
        return scripts;
    }

    @Bean
    @Override
    public CassandraMappingContext cassandraMapping() throws  ClassNotFoundException {
        return new CassandraMappingContext();
    }

}