package com.example.tennisscoreboard.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Properties;

/**
 * Utility class for configuring Hibernate and HikariCP connection pool.
 */
public class HibernateUtil {

    private static final HikariDataSource dataSource;
    private static final SessionFactory sessionFactory;

    static {
        try {
            // 🔹 1. HikariCP Configuration
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5433/tennis_db"); // DB URL
            hikariConfig.setUsername("postgres");        // DB username
            hikariConfig.setPassword("password");        // DB password
            hikariConfig.setDriverClassName("org.postgresql.Driver"); // JDBC driver
            hikariConfig.setMaximumPoolSize(10);         // max connections in pool
            hikariConfig.setMinimumIdle(2);              // minimum idle connections
            hikariConfig.setIdleTimeout(30000);          // idle connection timeout (ms)

            dataSource = new HikariDataSource(hikariConfig);

            // 🔹 2. Hibernate Properties
            Properties hibernateProps = new Properties();
            hibernateProps.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            hibernateProps.put("hibernate.hbm2ddl.auto", "update"); // update schema automatically
            hibernateProps.put("hibernate.show_sql", "true");       // print SQL to console
            hibernateProps.put("hibernate.format_sql", "true");     // format SQL output

            Configuration configuration = new Configuration();
            configuration.addProperties(hibernateProps);

            // 🔹 3. Register annotated entity classes
            configuration.addAnnotatedClass(com.example.tennisscoreboard.model.Player.class);
            configuration.addAnnotatedClass(com.example.tennisscoreboard.model.Match.class);

            // 🔹 4. Build SessionFactory with HikariCP datasource
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .applySetting("hibernate.connection.datasource", dataSource)
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (Exception ex) {
            throw new ExceptionInInitializerError("Initial SessionFactory creation failed: " + ex);
        }
    }

    /**
     * Returns the Hibernate SessionFactory.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Closes SessionFactory and HikariCP DataSource when application stops.
     */
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("SessionFactory closed.");
        }
        if (dataSource != null) {
            dataSource.close();
            System.out.println("HikariDataSource closed.");
        }
    }

    // Test connection (can be used for debugging)
/*
    public static void main(String[] args) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        System.out.println("Hibernate + HikariCP + PostgreSQL connected successfully!");
        session.close();
        shutdown(); // close resources
    }
*/
}
