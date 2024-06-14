package ru.komap.bankplugin.utils;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Try implements CommandExecutor {
    Random rand = new Random();

    public Try() {
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        int tried = this.rand.nextInt(2);
        Player player = (Player)commandSender;
        String msg = "";

        for(int i = 0; i <= strings.length - 1; ++i) {
            msg = msg + strings[i] + " ";
        }

        String var10000;
        if (tried == 0) {
            var10000 = player.getName();
            Bukkit.broadcastMessage("*" + var10000 + " \u043f\u043e\u043f\u044b\u0442\u0430\u043b\u0441\u044f " + msg + ChatColor.RED + "\n\u041d\u0435\u0443\u0434\u0430\u0447\u043d\u043e!");
            return true;
        } else if (tried == 1) {
            var10000 = player.getName();
            Bukkit.broadcastMessage("*" + var10000 + " \u043f\u043e\u043f\u044b\u0442\u0430\u043b\u0441\u044f " + msg + ChatColor.GREEN + "\n\u0423\u0434\u0430\u0447\u043d\u043e!");
            return true;
        } else {
            return false;
        }
    }
}