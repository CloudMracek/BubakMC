package me.cloud.bubakmc.listeners;

import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import me.cloud.bubakmc.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    String adminID;
    String avatarURL;
    String botName;

    public PlayerJoinListener() {
        adminID = Main.main.getConfig().getString("adminID");
        avatarURL = Main.main.getConfig().getString("avatarURL");
        botName = Main.main.getConfig().getString("botName");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setUsername(botName); // use this username
        builder.setAvatarUrl(avatarURL); // use this avatar
        builder.setContent("Hráč: **" + e.getPlayer().getName() + "** se připojil.");
        Main.client.send(builder.build());
    }

}
