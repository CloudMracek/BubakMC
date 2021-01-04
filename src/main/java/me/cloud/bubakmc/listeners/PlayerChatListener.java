package me.cloud.bubakmc.listeners;

import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import me.cloud.bubakmc.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setUsername(e.getPlayer().getName()); // use this username
        builder.setAvatarUrl("https://www.mc-heads.net/avatar/" + e.getPlayer().getName()); // use this avatar
        builder.setContent(e.getMessage());
        Main.client.send(builder.build());
    }
}
