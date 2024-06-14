package ru.komap.bankplugin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import ru.komap.bankplugin.utils.*;

import java.util.Objects;

public final class BankPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getLogger().info("Bank is started.");
        Objects.requireNonNull(this.getCommand("deposit")).setExecutor((CommandExecutor)new BankCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("withdraw")).setExecutor((CommandExecutor)new BankCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("transfer")).setExecutor((CommandExecutor)new BankCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("balance")).setExecutor((CommandExecutor)new BankCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("me")).setExecutor(new Me());
        Objects.requireNonNull(this.getCommand("try")).setExecutor(new Try());
        this.getServer().getPluginManager().registerEvents(new EventsListener(), this);
        Objects.requireNonNull(this.getCommand("disaster")).setExecutor(new Disaster());
        Objects.requireNonNull(getCommand("minusfps")).setExecutor(new MinusFps());
    }
    @Override
    public void onDisable() {
        this.getLogger().info("Bank is stopped.");
    }
}