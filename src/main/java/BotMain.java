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
            while(true) {
                System.out.println(bot.getUpdate());
                if(bot.getUpdate() != null) {
                    System.out.println("ciao");
                    break;
                }
            }
            System.out.println("ciao");
            BotThread botThread = new BotThread(bot, bot.getUpdate());
            botThread.startThread();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


