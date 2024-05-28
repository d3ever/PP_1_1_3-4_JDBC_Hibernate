package sexy.criss.task.core.jdbc.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import sexy.criss.task.core.jdbc.model.User;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Util {
    private static Connection connection;
    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        if(connection != null) return connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dataBase", "root", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory != null) return sessionFactory;
        Configuration configuration = new Configuration();
        Properties properties = new Properties();

        properties.put(AvailableSettings.DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.put(AvailableSettings.URL, "jdbc:mysql://localhost:3306/dataBase");
        properties.put(AvailableSettings.USER, "root");
        properties.put(AvailableSettings.PASS, "password");

        configuration.setProperties(properties);
        configuration.addAnnotatedClass(User.class);

        return sessionFactory = configuration.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());
    }

    public static boolean execute(String sql, Object... args) {
        boolean result = false;
        if(sql == null || sql.isEmpty()) return false;
        if(args.length > 0) {
            sql = format(sql, args);
        }
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            result = ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static PreparedStatement prepared(String sql, Object... args) throws SQLException {
        if(sql == null || sql.isEmpty()) return null;
        sql = format(sql, args);
        return getConnection().prepareStatement(sql);
    }

    public static String format(String s, Object... args) {
        return args.length > 0 ? String.format(s, args) : s;
    }

}