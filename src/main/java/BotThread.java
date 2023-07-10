import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


public class BotThread implements Runnable {
    private DBManager dbManager;
    private BotTelegram botTelegram;
    private Update update;

    public BotThread(BotTelegram botTelegram, Update update) {
        this.botTelegram = botTelegram;
        this.update = update;
    }

    public void startConnection() throws RuntimeException, SQLException {
        DBManager dbManager = new DBManager();
        this.dbManager = dbManager;
        dbManager.getConnection();
    }

    public void startThread() {
        Thread thread = new Thread(this);
        try {
            startConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        thread.start();
    }

    /**
     * Questo metodo permette all'utente di visualizzare il suo numero di preventivo tramite l'utilizzo di un thread a parte
     */
    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        while (true) {
            //System.out.println("ciao");
            //if (hour == 20 && minute == 20) {
                //if (this.update != null) {
                  //System.out.println("ciao");
                    if (this.update.hasMessage() || this.update.hasCallbackQuery()) {
                    //System.out.println("ciao");
                    SendMessage messaggioOfferta = new SendMessage();
                    messaggioOfferta.setChatId(this.update.getMessage().getChatId());
                    try {
                        ResultSet offertaDelGiorno = this.dbManager.offertaDelGiorno();
                        while(offertaDelGiorno.next()) {
                            Automobile automobileInOfferta = new Automobile(offertaDelGiorno.getString("targa"), offertaDelGiorno.getString("nome_modello"), offertaDelGiorno.getString("alimentazione"), offertaDelGiorno.getString("colore"), offertaDelGiorno.getString("condizione"), offertaDelGiorno.getInt("prezzo"), offertaDelGiorno.getString("tipo_di_cambio"), offertaDelGiorno.getString("materiale_cerchione"));
                            messaggioOfferta.setText(automobileInOfferta.toString());
                        }
                        this.botTelegram.execute(messaggioOfferta);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
//}