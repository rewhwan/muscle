package controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtill {
    public static final String DRIVER = "org.mariadb.jdbc.Driver";
    public static final String URL = "jdbc:mariadb://jbstv.synology.me:3307/muscle";

    public static Connection getConnection() throws Exception{
        Class.forName(DRIVER);
        Connection con = DriverManager.getConnection(URL, "rewhwan", "Lost6class!");
        return con;
    }
}
