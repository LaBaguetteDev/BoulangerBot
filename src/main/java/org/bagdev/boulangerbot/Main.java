package org.bagdev.boulangerbot;

import org.bagdev.boulangerbot.utils.ConfigManager;
import org.bagdev.boulangerbot.utils.commands.MessageManager;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.io.File;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Main {

    private static DiscordApi api;
    private static ConfigManager configManager;

    public static long LaunchTime = System.currentTimeMillis();;

    public static void main(String[] args) {

        configManager = new ConfigManager(new File(System.getProperty("user.dir"), "config.toml"));

        api = new DiscordApiBuilder()
                .setToken(configManager.getToml().getString("bot.token"))
                .login().join();

        api.addMessageCreateListener(MessageManager::create);
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

}
