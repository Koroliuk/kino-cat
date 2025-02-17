package com.halushko.kinocat.bot.handlers.input;

import com.halushko.kinocat.middleware.cli.Constants;
import com.halushko.kinocat.middleware.handlers.input.InputMessageHandler;
import com.halushko.kinocat.middleware.rabbit.RabbitMessage;
import org.apache.log4j.Logger;

import static com.halushko.kinocat.bot.KoTorrentBot.sendText;
import static com.halushko.kinocat.middleware.rabbit.RabbitJson.unNormalizeText;

public class SendTextMessageToUser extends InputMessageHandler {
    @Override
    protected void getDeliverCallbackPrivate(RabbitMessage message) {
        long chatId = message.getUserId();
        String text = unNormalizeText(message.getText());
        Logger.getRootLogger().debug(String.format("[SendTextMessageToUser] Send text chatId:%s, text:%s", chatId, text));
        sendText(chatId, text);
    }
    @Override
    protected String getQueue() {
        return Constants.Queues.Telegram.TELEGRAM_OUTPUT_TEXT;
    }
}
