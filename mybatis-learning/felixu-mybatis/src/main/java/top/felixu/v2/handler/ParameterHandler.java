package top.felixu.v2.handler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author felixu
 * @datetime 2018/4/3 06:52
 */
public class ParameterHandler {

    public void setParameters(PreparedStatement ps, Integer parameter) throws SQLException {
        ps.setInt(1, parameter);
    }
}
