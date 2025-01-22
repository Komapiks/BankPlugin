package ru.komap.bankplugin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import ru.komap.bankplugin.discord.DiscordBot;
import ru.komap.bankplugin.utils.*;

import java.util.*;
import java.util.logging.Logger;

public final class BankPlugin extends JavaPlugin implements CommandExecutor {
    public Logger logger = this.getLogger();
    private MotdManager motdManager;
    private DiscordBot discordBot;

    @Override
    public void onEnable() {
        logger.info("Bank is started.");
        Objects.requireNonNull(this.getCommand("deposit")).setExecutor((CommandExecutor) new BankCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("withdraw")).setExecutor((CommandExecutor) new BankCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("transfer")).setExecutor((CommandExecutor) new BankCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("balance")).setExecutor((CommandExecutor) new BankCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("me")).setExecutor(new Me());
        Objects.requireNonNull(this.getCommand("try")).setExecutor(new Try());
        this.getServer().getPluginManager().registerEvents(new EventsListener(), this);
        Objects.requireNonNull(this.getCommand("disaster")).setExecutor(new Disaster());
        Objects.requireNonNull(getCommand("minusfps")).setExecutor(new MinusFps());
        ExplodeArrow listener = new ExplodeArrow(this);

        // Регистрируем команду и обработчик событий
        getCommand("giveExplodingArrow").setExecutor(listener);
        getServer().getPluginManager().registerEvents(listener, this);

        motdManager = new MotdManager(getDataFolder());
        Objects.requireNonNull(this.getCommand("togglelink")).setExecutor(motdManager);

        discordBot = new DiscordBot();
        try {
            String discordToken = "MTE0MzYxODczNDU5MDc5OTkwMg.GBIzS1.tBnYdkqK-3bGaC2aCq49szLwCvuBtPJl4xpI_c"; // Укажите ваш токен
            discordBot.startBot(discordToken);
            getLogger().info("Discord бот успешно запущен.");
        } catch (Exception e) {
            getLogger().severe("Ошибка при запуске Discord бота: " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        logger.info("Bank is stopped.");
        if (discordBot != null) {
            discordBot.stopBot();
            getLogger().info("Discord бот остановлен.");
        }
    }
}