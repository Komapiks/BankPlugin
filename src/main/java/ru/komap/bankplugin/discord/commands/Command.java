package ru.komap.bankplugin.discord.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;

public interface Command {
    void execute(MessageCreateEvent event, String[] args);
}