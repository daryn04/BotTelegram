import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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
    private Connection conn;

    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     */
    public DBManager() throws SQLException {
        this.JDBC_DRIVER = "org.mariadb.jdbc.Driver";
        this.DB_URL = "jdbc:mariadb://localhost:3306/DB_Project";
        this.USER = "daryn";
        this.PASS = "daryn2002";
        try {
            Class.forName(this.getJDBC_DRIVER());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Connessione al database...");
        try {
            this.conn = DriverManager.getConnection(
                    this.getDB_URL(), this.getUSER(), this.getPASS());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Connessione al database effettuata con successo");
    }

    public ResultSet visualizzaMarchi() throws SQLException {
        String costruttore = "SELECT DISTINCT costruttore FROM modello";
        ResultSet rs;
        try (Statement stmt = this.conn.createStatement()) {
            rs = stmt.executeQuery(costruttore);
        }
        return rs;
    }

    public ArrayList<String> visualizzaTuttiModelli() throws SQLException {
        String tuttiModelli = "SELECT nome FROM modello";
        ResultSet rs;
        ArrayList<String> listaModelli = new ArrayList<>();
        try (Statement stmt = this.conn.createStatement()) {
            rs = stmt.executeQuery(tuttiModelli);
        }
        while(rs.next()){
            String modello = rs.getString("nome");
            listaModelli.add(modello);
        }
        return listaModelli;
    }
    public ResultSet visualizzaModelli(String costruttore) throws SQLException {
        String modello = "SELECT nome FROM modello WHERE costruttore ='" + costruttore +"'";
        ResultSet rs;
        try (Statement stmt = this.conn.createStatement()) {
            rs = stmt.executeQuery(modello);
        }
        return rs;
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

    /*
    public Connection startConnection(){
        try {
            Class.forName(this.getJDBC_DRIVER());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Connessione al database...");
        try {
            return this.conn = DriverManager.getConnection(
                    this.getDB_URL(), this.getUSER(), this.getPASS());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("Connessione al database effettuata con successo");
        //System.out.println(this.conn);
    }
*/

