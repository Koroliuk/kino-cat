package com.halushko;

import com.halushko.rabKot.handlers.input.InputMessageHandler;
import com.halushko.rabKot.rabbit.RabbitMessage;
import com.halushko.rabKot.cli.ExecuteBash;

import java.util.List;

public class TorrentOperator extends InputMessageHandler {
    public static final String EXECUTE_TORRENT_COMMAND;

    static {
        String str = System.getenv("EXECUTE_TORRENT_COMMAND");
        EXECUTE_TORRENT_COMMAND = str != null ? str : "EXECUTE_TORRENT_COMMAND";
    }

    @Override
    protected void getDeliverCallbackPrivate(RabbitMessage rabbitMessage) {
        try {
            String text = rabbitMessage.getText();
            long userId = rabbitMessage.getUserId();

            List<String> result = ExecuteBash.executeViaCLI(text);

        } catch (Exception e) {
            System.out.println("During message handle got an error: " + e.getMessage());
        }
    }

    @Override
    protected String getQueue() {
        return EXECUTE_TORRENT_COMMAND;
    }
}
