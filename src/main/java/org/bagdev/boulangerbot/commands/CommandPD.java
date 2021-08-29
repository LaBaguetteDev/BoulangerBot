package org.bagdev.boulangerbot.commands;

import org.bagdev.boulangerbot.utils.commands.Command;
import org.bagdev.boulangerbot.utils.commands.CommandExecutor;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Optional;

public class CommandPD implements CommandExecutor {
    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) {
        if(!event.getMessageContent().contains("@")) {
            event.getChannel().sendMessage("Connard faut ping quelqu'un pour dire que c'est un pd");
        }
        else if(event.getMessageContent().length() >= 4) {
            event.getChannel().sendMessage(event.getMessageContent().substring(4) + " c'est un pd :poop:");
        } else {
            event.getChannel().sendMessage("Fils de pute faut rajouter un truc après la commande");
        }
    }
}
