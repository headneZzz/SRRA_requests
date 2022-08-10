package ru.gosarhro.SRRA_requests.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "personalEntityManagerFactory",
        transactionManagerRef = "personalTransactionManager",
        basePackages = "ru.gosarhro.SRRA_requests.repository.personal_data"
)
public class PersonalDataConfig {

    @Bean(name = "personalDataSource")
    @ConfigurationProperties(prefix = "spring.personal-datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "personalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean personalEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("personalDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("ru.gosarhro.SRRA_requests.entity.personal_data")
                .persistenceUnit("personal")
                .build();
    }

    @Bean(name = "personalTransactionManager")
    public PlatformTransactionManager personalTransactionManager(
            @Qualifier("personalEntityManagerFactory") EntityManagerFactory personalEntityManagerFactory
    ) {
        return new JpaTransactionManager(personalEntityManagerFactory);
    }
}
