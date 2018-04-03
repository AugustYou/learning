package top.felixu.v1;

import java.sql.*;

/**
 * @author felixu
 * @datetime 2018/4/1 22:52
 */
public class SimpleExecutor implements Executor {

    @Override
    public <T> T query(String statement, String parameter) {
        /**
         * JDBC
         */
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8" +
                "&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String password = "felix19920320";

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Person person = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, Integer.parseInt(parameter));
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                person = new Person();
                person.setId(rs.getInt(1));
                person.setAddress(rs.getString(2));
                person.setAge(rs.getInt(3));
                person.setName(rs.getString(4));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != conn) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (null != preparedStatement) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return (T) person;
    }
}
