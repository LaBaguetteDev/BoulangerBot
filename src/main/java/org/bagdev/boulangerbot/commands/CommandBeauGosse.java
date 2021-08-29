package org.bagdev.boulangerbot.commands;

import org.bagdev.boulangerbot.utils.commands.Command;
import org.bagdev.boulangerbot.utils.commands.CommandExecutor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class CommandBeauGosse implements CommandExecutor {

    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) {
        if(event.getMessageContent().substring(1).contains("&")) {
            event.getChannel().sendMessage("Je vais pas commencer à répéter des commandes batard");
        } else {
            if(event.getMessageContent().length() >= 4) {
                event.getChannel().sendMessage(event.getMessageContent().substring(4));
            } else {
                event.getChannel().sendMessage("Fils de pute faut rajouter un truc après la commande");
            }
        }
    }
}
