import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.description.SetMyDescription;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.SQLException;

/**
 Questa classe consente la gestione generale del Bot. Gestisce i comandi mandati dall'utente e le risposte
 ai rispettivi bottoni cliccati.
 */
public class BotTelegram extends TelegramLongPollingBot {
    private String BotUsername;
    private String BotToken;
    private DBManager dbManager;
    private String stato;
    private Automobile automobile;
    private boolean isElectric = false;
    private HashMap<Integer, Automobile> automobileUtente = new HashMap<Integer, Automobile>();
    private int numeroPreventivi;

    @Override
    public String getBotUsername() {
        return "HP_MotorsBot";
    }

    @Override
    public String getBotToken() {
        return "6169935615:AAFx4WFNV__NKyLzQ-c57ZVCucNDUbOcous";
    }

    /**
    Questo metodo consente di avviare la connessione con il database creando un'istanza della classe DBManager
     */
    public void startConnection() throws SQLException {
        DBManager dbManager = new DBManager();
        this.dbManager = dbManager;
        this.dbManager.getConnection();
    }

    public void inizializzaBot() throws TelegramApiException {
        List<BotCommand> commandsList = new ArrayList();
        commandsList.add(new BotCommand("/start", "Ciao!"));
        commandsList.add(new BotCommand("/opzioni", "Per visualizzare la lista di opzioni che puoi effettuare"));
        //commandsList.add(new BotCommand("/marchi", "Per visualizzare la lista dei marchi disponibili"));
        //commandsList.add(new BotCommand("/modelli", "Per visualizzare la lista dei modelli disponibili"));
        SetMyCommands setMyCommands = new SetMyCommands();
        setMyCommands.setCommands(commandsList);
        this.execute(setMyCommands);
        SetMyDescription setMyDescription = new SetMyDescription();
        setMyDescription.setDescription("Ciao! Questo è il Bot della Concessionaria HP Motors!" + "\n" +
                "Cosa può fare questo bot?" + "\n" +
                "Questo Bot è in grado di far visualizzare le automobili in pronta consegna, le caratteristiche di un modello specifico e tanto altro ancora.." + "\n" +
                "Avvialo per visualizzare più in dettaglio le sue caratteristiche!");
        execute(setMyDescription);
    }

    private void start(String chatId) {
        String welcomeMessage = "Ciao! Sono il bot Telegram della Concessionaria HP Motors. " + "\n" +
                "Premi /opzioni per vedere la lista di opzioni che puoi effettuare!";
        String imagePath = "img/Logo.jpeg";
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

    private void opzioni(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Premi /nascondi per nascondere le opzioni." + "\n" +
                "Ecco cosa puoi chiedere: ");
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Visualizza le caratteristiche del modello desiderato");
        keyboard.add(row);
        row = new KeyboardRow();
        row.add("Richiedi un preventivo");
        keyboard.add(row);
        row = new KeyboardRow();
        row.add("Auto in pronta consegna");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void visualizzaMarchi(String chatId) {
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
/*
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
*/
    private InlineKeyboardMarkup setbtn(String nome) {
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

    private void setcolor(String chatId) throws TelegramApiException {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        SendMessage msg = new SendMessage();
        msg.setText("Seleziona il colore che desideri:");
        msg.setChatId(chatId);
        InlineKeyboardButton btn = new InlineKeyboardButton();
        btn.setText("⚪️");
        btn.setCallbackData("Bianco");
        InlineKeyboardButton btn1 = new InlineKeyboardButton();
        btn1.setText("⚫");
        btn1.setCallbackData("Nero");
        InlineKeyboardButton btn2 = new InlineKeyboardButton();
        btn2.setText("🔴");
        btn2.setCallbackData("Rosso");
        InlineKeyboardButton btn3 = new InlineKeyboardButton();
        btn3.setText("🟡");
        btn3.setCallbackData("Giallo");
        InlineKeyboardButton btn4 = new InlineKeyboardButton();
        btn4.setText("🔵");
        btn4.setCallbackData("Blu");
        InlineKeyboardButton btn5 = new InlineKeyboardButton();
        btn5.setText("🟢");
        btn5.setCallbackData("Verde");
        rowInline.add(btn);
        rowInline.add(btn1);
        rowInline.add(btn2);
        rowInline.add(btn3);
        rowInline.add(btn4);
        rowInline.add(btn5);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        msg.setReplyMarkup(markupInline);
        execute(msg);
    }

    private void setCambio(String chatId) throws TelegramApiException {
        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText("Seleziona il tipo di cambio:");
        SendSticker sticker = new SendSticker();
        sticker.setChatId(chatId);
        sticker.setSticker(new InputFile(new File("img/Automatico.jpg")));
        SendSticker sticker1 = new SendSticker();
        sticker1.setChatId(chatId);
        sticker1.setSticker(new InputFile(new File("img/Manuale.png")));
        InlineKeyboardMarkup btn = setbtn("Automatico");
        InlineKeyboardMarkup btn1 = setbtn("Manuale");
        sticker.setReplyMarkup(btn);
        sticker1.setReplyMarkup(btn1);
        execute(msg);
        execute(sticker);
        execute(sticker1);
    }

    private void setCerchioni(String chatId) throws TelegramApiException {
        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText("Seleziona il materiale dei cerchioni:");
        SendSticker sticker = new SendSticker();
        sticker.setChatId(chatId);
        sticker.setSticker(new InputFile(new File("img/Acciaio.png")));
        SendSticker sticker1 = new SendSticker();
        sticker1.setChatId(chatId);
        sticker1.setSticker(new InputFile(new File("img/Lega.png")));
        InlineKeyboardMarkup btn = setbtn("Acciaio");
        InlineKeyboardMarkup btn1 = setbtn("Lega");
        sticker.setReplyMarkup(btn);
        sticker1.setReplyMarkup(btn1);
        execute(msg);
        execute(sticker);
        execute(sticker1);
    }

    private void setAlimentazione(String chatId) throws TelegramApiException {
        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText("Seleziona il tipo di alimentazione:");
        SendSticker sticker = new SendSticker();
        sticker.setChatId(chatId);
        sticker.setSticker(new InputFile(new File("img/Benzina.jpg")));
        SendSticker sticker1 = new SendSticker();
        sticker1.setChatId(chatId);
        sticker1.setSticker(new InputFile(new File("img/Diesel.jpg")));
        InlineKeyboardMarkup btn = setbtn("Benzina");
        InlineKeyboardMarkup btn1 = setbtn("Diesel");
        sticker.setReplyMarkup(btn);
        sticker1.setReplyMarkup(btn1);
        execute(msg);
        execute(sticker);
        execute(sticker1);
    }
    /**
     Questo metodo si occupa della gestione dei messaggi ricevuti dal Bot o dei bottoni cliccati dall'utente
     */

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String chatId = update.getMessage().getChatId().toString();
            int userId = update.getMessage().getFrom().getId().intValue();
            if (update.getMessage().getText().equals("/start")) {
                start(chatId);
            }
            else if (update.getMessage().getText().equals("/opzioni")) {
                opzioni(chatId);
            }
            else if ((update.getMessage().getText().equals("Visualizza le caratteristiche del modello desiderato") || update.getMessage().getText().equals("/marchi"))) {
                visualizzaMarchi(chatId);
                this.stato = "visualizza";
            }
            else if (update.getMessage().getText().equals("Richiedi un preventivo")) {
                visualizzaMarchi(chatId);
                this.stato = "preventivo";
                Automobile automobile = new Automobile();
                this.automobileUtente.put(userId, automobile);
            }
            else if (update.getMessage().getText().equals("Auto in pronta consegna")){
                visualizzaMarchi(chatId);
                this.stato = "prontaConsegna";
                this.automobile = new Automobile();
            }
            //else if (update.getMessage().getText().equals("/nascondi")) {
            //    nascondi(chatId); //non funge
            //}
        }
        else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            String dataChatId = callbackQuery.getMessage().getChat().getId().toString();
            int userId = update.getCallbackQuery().getFrom().getId().intValue();
            SendChatAction sendChatAction = new SendChatAction();
            List<String> costruttori = new ArrayList();
            Collections.addAll(costruttori, "Fiat", "Seat", "Jeep", "Ford", "Alfa Romeo", "Volkswagen");
            ArrayList<String> colori = new ArrayList<>();
            Collections.addAll(colori, "Nero", "Rosso", "Blu", "Verde", "Giallo", "Bianco");
            if (this.stato == "visualizza") {
                ArrayList<String> tuttiModelli = null;
                try {
                    tuttiModelli = this.dbManager.visualizzaTuttiModelli();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (costruttori.contains(data)) {
                    replyModelli(data, dataChatId, sendChatAction);
                }
                if (tuttiModelli.contains(data)) {
                    ArrayList<Modello> modelli = new ArrayList<>();
                    try {
                        this.isElectric = this.dbManager.isElectric(data);
                        modelli = this.dbManager.visualizzaCaratteristicheModello(data);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (!this.isElectric) {
                        for (int i = 0; i < modelli.size(); i++) {
                            SendMessage msg = new SendMessage();
                            SendSticker sticker = new SendSticker();
                            msg.setText(modelli.get(i).toString());
                            msg.setChatId(dataChatId);
                            sticker.setChatId(dataChatId);
                            sticker.setSticker(new InputFile(new File("modelli/" + modelli.get(i).getNome() + ".png")));
                            try {
                                execute(sticker);
                                execute(msg);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    else {
                        try {
                            ModelloElettrico modelloElettrico = this.dbManager.visualizzaModelloElettrico(data);
                            SendMessage msg = new SendMessage();
                            SendSticker sticker = new SendSticker();
                            msg.setText(modelloElettrico.toString());
                            msg.setChatId(dataChatId);
                            sticker.setChatId(dataChatId);
                            sticker.setSticker(new InputFile(new File("modelli/" + modelloElettrico.getNome() + ".png")));
                            try {
                                execute(sticker);
                                execute(msg);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    }
            }
            else if (this.stato == "preventivo") {
                ArrayList<String> modelli = null;
                try {
                    modelli = this.dbManager.visualizzaTuttiModelli();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Set<Integer> userIdKeys = this.automobileUtente.keySet();
                for (Integer userIdKey : userIdKeys) {
                    if (userIdKey == userId) {
                        if (costruttori.contains(data)) {
                            replyModelli(data, dataChatId, sendChatAction);
                        }
                        if (modelli.contains(data)) {
                            this.automobileUtente.get(userIdKey).setNomeModello(data);
                            try {
                                this.isElectric = this.dbManager.isElectric(data);
                                ResultSet rs;
                                rs = this.dbManager.prezzoModello(data);
                                int prezzo = 0;
                                while (rs.next()) {
                                    prezzo = rs.getInt("prezzo_listino");
                                }
                                this.automobileUtente.get(userIdKey).setPrezzo(prezzo);
                                setcolor(dataChatId);
                            } catch (SQLException | TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if (colori.contains(data)) {
                            this.automobileUtente.get(userIdKey).setColore(data);
                            try {
                                setCerchioni(dataChatId);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if ((data.equals("Automatico") || data.equals("Manuale") && this.isElectric == false)) {
                            this.automobileUtente.get(userIdKey).setTipoDiCambio(data);
                            try {
                                setAlimentazione(dataChatId);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if ((data.equals("Benzina") || data.equals("Diesel") && this.isElectric == false)) {
                            this.automobileUtente.get(userIdKey).setAlimentazione(data);
                        }
                        if (data.equals("Acciaio") || data.equals("Lega") ) {
                            this.automobileUtente.get(userIdKey).setMaterialeCerchione(data);
                            //automobile.setMaterialeCerchione(data);
                            if (this.isElectric == false) {
                                try {
                                    setCambio(dataChatId);
                                } catch (TelegramApiException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        calcoloPrezzo(this.automobileUtente.get(userIdKey));
                        String date = getDate();
                        SendMessage msg = new SendMessage();
                        SendMessage msgPreventivi = new SendMessage();
                        msgPreventivi.setChatId(dataChatId);
                        msg.setChatId(dataChatId);
                        msg.setText("Abbiamo elaborato il tuo preventivo che ammonta a " + this.automobileUtente.get(userIdKey).getPrezzo() + " €\n"+ "Ti aspettiamo in concessionaria per concludere la pratica!" );
                        msgPreventivi.setText("Hai effettuato il preventivo numero " + this.numeroPreventivi + "|");
                        if (this.automobileUtente.get(userIdKey).getAlimentazione() != null && this.isElectric == false) {
                            try {
                                this.dbManager.inserisciPreventivo(userIdKey, this.automobileUtente.get(userIdKey), date);
                                execute(msg);
                                execute(msgPreventivi);
                                this.automobileUtente.remove(userIdKey);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else if (this.automobileUtente.get(userIdKey).getMaterialeCerchione() != null && this.isElectric == true) {
                            try {
                                this.dbManager.inserisciPreventivo(userIdKey, this.automobileUtente.get(userIdKey), date);
                                execute(msg);
                                execute(msgPreventivi);
                                this.automobileUtente.remove(userIdKey);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }

            }
            else if (this.stato == "prontaConsegna"){
                ArrayList<String> modelli = null;
                try {
                    modelli = this.dbManager.visualizzaTuttiModelli();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (costruttori.contains(data)) {
                    replyModelli(data, dataChatId, sendChatAction);
                }
                if (modelli.contains(data)){
                    ArrayList<Automobile> automobili = new ArrayList<>();
                    try {
                        automobili = this.dbManager.visualizzaAuto(data);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    for (int i=0; i<automobili.size(); i++){
                        SendMessage msg = new SendMessage();
                        SendSticker sticker = new SendSticker();
                        msg.setText(automobili.get(i).toString());
                        msg.setChatId(dataChatId);
                        sticker.setChatId(dataChatId);
                        sticker.setSticker(new InputFile(new File("img/auto/" + automobili.get(i).getNomeModello() + "_" + automobili.get(i).getColore() + ".png")));
                        try {
                            execute(sticker);
                            execute(msg);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }


    private String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return formatter.format(date);
    }
    private void calcoloPrezzo(Automobile automobile){
        int prezzo;
        if(!this.isElectric) {
            if (automobile.getTipoDiCambio() == "Automatico") {
                prezzo = automobile.getPrezzo();
                automobile.setPrezzo(prezzo + 1500);
            }
            if (automobile.getAlimentazione() == "Diesel") {
                prezzo = automobile.getPrezzo();
                automobile.setPrezzo(prezzo + 1500);
            }
        }
        if (automobile.getColore() == "Rosso" || automobile.getColore() == "Blu" || automobile.getColore() == "Azzurro" || automobile.getColore() == "Verde" || automobile.getColore() == "Nero" || automobile.getColore() == "Giallo") {
            prezzo = automobile.getPrezzo();
            automobile.setPrezzo(prezzo + 800);
        }
        if (automobile.getMaterialeCerchione() == "Lega") {
            prezzo = automobile.getPrezzo();
            automobile.setPrezzo(prezzo + 400);
        }
    }

    private void replyCostruttori(String chatId){
        ResultSet rs;
        try {
            rs = this.dbManager.visualizzaMarchi();
            SendSticker sticker = new SendSticker();
            sticker.setChatId(chatId);
            while (rs.next()) {
                String costruttore = rs.getString("costruttore");
                sticker.setSticker(new InputFile(new File("img/" + costruttore + ".png")));
                InlineKeyboardMarkup btn = setbtn(costruttore);
                sticker.setReplyMarkup(btn);
                execute(sticker);
            }
        } catch (SQLException | TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private void replyModelli(String costruttore, String chatId, SendChatAction action){
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

