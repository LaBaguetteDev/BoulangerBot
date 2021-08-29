package org.bagdev.boulangerbot.commands;

import org.bagdev.boulangerbot.Main;
import org.bagdev.boulangerbot.utils.commands.Command;
import org.bagdev.boulangerbot.utils.commands.CommandExecutor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.sql.Time;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CommandUptime implements CommandExecutor {
    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) throws ExecutionException, InterruptedException {
        long unixMsg = event.getMessage().getCreationTimestamp().toEpochMilli();

        long seconds = (unixMsg - Main.LaunchTime) / 1000;

        String result = convertSecondsToHhMmSs(seconds);

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Le boulanger est actif depuis")
                .setColor(Color.GREEN)
                .setDescription(result);

        event.getChannel().sendMessage(builder);
    }

    private static String convertSecondsToHhMmSs(long seconds) {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;

        return String.format("%02dh%02dmin%02dsec", h,m,s);
    }
}
