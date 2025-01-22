package ru.komap.bankplugin.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EventsListener implements Listener {
    Random rand = new Random();

    public EventsListener() {
    }

    /** @deprecated */
    @Deprecated
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity currentEntity = event.getEntity();
        int healthLvl;
        if (currentEntity instanceof Zombie) {
            Zombie entity = (Zombie)event.getEntity();
            healthLvl = this.rand.nextInt(15, 76);
            if (healthLvl >= 30 && healthLvl <= 64) {
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, -1, 0));
            } else if (healthLvl >= 65) {
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, -1, 1));
                entity.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, -1, 1));
            }

            entity.setMaxHealth((double)healthLvl);
            entity.setHealth((double)healthLvl);
        } else if (currentEntity instanceof Skeleton) {
            Skeleton entity = (Skeleton)event.getEntity();
            healthLvl = this.rand.nextInt(15, 76);
            if (healthLvl >= 30 && healthLvl <= 64) {
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, -1, 0));
            } else if (healthLvl >= 65) {
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, -1, 1));
            }

            entity.setMaxHealth((double)healthLvl);
            entity.setHealth((double)healthLvl);
        } else if (currentEntity instanceof Creeper) {
            Creeper entity = (Creeper)event.getEntity();
            healthLvl = this.rand.nextInt(15, 76);
            if (healthLvl >= 30 && healthLvl <= 64) {
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, -1, 0));
            } else if (healthLvl >= 65) {
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, -1, 1));
                entity.setExplosionRadius(6);
                entity.setMaxFuseTicks(70);
                entity.setFuseTicks(70);
            }

            entity.setMaxHealth((double)healthLvl);
            entity.setHealth((double)healthLvl);
        } else if (currentEntity instanceof Spider) {
            Spider entity = (Spider)event.getEntity();
            healthLvl = this.rand.nextInt(8, 46);
            if (healthLvl >= 30) {
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, -1, 1));
                entity.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, -1, 0));
                entity.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, -1, 1));
                entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, -1, 1));
                entity.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, -1, 0));
            }

            entity.setMaxHealth((double)healthLvl);
            entity.setHealth((double)healthLvl);
        } else if (currentEntity instanceof Phantom) {
            Phantom entity = (Phantom)event.getEntity();
            healthLvl = this.rand.nextInt(5, 41);
            if (healthLvl >= 30) {
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, -1, 0));
                entity.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, -1, 0));
            }

            entity.setMaxHealth((double)healthLvl);
            entity.setHealth((double)healthLvl);
        }

    }

    @EventHandler
    public void creeperIgnited(ExplosionPrimeEvent event) {
        if (event.getEntity() instanceof Creeper && ((Creeper)event.getEntity()).hasPotionEffect(PotionEffectType.SLOWNESS)) {
            ((Creeper)event.getEntity()).removePotionEffect(PotionEffectType.SLOWNESS);
        }

    }

    @EventHandler
    public void craftingEvent(CraftItemEvent event) {
        List<String> legendaryList = new ArrayList();
        legendaryList.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Легендарный..");
        legendaryList.add(ChatColor.GOLD + "" + ChatColor.BOLD + "+ 5 урона");
        List<String> sharpenList = new ArrayList();
        sharpenList.add(ChatColor.WHITE + "" + ChatColor.BOLD + "Острый..");
        sharpenList.add(ChatColor.WHITE + "" + ChatColor.BOLD + "+ 3 урона");
        List<String> longList = new ArrayList();
        longList.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Cломанный..");
        longList.add(ChatColor.GRAY + "" + ChatColor.BOLD + "- 5 скорости атаки");
        AttributeModifier longModif1 = new AttributeModifier(UUID.randomUUID(), "Legendary Modifier", 2.0, Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier longModif2 = new AttributeModifier(UUID.randomUUID(), "Legendary Modifier", -5.0, Operation.ADD_NUMBER, EquipmentSlot.HAND);
        ItemStack result = event.getRecipe().getResult();
        if (result != null && result.toString().contains("_SWORD")) {
            ItemMeta itemMeta = result.getItemMeta();
            Random rand = new Random();
            int modificator = rand.nextInt(0, 16);
            if (modificator == 0) {
                itemMeta.setLore(legendaryList);
                if (result.toString().contains("DIAMOND_SWORD")) {
                    itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, getLM(7));
                } else if (result.toString().contains("IRON_SWORD")) {
                    itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, getLM(6));
                } else if (result.toString().contains("GOLDEN_SWORD")) {
                    itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, getLM(4));
                } else if (result.toString().contains("STONE_SWORD")) {
                    itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, getLM(5));
                } else if (result.toString().contains("WOODEN_SWORD")) {
                    itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, getLM(4));
                } else if (result.toString().contains("NETHERITE_SWORD")) {
                    itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, getLM(8));
                }

                result.setItemMeta(itemMeta);
                event.getInventory().setResult(result);
            }

            if (modificator >= 1 && modificator <= 4) {
                itemMeta.setLore(sharpenList);
                if (result.toString().contains("DIAMOND_SWORD")) {
                    itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, getSM(7));
                } else if (result.toString().contains("IRON_SWORD")) {
                    itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, getSM(6));
                } else if (result.toString().contains("GOLDEN_SWORD")) {
                    itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, getSM(4));
                } else if (result.toString().contains("STONE_SWORD")) {
                    itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, getSM(5));
                } else if (result.toString().contains("WOODEN_SWORD")) {
                    itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, getSM(4));
                } else if (result.toString().contains("NETHERITE_SWORD")) {
                    itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, getSM(8));
                }

                result.setItemMeta(itemMeta);
                event.getInventory().setResult(result);
            }

            if (modificator == 5) {
                itemMeta.setLore(longList);
                itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_KNOCKBACK, longModif1);
                itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, longModif2);
                result.setItemMeta(itemMeta);
                event.getInventory().setResult(result);
            }
        }

    }

    static AttributeModifier getLM(int m) {
        AttributeModifier legendaryModif = new AttributeModifier(UUID.randomUUID(), "Legendary Modifier", (double)(5 + m), Operation.ADD_NUMBER, EquipmentSlot.HAND);
        return legendaryModif;
    }

    static AttributeModifier getSM(int m) {
        AttributeModifier sharpenModifier = new AttributeModifier(UUID.randomUUID(), "Sharpen Modifier", (double)(3 + m), Operation.ADD_NUMBER, EquipmentSlot.HAND);
        return sharpenModifier;
    }
}
