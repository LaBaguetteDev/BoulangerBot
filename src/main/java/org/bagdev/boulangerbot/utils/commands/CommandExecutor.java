package org.bagdev.boulangerbot.utils.commands;

import org.javacord.api.event.message.MessageCreateEvent;

import java.util.concurrent.ExecutionException;

public interface CommandExecutor {
    void run(MessageCreateEvent event, Command command, String[] args) throws ExecutionException, InterruptedException;
}
