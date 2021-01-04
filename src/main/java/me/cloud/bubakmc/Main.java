package me.cloud.bubakmc;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import me.cloud.bubakmc.listeners.PlayerChatListener;
import me.cloud.bubakmc.listeners.PlayerQuitListener;
import me.cloud.bubakmc.listeners.PlayerJoinListener;
import me.cloud.bubakmc.utils.DiscordAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public class Main extends JavaPlugin {

    public static Main main;
    public static WebhookClient client;

    @Override
    public void onEnable() {

        // Creating a default config
        saveDefaultConfig();

        // This terrible thing
        main = this;

        WebhookClientBuilder builder = new WebhookClientBuilder(getConfig().getString("webhookURL")); // or id, token
        client = builder.build();

        getLogger().info("Starting Bubák");
        getLogger().info("Initializing Discord API");
        try {
            DiscordAPI dapi = new DiscordAPI(getConfig().getString("botToken"));
            getLogger().info("Successfully connected to Discord API.");
        } catch (LoginException e) {
            getLogger().warning("Failed to connect to Discord API. " + e.getMessage());
        }

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), this);

    }

    @Override
    public void onDisable() {
        getLogger().info("Stopping Bubák");
    }
}
