package ru.komap.bankplugin.discord.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;

public class HupaCommand implements Command{
    @Override
    public void execute(MessageCreateEvent event, String[] args) {
        MessageChannel channel = event.getMessage().getChannel().block();
        if (channel != null) {
            channel.createMessage("loh!").block();
        }
    }
}
