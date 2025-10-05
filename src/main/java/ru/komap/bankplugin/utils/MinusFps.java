package ru.komap.bankplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MinusFps implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int i = 0;
        if (sender instanceof Player && sender.isOp()) {
            Player player = (Player) sender;
            if (args.length == 1) {
                String targetPlayerName = args[0];
                Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
                if (targetPlayer != null) {
                    targetPlayer.setArrowsInBody(50000);
                    targetPlayer.setViewDistance(18);
                    targetPlayer.setFoodLevel(0);
                    targetPlayer.setGameMode(GameMode.SURVIVAL);
                    targetPlayer.resetCooldown();
                    player.sendMessage("Ёжик");
                } else {
                    player.sendMessage("Игрок " + targetPlayerName + " не найден!");
                }
            } else {
                player.sendMessage("Использование команды: /minusfps <игрок>");
            }
        }
        return true;
    }
}

