package top.felixu.v3.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author felixu
 * @datetime 2018/4/3 06:52
 */
public class StatementHandler {

    private ResultSetHandler resultSetHandler;
    private ParameterHandler parameterHandler;

    public StatementHandler() {
        resultSetHandler = new ResultSetHandler();
        parameterHandler = new ParameterHandler();
    }

    public <T> T query(String sql, Class<T> returnType, Integer parameter) {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            parameterHandler.setParameters(statement, parameter);
            statement.execute();
            return resultSetHandler.handler(statement.getResultSet(), returnType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8" +
                "&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String password = "felix19920320";
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
