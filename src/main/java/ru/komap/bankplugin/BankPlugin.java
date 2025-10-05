package ru.komap.bankplugin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Logger;

public final class BankPlugin extends JavaPlugin implements CommandExecutor {
    public Logger logger = this.getLogger();

    @Override
    public void onEnable() {
        logger.info("Bank is started.");
        Objects.requireNonNull(this.getCommand("deposit")).setExecutor((CommandExecutor) new BankCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("withdraw")).setExecutor((CommandExecutor) new BankCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("transfer")).setExecutor((CommandExecutor) new BankCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("balance")).setExecutor((CommandExecutor) new BankCommandExecutor(this));



    }

    @Override
    public void onDisable() {
        logger.info("Bank is stopped.");
    }
}