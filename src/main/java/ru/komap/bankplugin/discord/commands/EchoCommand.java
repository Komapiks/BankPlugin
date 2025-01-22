package ru.komap.bankplugin.discord.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;

public class EchoCommand implements Command {
    @Override
    public void execute(MessageCreateEvent event, String[] args) {
        if (args.length < 2) {
            event.getMessage().getChannel().block().createMessage("Usage: !echo <message>").block();
            return;
        }
        String message = String.join(" ", args).substring(args[0].length()).trim();
        MessageChannel channel = event.getMessage().getChannel().block();
        if (channel != null) {
            channel.createMessage(message).block();
        }
    }
}

