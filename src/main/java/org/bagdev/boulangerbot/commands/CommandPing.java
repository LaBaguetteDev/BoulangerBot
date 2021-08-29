package org.bagdev.boulangerbot.commands;

import org.bagdev.boulangerbot.utils.commands.Command;
import org.bagdev.boulangerbot.utils.commands.CommandExecutor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class CommandPing implements CommandExecutor {
    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) {

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Pinging...")
                .setColor(Color.ORANGE);

        event.getChannel().sendMessage(builder).thenAccept(msg -> {
            long unixBot = msg.getCreationTimestamp().toEpochMilli();
            long unixUser = event.getMessage().getCreationTimestamp().toEpochMilli();
            long ping = unixBot - unixUser;

            builder.setColor(Color.RED)
                    .setDescription(ping + "ms")
                    .setTitle("Pong fils de pute");

            msg.edit(builder);
        });
    }
}
