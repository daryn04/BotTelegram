import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author daryn
 */
public class DBManager {
    private String JDBC_DRIVER;
    private String DB_URL;

    //  Database credentials
    private String USER;
    private String PASS;
    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     */
    public DBManager(){
        this.JDBC_DRIVER = "org.mariadb.jdbc.Driver";
        this.DB_URL = "jdbc:mariadb://localhost:3306/DB_Project";
        this.USER = "daryn";
        this.PASS = "daryn2002";
    }

    public String getJDBC_DRIVER() {
        return JDBC_DRIVER;
    }

    public String getDB_URL() {
        return DB_URL;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASS() {
        return PASS;
    }

    /*
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/DB_Project", "root", "password");
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            /*
            stmt = conn.createStatement();
            String sql = "SELECT DISTINCT costruttore FROM modello";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
    */
}
