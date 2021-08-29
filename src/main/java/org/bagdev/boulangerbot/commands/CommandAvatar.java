package org.bagdev.boulangerbot.commands;

import org.bagdev.boulangerbot.Main;
import org.bagdev.boulangerbot.utils.commands.Command;
import org.bagdev.boulangerbot.utils.commands.CommandExecutor;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Optional;

public class CommandAvatar implements CommandExecutor {
    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) {

        if(event.getMessage().toString().contains("@")) {
            String avatarToGet = event.getMessageContent()
                    .substring(event.getMessageContent().indexOf("!") +1,
                            event.getMessageContent().indexOf(">"));

            Optional<User> userToAvatar = event.getApi().getCachedUserById(avatarToGet);

            MessageBuilder icon = new MessageBuilder().addAttachment(userToAvatar.get().getAvatar());
            icon.send(event.getChannel());

        } else {
            MessageBuilder icon = new MessageBuilder().addAttachment(event.getMessageAuthor().getAvatar());
            icon.send(event.getChannel());
        }
    }
}
