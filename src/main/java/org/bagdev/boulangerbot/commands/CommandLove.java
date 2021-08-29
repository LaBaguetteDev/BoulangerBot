package org.bagdev.boulangerbot.commands;

import org.bagdev.boulangerbot.utils.commands.Command;
import org.bagdev.boulangerbot.utils.commands.CommandExecutor;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Random;

public class CommandLove implements CommandExecutor {
    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) {
        if(!event.getMessageContent().matches(".*@.*@.*")) {
            event.getChannel().sendMessage("Connard faut ping quelqu'un pour dire que c'est un pd");
        } else {
            String firstName = event.getMessageContent().substring(event.getMessageContent().indexOf("<"), event.getMessageContent().indexOf(">") + 1);
            String secondName = event.getMessageContent().substring(event.getMessageContent().lastIndexOf("<"));

            Random random = new Random();
            int nextInt = random.nextInt(101);
            event.getChannel().sendMessage(firstName + " et " + secondName
                    + " ont " + nextInt + "% de chance de vouloir baiser");

            if(nextInt < 10) {
                event.getChannel().sendMessage("Oula ils ont pas l'air de s'aimer ces deux là :cold-face:");
            } else if (nextInt < 50) {
                event.getChannel().sendMessage("C'est des potes quoi :sweat_smile:");
            } else if (nextInt < 70) {
                event.getChannel().sendMessage("Ca devient chaud là :smirk:");
            } else {
                event.getChannel().sendMessage("ENORME TENSION SEXUELLE :hot_face:");
            }
        }
    }
}
