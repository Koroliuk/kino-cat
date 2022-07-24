package com.halushko;

import com.halushko.handlers.input.SendTextMessageToUser;
import com.halushko.handlers.telegram.KoTorrentUserMessageHandler;
import com.halushko.rabKot.handlers.telegram.UserMessageHandler;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collection;

public class KoTorrentBot extends TelegramLongPollingBot {
    public static KoTorrentBot BOT;

    public static final String BOT_NAME;
    static {
        String str = System.getenv("BOT_NAME");
        BOT_NAME = str != null ? str : "koTorrentBot";
    }

    public static final String BOT_TOKEN;
    static {
        String str = System.getenv("BOT_TOKEN");
        BOT_TOKEN = str != null ? str : "1859184957:AAG24FRjQND5gSaiblsaQgZe_nLNId8sOx8";
    }

    public static void main(String[] args) {
        try {
            ApiContextInitializer.init();
            TelegramBotsApi botapi = new TelegramBotsApi();
            BOT = new KoTorrentBot();
            new Thread(new SendTextMessageToUser()).start();
            botapi.registerBot(BOT);
        } catch (TelegramApiException e) {
            System.out.println("Bot start has been fail: " + e.getMessage());
        }
    }


    @Override
    public void onUpdateReceived(Update update) {
        for (UserMessageHandler handler : KoTorrentUserMessageHandler.getHandlers()) {
            handler.readMessage(update);
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    public static void sendText(long chatId, String str) {
        if (str == null || str.isEmpty()) {
            return;
        }
        try {
            BOT.execute(new SendMessage() {{
                            setChatId(chatId);
                            setText(str);
                        }}
            );
        } catch (TelegramApiException ex) {
            System.out.println("Unknown koTorrent error: " + ex.getMessage());
        }
    }

    public static void sendText(long chatId, Collection<String> str) {
        for (String line : str) sendText(chatId, line);
    }
}
