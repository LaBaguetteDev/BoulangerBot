package org.bagdev.boulangerbot.commands;

import org.bagdev.boulangerbot.utils.commands.Command;
import org.bagdev.boulangerbot.utils.commands.CommandExecutor;
import org.bagdev.boulangerbot.utils.commands.MessageManager;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CommandHelp implements CommandExecutor {
    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) {

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Besoin d'aide ?")
                .setDescription("Préfixe à utiliser : ``&``")
                .setColor(Color.ORANGE);

        ArrayList<Command> commands = MessageManager.registry.getCommandsList();
        for(Command cmd : commands) {
            builder.addField(Arrays.toString(cmd.getAliases()), cmd.getDescription(), true);
        }

        event.getMessageAuthor().asUser().get().sendMessage(builder);
        event.getChannel().sendMessage("Regarde tes messages privés bg");
    }
}
