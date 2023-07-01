import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BotMain {
    public static void main(String args[]) {
        DBManager dbManager = new DBManager();
        Connection conn = null;
        try {
            Class.forName(dbManager.getJDBC_DRIVER());
            System.out.println("Connessione al database...");
            conn = DriverManager.getConnection(
                    dbManager.getDB_URL(), dbManager.getUSER(), dbManager.getPASS());
            System.out.println("Connessione al database effettuata con successo");
            try {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                BotTelegram bot = new BotTelegram(conn);
                botsApi.registerBot(bot);
            } catch (TelegramApiException e){
                e.printStackTrace();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn == null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}

