package org.bagdev.boulangerbot.commands;

import org.bagdev.boulangerbot.utils.commands.Command;
import org.bagdev.boulangerbot.utils.commands.CommandExecutor;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandDelete implements CommandExecutor {
    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) throws ExecutionException, InterruptedException {

        event.getChannel().sendMessage("Commande indisponible (trop teubé pour la coder correctement lol)");


        /*if(!event.getMessageAuthor().canDeleteMessage()) {
            event.getChannel().
                    sendMessage("T'as pas les droits pour supprimer un message salope");
        } else {
            String message = event.getMessageContent();
            if(message.matches(".*\\d+")) {
                int number = getNumber(message);

                AtomicInteger nbDeleted = new AtomicInteger();

                MessageSet messages = event.getChannel().getMessages(number).get();
                messages.forEach(msg -> {
                    event.getChannel().deleteMessages(msg);
                    nbDeleted.getAndIncrement();
                });

                event.getChannel().sendMessage("Nombre de messages supprimés : " + nbDeleted);

            } else {
                event.getChannel().
                        sendMessage("Connard faut spécifier le nombre de message que tu veux que je supprime");
            }
        }*/
    }

    /*private int getNumber(String txt) {
        int i = 0;
        while(i < txt.length() && !Character.isDigit(txt.charAt(i))) {
            i++;
        }
        int j = i;
        while(j < txt.length() && Character.isDigit(txt.charAt(j))) {
            j++;
        }

        return Integer.parseInt(txt.substring(i, j));
    }*/
}