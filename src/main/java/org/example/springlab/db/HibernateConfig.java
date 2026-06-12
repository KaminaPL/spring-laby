package org.example.springlab.db;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import org.example.springlab.models.Rental;
import org.example.springlab.models.User;
import org.example.springlab.models.Vehicle;
import org.example.springlab.models.VehicleCategoryConfig;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateConfig {

    @Getter
    private static final SessionFactory sessionFactory;

    static {
        try {
            Dotenv dotenv = Dotenv.load();
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(User.class)
                    .addAnnotatedClass(Vehicle.class)
                    .addAnnotatedClass(Rental.class)
                    .addAnnotatedClass(VehicleCategoryConfig.class)
                    .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                    .setProperty("hibernate.connection.url", dotenv.get("DB_URL"))
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                    .setProperty("hibernate.hbm2ddl.auto", "update");
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable e) {
            throw  new ExceptionInInitializerError("Error occurred while initializing Hibernate: " + e);
        }
    }

}
