package ru.komap.bankplugin.discord.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandHandler() {
        // Регистрируем команды
        registerCommand("ping", new PingCommand());
        registerCommand("echo", new EchoCommand());
        registerCommand("hupa", new HupaCommand());
        registerCommand("online", new OnlineCommand());
    }

    private void registerCommand(String name, Command command) {
        commands.put(name, command);
    }

    public void handleCommand(MessageCreateEvent event) {
        String content = event.getMessage().getContent().trim();
        String[] parts = content.split("\\s+");
        String commandName = parts[0].substring(1); // удаляем префикс "!"

        Command command = commands.get(commandName);
        if (command != null) {
            command.execute(event, parts);
        } else {
            event.getMessage().getChannel().block()
                    .createMessage("Неизвестная команда: " + commandName).block();
        }
    }
}

