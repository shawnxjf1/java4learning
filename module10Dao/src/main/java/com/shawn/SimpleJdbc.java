package com.shawn;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *  用于演示原生jdbc程序如何操作数据库，因为现在都是mybatis做了封装<br>
 *  了解jdbc历史有助于了解mybatis等做了什么封装和优化，为什么被大家接受<br>
 *   本测试用例执行通过2019年8月12日<br>
 */
public class SimpleJdbc {

    String userName = "root";
    String password = "Huawei12#$$";
    String dbms = "mysql";
    String serverName = "pigx-mysql";
    String portNumber = "3306";
    String dbName = "pigxx_config";
    Connection connection = null;

    public static void main(String[] args) {
        new SimpleJdbc().operate();
    }

    void operate() {
        try {

            connection = connect();

//            createTable();
//
//            populateTable();

            viewTable();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {connection.close();}
            catch (SQLException e) {e.printStackTrace();}
        }
    }

    /**
     * Create connection
     *
     * @return
     * @throws SQLException
     */
    Connection connect() throws SQLException {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        String url = "jdbc:" + dbms + "://" + serverName + ":" + portNumber
                + "/" + dbName;
        conn = DriverManager.getConnection(url, connectionProps);

        System.out.println("Connected to database");
        return conn;
    }

    /**
     * Create table SUPPLIERS
     *
     * @throws SQLException
     */
    public void createTable() throws SQLException {
        String createString = "create table " + dbName + ".SUPPLIERS "
                + "(SUP_ID integer NOT NULL, "
                + "SUP_NAME varchar(40) NOT NULL, "
                + "STREET varchar(40) NOT NULL, "
                + "CITY varchar(20) NOT NULL, " + "STATE char(2) NOT NULL, "
                + "ZIP char(5), " + "PRIMARY KEY (SUP_ID))";

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(createString);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * Populate table SUPPLIERS
     *
     * @throws SQLException
     */
    public void populateTable() throws SQLException {

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(
                    "insert into " + dbName +".SUPPLIERS values(49, 'Superior Coffee', '1 Party Place', 'Mendocino', 'CA', '95460')");
            stmt.executeUpdate(
                    "insert into " + dbName +".SUPPLIERS values(101, 'Acme, Inc.', '99 Market Street', 'Groundsville', 'CA', '95199')");
            stmt.executeUpdate(
                    "insert into " + dbName +".SUPPLIERS values(150, 'The High Ground', '100 Coffee Lane', 'Meadows', 'CA', '93966')");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    /**
     * Query table SUPPLIERS
     *
     * @param con
     * @throws SQLException
     */
    public void viewTable() throws SQLException {
        System.out.println("view table....");
        Statement stmt = null;
        String query = "select id,data_id, group_id, src_ip from config_info";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                long id = rs.getLong("id");
                String data_id = rs.getString("data_id");
                String group_id = rs.getString("group_id");
                String src_ip = rs.getString("src_ip");
                System.out.println( "config_info:(" + id + "): "
                        + data_id + ", " + group_id + ", " + src_ip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}