package ru.komap.bankplugin.discord.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.legacy.LegacyMessageCreateSpec;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class OnlineCommand implements Command{
    @Override
    public void execute(MessageCreateEvent event, String[] args) {
        MessageChannel channel = event.getMessage().getChannel().block();
        for (Player player : Bukkit.getOnlinePlayers()) {
            channel.createMessage((Consumer<? super LegacyMessageCreateSpec>) Bukkit.getOnlinePlayers());
            if (Bukkit.getOnlinePlayers() != null) {
                channel.createMessage(player.getName());
            } else {
                channel.createMessage("Никого нет в онлайне");
            }
        }
    }
}
