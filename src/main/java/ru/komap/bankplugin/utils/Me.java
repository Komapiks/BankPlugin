package ru.komap.bankplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Me implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        boolean work = true;
        if (work) {
            Object msg = "";
            for (int i = 0; i <= strings.length - 1; ++i) {
                msg = (String)msg + strings[i] + " ";
            }
            Player player = (Player)commandSender;
            Bukkit.broadcastMessage((String)("*" + player.getName() + " " + ChatColor.GREEN + (String)msg));
            return true;
        }
        return false;
    }
}
