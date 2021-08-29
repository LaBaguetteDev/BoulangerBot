package org.bagdev.boulangerbot.commands;

import org.bagdev.boulangerbot.utils.commands.Command;
import org.bagdev.boulangerbot.utils.commands.CommandExecutor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class CommandFdp implements CommandExecutor {
    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) {

        event.getChannel().sendMessage("TA GUEULE");

    }
}
