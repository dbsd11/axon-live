/*
 * Copyright (c) 2010-2016. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package group.bison.axon.live.config;

import org.axonframework.common.jdbc.ConnectionProvider;
import org.axonframework.common.jdbc.UnitOfWorkAwareConnectionProviderWrapper;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.jdbc.EventSchema;
import org.axonframework.eventsourcing.eventstore.jdbc.EventTableFactory;
import org.axonframework.eventsourcing.eventstore.jdbc.HsqlEventTableFactory;
import org.axonframework.eventsourcing.eventstore.jdbc.JdbcEventStorageEngine;
import org.axonframework.modelling.saga.repository.SagaStore;
import org.axonframework.modelling.saga.repository.jdbc.GenericSagaSqlSchema;
import org.axonframework.modelling.saga.repository.jdbc.JdbcSagaStore;
import org.axonframework.modelling.saga.repository.jdbc.SagaSqlSchema;
import org.axonframework.spring.jdbc.SpringDataSourceConnectionProvider;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactoryBean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = {
                "group.bison.axon.live.*"
        },
        transactionManagerRef = "jpaTransactionManager",
        entityManagerFactoryRef = "entityManagerFactoryBean"
)
public class CQRSH2DBConfig {

    @Bean
    public EmbeddedDatabaseFactoryBean dataSource() {
        EmbeddedDatabaseFactoryBean embeddedDatabaseFactoryBean = new EmbeddedDatabaseFactoryBean();
        embeddedDatabaseFactoryBean.setDatabaseType(EmbeddedDatabaseType.H2);

        return embeddedDatabaseFactoryBean;
    }

    @Bean(name = "entityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(DataSource dataSource) {
        final LocalContainerEntityManagerFactoryBean container = new LocalContainerEntityManagerFactoryBean();
        container.setDataSource(dataSource);
        container.setPackagesToScan("group.bison.axon.live.entity", "org.axonframework.eventhandling.tokenstore", "org.axonframework.modelling.saga.repository.jpa", "org.axonframework.eventsourcing.eventstore.jpa");

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(true);
        container.setJpaVendorAdapter(adapter);

        container.setJpaProperties(jpaProps());
        return container;
    }

    @Bean(name = "jpaTransactionManager")
    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean(name = "axonJpaTransactionManager")
    public SpringTransactionManager axonJpaTransactionManager(PlatformTransactionManager jpaTransactionManager) {
        return new SpringTransactionManager(jpaTransactionManager, new DefaultTransactionDefinition());
    }

    private Properties jpaProps() {
        final Properties p = new Properties();
        p.setProperty("hibernate.show_sql", "true");
        p.setProperty("hibernate.generate_statistics", "false");
        p.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return p;
    }

    //    @Bean
    public ConnectionProvider connectionProvider(DataSource dataSource) {
        return new UnitOfWorkAwareConnectionProviderWrapper(new SpringDataSourceConnectionProvider(dataSource));
    }

    //    @Bean
    public JdbcEventStorageEngine eventStorageEngine(ConnectionProvider connectionProvider, SpringTransactionManager transactionManager) {
        return JdbcEventStorageEngine.builder()
                .connectionProvider(connectionProvider)
                .transactionManager(transactionManager)
                .build();
    }

    //    @Bean
    public EventStore eventStore(ConnectionProvider connectionProvider, SpringTransactionManager transactionManager) {
        return EmbeddedEventStore.builder()
                .storageEngine(eventStorageEngine(connectionProvider, transactionManager))
                .build();
    }

    //    @Bean
    public EventTableFactory eventSchemaFactory() {
        return HsqlEventTableFactory.INSTANCE;
    }

    //    @Bean
    public EventSchema eventSchema() {
        return new EventSchema();
    }

    //    @Bean
    public SagaSqlSchema sagaSqlSchema() {
        return new GenericSagaSqlSchema();
    }

    //    @Bean
    public SagaStore<Object> sagaRepository(DataSource dataSource) {
        return JdbcSagaStore.builder()
                .dataSource(dataSource)
                .sqlSchema(sagaSqlSchema())
                .build();
    }

    @Component
    public static class H2 {
        private org.h2.tools.Server webServer;

        private org.h2.tools.Server tcpServer;

        @EventListener(org.springframework.context.event.ContextRefreshedEvent.class)
        public void start() throws java.sql.SQLException {
            this.webServer = org.h2.tools.Server.createWebServer("-webPort", "8082", "-tcpAllowOthers").start();
            this.tcpServer = org.h2.tools.Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
        }

        @EventListener(org.springframework.context.event.ContextClosedEvent.class)
        public void stop() {
            this.tcpServer.stop();
            this.webServer.stop();
        }
    }
}
