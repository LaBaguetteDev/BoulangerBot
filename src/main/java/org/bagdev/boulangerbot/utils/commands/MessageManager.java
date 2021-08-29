package org.bagdev.boulangerbot.utils.commands;

import org.bagdev.boulangerbot.Main;
import org.bagdev.boulangerbot.commands.*;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class MessageManager {

    public static CommandRegistry registry = new CommandRegistry();

    static {
        registry.addCommand(new Command(
                "ping",
                "Ping le bot",
                new CommandPing(),
                "ping"
        ));

        registry.addCommand(new Command(
                "fdp",
                "Le Boulanger se défend",
                new CommandFdp(),
                "fdp", "tacos"
        ));

        registry.addCommand(new Command(
                "after command",
                "répète ce que tu dis après la commande",
                new CommandBeauGosse(),
                "bg"
        ));

        registry.addCommand(new Command(
                "pingpd",
                "dit que le gars ping est un pd",
                new CommandPD(),
                "pd"
        ));

        registry.addCommand(new Command(
                "love",
                "donne le pourcentage de chance que deux personnes s'aiment",
                new CommandLove(),
                "love"
        ));

        registry.addCommand(new Command(
                "help",
                "donne la liste des commandes et leurs discriptions",
                new CommandHelp(),
                "help"
        ));

        registry.addCommand(new Command(
                "ta gueule",
                "s'excuse d'avoir trop parlé",
                new CommandTG(),
                "tg"
        ));

        registry.addCommand(new Command(
                "avatar",
                "Retourne l'image de profil, soit de celui qui a lancé la commande, soit de l'utilisateur spécifié",
                new CommandAvatar(),
                "avatar"
        ));

        registry.addCommand(new Command(
                "suppression",
                "Supprime les messages précédents (non disponible)",
                new CommandDelete(),
                "clean"
        ));

        registry.addCommand(new Command(
                "timeup",
                "Donne la durée d'activité du bot",
                new CommandUptime(),
                "uptime"
        ));

        registry.addCommand(new Command(
                "trivial",
                "Donne une question triviale",
                new CommandTrivia(),
                "trivial"
        ));

        registry.addCommand(new Command(
                "gif",
                "Envoie un gif",
                new CommandGif(),
                "gif"
        ));

    }

    private static final String PREFIX = Main.getConfigManager().getToml().getString("bot.prefix");

    public static void create(MessageCreateEvent event) {

        if(event.getMessageContent().startsWith(PREFIX)) {
            String[] args = event.getMessageContent().split(" ");

            String commandName = args[0].substring(PREFIX.length());
            args = args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length - 1);

            String[] finalArgs = args;
            registry.getByAlias(commandName).ifPresent((cmd) -> {
                try {
                    cmd.getExecutor().run(event, cmd, finalArgs);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
