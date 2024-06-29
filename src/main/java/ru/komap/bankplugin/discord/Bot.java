package ru.komap.bankplugin.discord;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import org.bukkit.plugin.java.JavaPlugin;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.logging.Logger;

public class Bot extends JavaPlugin {
    Logger logger = this.getLogger();
    private final File Count = new File("Count.json");
    public void start() {
        DiscordClient client = DiscordClient.create("MTE0MzYxODczNDU5MDc5OTkwMg.GTJY1s.2l1GTJsAAvAN9rI9OdkScqU_Kl9Nq2FF1JvIRI");
        Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) -> {
            // ReadyEvent example
            Mono<Void> printOnLogin = gateway.on(ReadyEvent.class, event ->
                            Mono.fromRunnable(() -> {
                                final User self = event.getSelf();
                                String message = "Logged in as" + self.getUsername();
                                logger.warning(message);
                            }))
                    .then();

            // MessageCreateEvent example
            Mono<Void> handlePingCommand = gateway.on(MessageCreateEvent.class, event -> {
                Message message = event.getMessage();

                if (message.getContent().equalsIgnoreCase("!ping")) {
                    return message.getChannel()
                            .flatMap(channel -> channel.createMessage("pong!"));
                }

                return Mono.empty();
            }).then();

            Mono<Void> handleMessageCount = gateway.on(MessageCreateEvent.class, event -> {
                Message message = event.getMessage();

                if (message.getContent().equalsIgnoreCase("!ping")) {
                    return message.getChannel()
                            .flatMap(channel -> channel.createMessage("pong!"));
                }

                return Mono.empty();
            }).then();
            // combine them!
            return printOnLogin.and(handlePingCommand).and(handleMessageCount);
        });
        login.block();
    }
}
