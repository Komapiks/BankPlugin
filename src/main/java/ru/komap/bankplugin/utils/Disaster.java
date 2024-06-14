package ru.komap.bankplugin.utils;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Disaster implements CommandExecutor {
    Random rand = new Random();

    public Disaster() {
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player && commandSender.isOp()) {
            World currentWorld = player.getWorld();
            Location playerLocation = player.getLocation();
            int x = this.rand.nextInt(-100, 101);
            int z = this.rand.nextInt(-100, 101);
            Location meteorLocation = playerLocation.add((double)x, 0.0, (double)z);
            int y = currentWorld.getHighestBlockYAt(meteorLocation.getBlockX(), meteorLocation.getBlockZ());
            meteorLocation.setY((double)y);
            this.generateNetherrackSphere(meteorLocation, Integer.parseInt(strings[0]));
            Bukkit.broadcastMessage("Meteor was fallen");
            return true;
        } else {
            return false;
        }
    }

    public void generateNetherrackSphere(Location center, int radius) {
        World world = center.getWorld();
        int centerX = center.getBlockX();
        int centerY = center.getBlockY();
        int centerZ = center.getBlockZ();

        for(int x = centerX - radius; x <= centerX + radius; ++x) {
            for(int y = centerY - radius; y <= centerY + radius; ++y) {
                for(int z = centerZ - radius; z <= centerZ + radius; ++z) {
                    double distance = Math.sqrt(Math.pow((double)(centerX - x), 2.0) + Math.pow((double)(centerY - y), 2.0) + Math.pow((double)(centerZ - z), 2.0));
                    if (distance <= (double)radius) {
                        world.getBlockAt(x, y, z).setType(Material.NETHERRACK);
                    }
                }
            }
        }

    }
}