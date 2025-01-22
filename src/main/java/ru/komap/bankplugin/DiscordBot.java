package ru.komap.bankplugin.discord;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import ru.komap.bankplugin.discord.commands.CommandHandler;

public class DiscordBot {
    private GatewayDiscordClient client;
    private final CommandHandler commandHandler = new CommandHandler();

    public void startBot(String token) {
        client = DiscordClientBuilder.create(token).build().login().block();

        // Обработка события получения сообщений
        client.getEventDispatcher().on(MessageCreateEvent.class).subscribe(event -> {
            String content = event.getMessage().getContent().trim();
            if (content.startsWith("!")) { // Проверка префикса команд
                commandHandler.handleCommand(event);
            }
        });
    }

    public void stopBot() {
        if (client != null) {
            client.logout().block();
        }
    }
}
