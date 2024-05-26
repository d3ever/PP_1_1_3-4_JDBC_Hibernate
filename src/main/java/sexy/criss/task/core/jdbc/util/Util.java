package sexy.criss.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static Connection connection;

    public static Connection getConnection() {
        if(connection != null) return connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dataBase", "root", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
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