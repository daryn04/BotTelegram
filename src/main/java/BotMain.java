import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.sql.SQLException;

public class BotMain {
    public static void main(String args[]) throws SQLException {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            BotTelegram bot = new BotTelegram();
            botsApi.registerBot(bot);
            bot.startConnection();
            bot.inizializzaBot();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        Runnable botThread = new BotThread();
        Thread thread = new Thread(botThread);
        thread.start();
        }
    }
}


