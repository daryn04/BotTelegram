import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.description.SetMyDescription;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
//import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.ResultSet;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.sql.SQLException;

public class BotTelegram extends TelegramLongPollingBot {

    private String BotUsername = "HP_MotorsBot";
    private String BotToken = "6169935615:AAFx4WFNV__NKyLzQ-c57ZVCucNDUbOcous";
    private DBManager dbManager;
    private String stato;

    @Override
    public String getBotUsername() {
        return this.BotUsername;
    }

    @Override
    public String getBotToken() {
        return this.BotToken;
    }

    public void startConnection() {
        DBManager dbManager = null;
        try {
            dbManager = new DBManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.dbManager = dbManager;
        try {
            dbManager.visualizzaModelli("Fiat");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCommands() {
        List<BotCommand> commandsList = new ArrayList();
        commandsList.add(new BotCommand("/opzioni", "Per visualizzare la lista di opzioni che puoi effettuare"));
        commandsList.add(new BotCommand("/marchi", "Per visualizzare la lista dei marchi disponibili"));
        commandsList.add(new BotCommand("/modelli", "Per visualizzare la lista dei modelli disponibili"));
        try {
            SetMyCommands setMyCommands = new SetMyCommands();
            setMyCommands.setCommands(commandsList);
            this.execute(setMyCommands);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDescription() throws TelegramApiException {
        SetMyDescription setMyDescription = new SetMyDescription();
        setMyDescription.setDescription("ciao");
        execute(setMyDescription);
    }

    public void start(String chatId) {
        String welcomeMessage = "Ciao! Sono il bot Telegram della Concessionaria HP Motors. " + "\n" +
                "Premi /opzioni per vedere la lista di opzioni che puoi effettuare!";
        String imagePath = "./Logo.jpeg";
        try {
            SendPhoto photo = new SendPhoto();
            photo.setChatId(chatId);
            photo.setPhoto(new InputFile(new File(imagePath)));
            photo.setCaption(welcomeMessage);
            execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void opzioni(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Premi /nascondi per nascondere le opzioni." + "\n" +
                "Ecco cosa puoi chiedere: ");
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Visualizza");
        keyboard.add(row);
        row = new KeyboardRow();
        row.add("Richiedi un preventivo");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void visualizzaMarchi(String chatId) {
        try {
            SendMessage msg = new SendMessage();
            msg.setChatId(String.valueOf(chatId));
            msg.setText("Clicca sul marchio per visualizzare i modelli di questo.");
            execute(msg);
            replyCostruttori(String.valueOf(chatId));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void nascondi(String chatId) {
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

    public InlineKeyboardMarkup setbtn(String nome) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton btn = new InlineKeyboardButton();
        btn.setText(nome);
        btn.setCallbackData(nome);
        rowInline.add(btn);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String chatId = update.getMessage().getChatId().toString();
            if (update.getMessage().getText().equals("/start")) {
                start(chatId);
            } else if (update.getMessage().getText().equals("/opzioni")) {
                opzioni(chatId);
            } else if ((update.getMessage().getText().equals("Visualizza") || update.getMessage().getText().equals("/marchi"))) {
                visualizzaMarchi(chatId);
                this.stato = "visualizza";
            } else if (update.getMessage().getText().equals("Richiedi un preventivo")) {
                visualizzaMarchi(chatId);
                this.stato = "preventivo";
            } else if (update.getMessage().getText().equals("/nascondi")) {
                nascondi(chatId); //non funge
            }
        }
        else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            String dataChatId = callbackQuery.getMessage().getChat().getId().toString();
            SendChatAction sendChatAction = new SendChatAction();
            List<String> costruttori = new ArrayList();
            Collections.addAll(costruttori, "Fiat", "Seat", "Jeep", "Ford", "Alfa Romeo", "Volkswagen");
            ArrayList<String> colori = new ArrayList<>();
            Collections.addAll(colori, "Nero", "Rosso", "Blu", "Verde", "Giallo", "Azzurro", "Bianco", "Grigio");

            if (this.stato == "visualizza") {
                if (costruttori.contains(data)) {
                    replyModelli(data, dataChatId, sendChatAction);
                }
            }
            else if (this.stato == "preventivo") {
                Automobile automobile = new Automobile();
                ArrayList<String> modelli = null;
                try {
                    modelli = this.dbManager.visualizzaTuttiModelli();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (costruttori.contains(data)) {
                    automobile.setCostruttore(data);
                    replyModelli(data, dataChatId, sendChatAction);
                }
                if (modelli.contains(data)) {
                    automobile.setNomeModello(data);
                    try {
                        automobile.setPrezzo(dbManager.prezzoModello(data));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (colori.contains(data)) {
                    automobile.setColore(data);
                }
                if (data == "Automatico" || data == "Manuale") {
                    automobile.setTipoDiCambio(data);
                }
                if (data == "Benzina" || data == "Diesel") {
                    automobile.setAlimentazione(data);
                }
                if (data == "Acciaio" || data == "Lega") {
                    automobile.setMaterialeCerchione(data);
                }
                calcoloPrezzo(automobile);
                try {
                    dbManager.inserisciPreventivo(dataChatId, automobile);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void calcoloPrezzo(Automobile automobile){
        int prezzo;
        if (automobile.getTipoDiCambio() == "Automatico") {
            prezzo = automobile.getPrezzo();
            automobile.setPrezzo(prezzo + 1500);
        }
        if (automobile.getMaterialeCerchione() == "Lega") {
            prezzo = automobile.getPrezzo();
            automobile.setPrezzo(prezzo + 400);
        }
        if (automobile.getAlimentazione() == "Diesel") {
            prezzo = automobile.getPrezzo();
            automobile.setPrezzo(prezzo + 1500);
        }
        if (automobile.getColore() == "Rosso" || automobile.getColore() == "Blu" || automobile.getColore() == "Azzurro" || automobile.getColore() == "Verde" || automobile.getColore() == "Nero" || automobile.getColore() == "Giallo") {
            prezzo = automobile.getPrezzo();
            automobile.setPrezzo(prezzo + 800);
        }
    }

    public void replyCostruttori(String chatId){
        ResultSet rs;
        try {
            rs = this.dbManager.visualizzaMarchi();
            SendSticker sticker = new SendSticker();
            sticker.setChatId(chatId);
            while (rs.next()) {
                String costruttore = rs.getString("costruttore");
                sticker.setSticker(new InputFile(new File("img-resized/" + costruttore + ".png")));
                InlineKeyboardMarkup btn = setbtn(costruttore);
                sticker.setReplyMarkup(btn);
                execute(sticker);
            }
        } catch (SQLException | TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void replyModelli(String costruttore, String chatId, SendChatAction action){
        try {
            ResultSet rs = this.dbManager.visualizzaModelli(costruttore);
            action.setChatId(chatId);
            SendSticker modelloSticker = new SendSticker();
            modelloSticker.setChatId(chatId);
            SendMessage msg = new SendMessage();
            msg.setChatId(chatId);
            msg.setText("Clicca sul modello per visualizzare i colori disponibili di questo.");
            execute(msg);
            while(rs.next()){
            String modello = rs.getString("nome");
            modelloSticker.setSticker(new InputFile(new File("modelli/" + modello +".png")));
            action.setAction(ActionType.UPLOADPHOTO);
            InlineKeyboardMarkup btn = setbtn(modello);
            modelloSticker.setReplyMarkup(btn);
            execute(action);
            execute(modelloSticker);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

