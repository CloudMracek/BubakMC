package me.cloud.bubakmc.listeners;

import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import me.cloud.bubakmc.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    String adminID;
    String avatarURL;

    public PlayerQuitListener() {
        adminID = Main.main.getConfig().getString("adminID");
        avatarURL = Main.main.getConfig().getString("avatarURL");
    }

    @EventHandler
    public void onJoin(PlayerQuitEvent e) {
        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setUsername("Bubák"); // use this username
        builder.setAvatarUrl(avatarURL); // use this avatar
        builder.setContent("Hráč: **" + e.getPlayer().getName() + "** se odpojil.");
        Main.client.send(builder.build());
    }
}
