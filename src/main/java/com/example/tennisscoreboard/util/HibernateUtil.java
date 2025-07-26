package com.example.tennisscoreboard.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Properties;


public class HibernateUtil {

    private static final HikariDataSource dataSource;
    private static final SessionFactory sessionFactory;

    static {
        try {

            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5433/tennis_db"); // DB URL
            hikariConfig.setUsername("postgres");
            hikariConfig.setPassword("password");
            hikariConfig.setDriverClassName("org.postgresql.Driver");
            hikariConfig.setMaximumPoolSize(10);
            hikariConfig.setMinimumIdle(2);
            hikariConfig.setIdleTimeout(30000);

            dataSource = new HikariDataSource(hikariConfig);


            Properties hibernateProps = new Properties();
            hibernateProps.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            hibernateProps.put("hibernate.hbm2ddl.auto", "update");
            hibernateProps.put("hibernate.show_sql", "true");
            hibernateProps.put("hibernate.format_sql", "true");

            Configuration configuration = new Configuration();
            configuration.addProperties(hibernateProps);


            configuration.addAnnotatedClass(com.example.tennisscoreboard.model.Player.class);
            configuration.addAnnotatedClass(com.example.tennisscoreboard.model.Match.class);


            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .applySetting("hibernate.connection.datasource", dataSource)
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (Exception ex) {
            throw new ExceptionInInitializerError("Initial SessionFactory creation failed: " + ex);
        }
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }


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


}
