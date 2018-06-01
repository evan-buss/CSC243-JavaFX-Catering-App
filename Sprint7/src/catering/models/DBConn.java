//This class will make it easier to access the database

package catering.models;

import java.sql.*;


public class DBConn {

    public static Connection conn = null;
    public static Statement stmt = null;


    public static ResultSet getResultSet(String query) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:javajoint.db");
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            return stmt.executeQuery(query);

//            stmt.close();
//            conn.commit();
//            conn.close();
//            return rs;
        } catch (Exception ex) {
            System.err.println("resultset");
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            return null;
        }
    }

    public static int getLastID(String idName, String tableName) {
        try {
            if (conn.isClosed()) {
                System.out.println("connection is closed");
            } else {
                System.out.println("connection is open");
                stmt.close();
                conn.commit();
                conn.close();
            }
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:javajoint.db");
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String query = "SELECT MAX(" + idName + ") as last_id from " + tableName + ";";
            ResultSet rs = stmt.executeQuery(query);
            int num = rs.getInt("last_id");

            conn.commit();
            closeAll();
//            stmt.close();
//            conn.close();
            return num;
        } catch (Exception ex) {
            System.err.println("getLastID");
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            return -1;
        }
    }


    /**
     * Used for submitting a query that doesn't need to return a value;
     *
     * @param query String SQL formatted query
     */
    public static void query(String query) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:javajoint.db");
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.executeUpdate(query);

            conn.commit();
            closeAll();
//            stmt.close();
//            conn.close();
        } catch (Exception ex) {
            System.err.println("query");
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static void closeAll() {
        try {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* ignored */}
            }
        } catch (Exception ex) {
            System.err.println("closeall error");
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }
}
