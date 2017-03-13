package org.combo.cfg;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceCfg implements EnvironmentAware {

    private RelaxedPropertyResolver resolver;

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(resolver.getProperty("driverClassName"));
        hikariConfig.setJdbcUrl(resolver.getProperty("url"));
        hikariConfig.setUsername(resolver.getProperty("username"));
        hikariConfig.setPassword(resolver.getProperty("password"));

        hikariConfig.setAutoCommit(Boolean.parseBoolean(resolver.getProperty("autoCommit")));
        hikariConfig.setMaxLifetime(Long.parseLong(resolver.getProperty("maxLifetime")));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(resolver.getProperty("maximumPoolSize")));

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }

    @Bean(name="txManager")
    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource datasource) {
        DataSourceTransactionManager dsm = new DataSourceTransactionManager();
        dsm.setDataSource(datasource);
        return dsm;
    }

    @Override
    public void setEnvironment(Environment environment) {
        resolver = new RelaxedPropertyResolver(environment, "app.datasource.hikari.");
    }
}
