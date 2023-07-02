import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BotMain {
    public static void main(String args[]) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            BotTelegram bot = new BotTelegram();
            botsApi.registerBot(bot);
            bot.startConnection();
            bot.setCommands();
            bot.setDescription();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


