import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
/**
 *
 * @author daryn
 */
public class DBManager {
    private String JDBC_DRIVER;
    private Connection conn;

    public DBManager() {
        this.JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    }

    public void getConnection() throws SQLException {
        ParserXML parserXML = new ParserXML();
        parserXML.readConfigFile("./config.xml");
        try {
            Class.forName(this.getJDBC_DRIVER());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Connessione al database..."); //jdbc:mariadb://localhost:3306/Java_Project
        this.conn = DriverManager.getConnection(
                   "jdbc:mariadb://" + parserXML.getHost() + ":" + parserXML.getPort() + "/" + parserXML.getName(), parserXML.getUsername(), parserXML.getPassword());
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

    public ResultSet prezzoModello(String modello) throws SQLException {
        String getPrezzo = "SELECT * FROM modello WHERE nome ='" + modello +"'";
        ResultSet rs;
        try (Statement stmt = this.conn.createStatement()) {
            rs = stmt.executeQuery(getPrezzo);
        }
        return rs;
    }

    public void inserisciPreventivo(int userId, Automobile automobile, String date) throws SQLException {
        String preventivo = "INSERT INTO preventivo_telegram(id_user, data_preventivo, nome_modello, alimentazione, colore, prezzo, tipo_di_cambio, materiale_cerchione) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        ResultSet rs;
        try (PreparedStatement stmt = this.conn.prepareStatement(preventivo)) {
            stmt.setInt(1, userId);
            stmt.setDate(2, Date.valueOf(date));
            stmt.setString(3, automobile.getNomeModello());
            stmt.setString(4, automobile.getAlimentazione());
            stmt.setString(5, automobile.getColore());
            stmt.setInt(6, automobile.getPrezzo());
            stmt.setString(7, automobile.getTipoDiCambio());
            stmt.setString(8, automobile.getMaterialeCerchione());
            stmt.execute();
        }
    }
    public ResultSet visualizzaModelli(String costruttore) throws SQLException {
        String modello = "SELECT nome FROM modello WHERE costruttore ='" + costruttore +"'";
        ResultSet rs;
        try (Statement stmt = this.conn.createStatement()) {
            rs = stmt.executeQuery(modello);
        }
        return rs;
    }

    public ArrayList<Automobile> visualizzaAuto(String nomeModello) throws SQLException {
        String auto = "SELECT * FROM automobile WHERE nome_modello ='" + nomeModello + "'";
        ArrayList<Automobile> automobili = new ArrayList<>();
        ResultSet rs;
        try (Statement stmt = this.conn.createStatement()) {
            rs = stmt.executeQuery(auto);
        }
        while(rs.next()){
            Automobile automobile = new Automobile(rs.getString("targa"), rs.getString("nome_modello"), rs.getString("alimentazione"), rs.getString("colore"), rs.getString("condizione"), rs.getInt("prezzo"), rs.getString("tipo_di_cambio"), rs.getString("materiale_cerchione"));
            automobili.add(automobile);
        }
        return automobili;
    }

    public ArrayList<Modello> visualizzaCaratteristicheModello(String nomeModello) throws SQLException {
        String tuttiModelli = "SELECT * FROM modello WHERE nome ='" + nomeModello + "'";
        ArrayList<Modello> modelli = new ArrayList<>();
        ResultSet rs;
        try (Statement stmt = this.conn.createStatement()) {
            rs = stmt.executeQuery(tuttiModelli);
        }
        while(rs.next()){
            Modello modello = new Modello(rs.getString("nome"), rs.getString("costruttore"), rs.getInt("consumo"), rs.getInt("cilindrata"), rs.getInt("potenza"), rs.getInt("cavalli"), rs.getInt("n_porte"), rs.getInt("n_posti"), rs.getInt("velocita_max"), rs.getInt("prezzo_listino"));
            modelli.add(modello);
        }
        return modelli;
    }

    public boolean isElectric(String nomeModello) throws SQLException {
        String modelloElettrico = "SELECT * FROM modello WHERE nome ='" + nomeModello + "'";
        ResultSet rs;
        boolean elettrica = false;
        try (Statement stmt = this.conn.createStatement()) {
            rs = stmt.executeQuery(modelloElettrico);
            while (rs.next()) {
                elettrica = rs.getBoolean("elettrica");
            }
        }
        return elettrica;
    }

    public ModelloElettrico visualizzaModelloElettrico(String nomeModello) throws SQLException {
        String modelloElettricoCaratteristiche = "SELECT * FROM modello JOIN modello_elettrico ON nome ='" + nomeModello +"'";
        ResultSet rs;
        ModelloElettrico modelloElettrico = null;
        try (Statement stmt = this.conn.createStatement()) {
            rs = stmt.executeQuery(modelloElettricoCaratteristiche);
            while (rs.next()) {
                modelloElettrico = new ModelloElettrico(rs.getInt("batteria_kWh"), rs.getInt("autonomia"), rs.getInt("tempo_ricarica"), rs.getString("nome"), rs.getString("costruttore"), rs.getInt("consumo"), rs.getInt("cilindrata"), rs.getInt("potenza"), rs.getInt("cavalli"), rs.getInt("n_posti"), rs.getInt("n_porte"), rs.getInt("velocita_max"), rs.getInt("prezzo_listino"));
            }
        }
        return modelloElettrico;
    }

    public int numeroPreventivi() throws SQLException {
        String numeroPreventivo = "SELECT COUNT(id_preventivo) FROM preventivo_telegram";
        ResultSet rs;
        int numeroPreventivi = 0;
        try (Statement stmt = this.conn.createStatement()) {
            rs = stmt.executeQuery(numeroPreventivo);
            while (rs.next()) {
                numeroPreventivi = rs.getInt("count(id_preventivo)");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return numeroPreventivi;
    }
    public String getJDBC_DRIVER() {
        return JDBC_DRIVER;
    }

}
