package me.entropyx.dao;

/**
 * Created by shaosong on 14/12/20.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtil {

    private String url = "jdbc:mysql://localhost:3306/AutoDatabase";
    private String username = "root";
    private String password = "0620";
    private String driver="com.mysql.jdbc.Driver";
    private Connection con;


    public ConnectionUtil(String url, String username, String password) {
        super();
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public ConnectionUtil() {
        super();
    }

    public void dbConnect() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeSql(String sql) {
        try {
            Statement sta = con.createStatement();
            sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ResultSet executeQuerySql(String sql) {
        ResultSet rs = null;
        try {
            Statement sta = con.createStatement();
            rs = sta.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void dbClose() {
        if (con != null) {
            try {
                if (!con.isClosed())
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
