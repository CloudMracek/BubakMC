package me.cloud.bubakmc.utils;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import me.cloud.bubakmc.Main;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Objects;
import java.util.Scanner;

public class DiscordEventHandler implements EventListener  {

    String adminID;
    String avatarURL;
    String botName;

    public DiscordEventHandler() {
        adminID = Main.main.getConfig().getString("adminID");
        avatarURL = Main.main.getConfig().getString("avatarURL");
        botName = Main.main.getConfig().getString("botName");
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if(event instanceof ReadyEvent) {
            Bukkit.getLogger().info("Successfully logged in as: " + event.getJDA().getSelfUser().getName());
        }

        else if(event instanceof MessageReceivedEvent) {
            MessageReceivedEvent e = (MessageReceivedEvent) event;
            
            if(!e.getChannel().getId().equals(Main.main.getConfig().getString("relayChannelID"))) return;
            if(e.getAuthor().isBot()) return;

            String[] args = e.getMessage().getContentRaw().split(" ");

            if(e.getMessage().getContentRaw().startsWith("!whitelist")) {
                try {

                    //Sacrificing readability for compactness. Basically checks is the json file exists.
                    JSONObject accounts;
                    try {
                        String myJson = new Scanner(new File("accounts.json")).useDelimiter("\\Z").next();
                        accounts = new JSONObject(myJson);
                    }
                    catch (FileNotFoundException fileNotFoundException) {
                        accounts = new JSONObject();
                    }

                    if(accounts.isNull(e.getAuthor().getId())) {
                        accounts.put(e.getAuthor().getId(), args[1]);

                        FileWriter file = new FileWriter("accounts.json");
                        file.write(accounts.toString());
                        file.close();


                        //noinspection deprecation
                        Bukkit.getOfflinePlayer(args[1]).setWhitelisted(true);
                        e.getChannel().sendMessage(e.getAuthor().getAsMention() + " Jméno \"" + args[1] + "\" bylo přídáno do whitelistu. Nyní se můžeš připojit. ```IP: honzuvkod.dev```").queue();

                    }
                    else {
                        String mcName = accounts.getString(e.getAuthor().getId());
                        e.getChannel().sendMessage(e.getAuthor().getAsMention() + " Pod tvým Discord účtem už je ve whitelistu zapsán Minecraft účet: " + mcName +
                                ". Pokud jsi si změnil jméno, nebo chceš přidat kamaráda, kontaktuj administrátory.").queue();
                    }
                }
                catch (Exception ex)  {
                    e.getChannel().sendMessage("Nastal problém při zpracovávání tvého příkazu. Kontaktuj prosím: " + Objects.requireNonNull(e.getJDA().getUserById(adminID)).getAsMention()).queue();
                    ex.printStackTrace();
                }
            }

            else if(e.getMessage().getContentRaw().startsWith("!status")) {
                WebhookMessageBuilder builder = new WebhookMessageBuilder();
                builder.setUsername(botName); // use this username
                builder.setAvatarUrl(avatarURL); // use this avatar

                StringBuilder onlinePlayers = new StringBuilder();
                for(Player p : Bukkit.getOnlinePlayers()) {
                    onlinePlayers.append(p.getName()).append("\n");
                }
                WebhookEmbed embed = new WebhookEmbedBuilder()
                        .setColor(0x2FFF30)
                        .setTitle(new WebhookEmbed.EmbedTitle("Na serveru je: " + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getServer().getMaxPlayers() + " hráčů.", null))
                        .setDescription(onlinePlayers.toString())
                        .build();

                builder.addEmbeds(embed);

                Main.client.send(builder.build());
            }

            else {
                Bukkit.broadcastMessage(ChatColor.BLUE + "[Discord] " + ChatColor.RESET + e.getAuthor().getName() + ": " + e.getMessage().getContentDisplay());

                if(e.getMessage().getContentRaw().toLowerCase().contains("jak dela bubak") || e.getMessage().getContentRaw().toLowerCase().contains("jak dělá bubák")) {
                    e.getChannel().sendMessage("Bububu! <:bubkemote:747142357140635699>").queue();
                }

            }
        }
    }
}
