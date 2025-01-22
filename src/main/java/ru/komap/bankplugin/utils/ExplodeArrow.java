package ru.komap.bankplugin.utils;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class ExplodeArrow implements Listener, CommandExecutor {
    private final JavaPlugin plugin;
    private final Set<UUID> explosiveArrows = new HashSet<>();

    public ExplodeArrow(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // Метод для обработки команды выдачи взрывной стрелы
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Эту команду могут использовать только игроки!");
            return true;
        }

        // Проверяем, что у игрока есть права оператора
        if (player.isOp()) {
            // Создаем специальную стрелу
            ItemStack arrow = new ItemStack(Material.ARROW, 1);

            // Выдаём стрелу игроку
            player.getInventory().addItem(arrow);
            player.sendMessage("Вы получили специальную взрывную стрелу!");
            return true;
        } else {
            player.sendMessage("Эта команда доступна только операторам.");
            return true;
        }
    }

    // Добавление выпущенной взрывной стрелы в карту
    @EventHandler
    public void onPlayerShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack arrowItem = player.getInventory().getItemInOffHand().getType() == Material.ARROW
                    ? player.getInventory().getItemInOffHand()
                    : player.getInventory().getItemInMainHand();

            // Проверка, что используется именно выданная командами стрела
            if (arrowItem.getType() == Material.ARROW && player.isOp()) {
                // Добавляем UUID выпущенной стрелы в Set explosiveArrows
                if (event.getProjectile() instanceof Arrow arrow) {
                    explosiveArrows.add(arrow.getUniqueId());
                }
            }
        }
    }

    // Проверка при попадании стрелы в блок
    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow arrow) {
            // Проверяем, есть ли UUID этой стрелы в карте explosiveArrows
            if (explosiveArrows.contains(arrow.getUniqueId())) {
                arrow.getWorld().createExplosion(arrow.getLocation(), 2.0F, false, false);
                arrow.remove();
                explosiveArrows.remove(arrow.getUniqueId()); // Удаляем UUID стрелы после взрыва
            }
        }
    }
}