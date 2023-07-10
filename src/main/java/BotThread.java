import java.sql.SQLException;

public class BotThread implements Runnable {
    private DBManager dbManager;
    private int numeroPreventivi;

    public void startConnection() throws RuntimeException, SQLException {
        DBManager dbManager = new DBManager();
        this.dbManager = dbManager;
    }
    /**
     Questo metodo permette all'utente di visualizzare il suo numero di preventivo tramite l'utilizzo di un thread a parte
     */
    @Override
    public void run() {
        try {
            startConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while (true) { //Thread (this)
            try {
                this.numeroPreventivi = this.dbManager.numeroPreventivi();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(3600000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}