import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
//import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

//import java.io.File;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BotTelegram extends TelegramLongPollingBot {
    private Connection conn;
    //private String logoPath = "./Logo.jpeg";

    public BotTelegram(Connection conn){
        this.conn = conn;
    }
    @Override
    public String getBotUsername() {
        return "HP_MotorsBot";
    }

    @Override
    public String getBotToken() {
        return "6169935615:AAFx4WFNV__NKyLzQ-c57ZVCucNDUbOcous";
    }
/*
    public void setIcon(){
        //String logo = this.logoPath;
        SetChatPhoto setChatPhoto = new SetChatPhoto();
        setChatPhoto.setChatId("@HP_MotorsBot");
        setChatPhoto.setPhoto(new InputFile(new File("./Logo.jpeg")));
        try {
            execute(setChatPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
*/
    public void visualizzaMarchi(Connection conn, long chatId) throws SQLException {
        int i = 0;
        String sql = "SELECT DISTINCT costruttore FROM modello";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            SendMessage msg = new SendMessage();
            SendMessage msg1 = new SendMessage();
            msg.setChatId(chatId);
            //msg1.setChatId(chatId);
            //msg1.setText("Clicca sul marchio di cui desideri visualizzare i modelli.");
            try {
                msg.setText("Clicca sul marchio di cui desideri visualizzare i modelli.");
                //execute(msg);
                while (rs.next()) {
                    String costruttore = rs.getString("costruttore");
                    //msg.setText(costruttore);
                    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                    List<InlineKeyboardButton> rowInline = new ArrayList<>();
                    InlineKeyboardButton btn = new InlineKeyboardButton();
                    btn.setText(costruttore);
                    btn.setCallbackData(String.valueOf(i++));
                    rowInline.add(btn);
                    rowsInline.add(rowInline);
                    markupInline.setKeyboard(rowsInline);
                    //execute(msg);
                    msg.setReplyMarkup(markupInline);
                    execute(msg);
                }

                //execute(msg);
                //msg.setReplyMarkup(markupInline);
                /*try{
                    execute(msg);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                execute(msg);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }*/
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            long chatId = update.getMessage().getChatId();
            String welcomeMessage = "Ciao! Sono il bot Telegram della Concessionaria HP Motors. " + "\n" +
                    "Premi /opzioni per vedere la lista di opzioni che puoi effettuare!";
            String imagePath = "./Logo.jpeg";
            try {
                //SendMessage msg = new SendMessage();
                //msg.setChatId(String.valueOf(chatId));
                //msg.setText(welcomeMessage);
                SendPhoto photo = new SendPhoto();
                photo.setChatId(chatId);
                photo.setPhoto(new InputFile(new File(imagePath)));
                photo.setCaption(welcomeMessage);
                //execute(msg);
                execute(photo);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasMessage() && update.getMessage().getText().equals("/opzioni")) {
            long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage(); // Create a message object object
            message.setChatId(chatId);
            message.setText("Premi /nascondi per nascondere le opzioni." + "\n" +
                    "Ecco cosa puoi chiedere: ");
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboard = new ArrayList<>();
            KeyboardRow row = new KeyboardRow();
            row.add("Visualizza auto in pronta consegna");
            keyboard.add(row);
            row = new KeyboardRow();
            row.add("Visualizza i modelli");
            keyboard.add(row);
            row = new KeyboardRow();
            row.add("Visualizza i marchi");
            keyboard.add(row);
            keyboardMarkup.setKeyboard(keyboard);
            message.setReplyMarkup(keyboardMarkup);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasMessage() && update.getMessage().getText().equals("Visualizza i marchi")) {
            long chatId = update.getMessage().getChatId();
            try {
                visualizzaMarchi(this.conn, chatId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else if (update.hasMessage() && update.getMessage().getText().equals("/nascondi")) {
            long chatId = update.getMessage().getChatId();
            SendMessage msg = new SendMessage();
            msg.setChatId(chatId);
            msg.setText("Premi /opzioni per vedere le opzioni che puoi effettuare!");
            ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
            msg.setReplyMarkup(keyboardMarkup);
            try {
                execute(msg);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}

