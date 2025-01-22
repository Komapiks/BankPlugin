package ru.komap.bankplugin.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class MotdManager implements CommandExecutor {

    private final Set<UUID> disabledPlayers = new HashSet<>();
    private final Gson gson = new Gson();
    private final File dataFile;

    public MotdManager(File dataFolder) {
        this.dataFile = new File(dataFolder, "players_bp.json");
        // Загрузка данных отключенных игроков при создании объекта
        loadDisabledPlayers();
        startMotdTask();
    }

    private void startMotdTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                String message = ChatColor.GREEN + "Для продолжения игры на нашем сервере вам надо вступить в дискорд сервер: " + ChatColor.BLUE + ChatColor.UNDERLINE
                        + "https://discord.com/invite/2bKRBNswyP";

                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!disabledPlayers.contains(player.getUniqueId())) {
                        player.spigot().sendMessage(
                                net.md_5.bungee.api.chat.TextComponent.fromLegacyText(message)
                        );
                    }
                }
            }
        }.runTaskTimerAsynchronously(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("BankPlugin")), 0, 8 * 60 * 20);
    }

    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        UUID playerId = player.getUniqueId();

        // Переключение состояния получения сообщений
        if (disabledPlayers.contains(playerId)) {
            disabledPlayers.remove(playerId);
            player.sendMessage(ChatColor.GREEN + "You will now receive link messages.");
        } else {
            disabledPlayers.add(playerId);
            player.sendMessage(ChatColor.RED + "You have disabled link messages.");
        }

        saveDisabledPlayers(); // Сохранение состояния сразу после изменения
        return true;
    }

    private void loadDisabledPlayers() {
        if (!dataFile.exists()) {
            return;
        }

        try (FileReader reader = new FileReader(dataFile)) {
            Type type = new TypeToken<Set<UUID>>() {}.getType();
            Set<UUID> loadedPlayers = gson.fromJson(reader, type);
            if (loadedPlayers != null) {
                disabledPlayers.addAll(loadedPlayers);
            }
            Bukkit.getLogger().info("Loaded disabled players from file.");
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not load disabled players: " + e.getMessage());
        }
    }

    private void saveDisabledPlayers() {
        if (!dataFile.getParentFile().exists() && !dataFile.getParentFile().mkdirs()) {
            Bukkit.getLogger().severe("Could not create plugin data directory.");
            return;
        }

        try (FileWriter writer = new FileWriter(dataFile)) {
            gson.toJson(disabledPlayers, writer);
            Bukkit.getLogger().info("Disabled players saved to file.");
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save disabled players: " + e.getMessage());
        }
    }
}