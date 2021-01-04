package me.cloud.bubakmc.utils;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class DiscordAPI {
    public DiscordAPI(String botToken) throws LoginException {
        JDABuilder builder = JDABuilder.createDefault(botToken);
        builder.setActivity(Activity.watching("P1"));
        builder.addEventListeners(new DiscordEventHandler());
        builder.build();
    }
}
