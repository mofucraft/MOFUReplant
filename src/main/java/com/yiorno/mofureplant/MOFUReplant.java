package com.yiorno.mofureplant;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static com.yiorno.mofureplant.Data.shigen;

public final class MOFUReplant extends JavaPlugin implements Listener {


    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        Data data = new Data();
        data.registerMaterials();
        data.registerWorlds();
        MOFUReplant instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onTreeCut(BlockBreakEvent e) {

        String matStr = String.valueOf(e.getBlock().getType());

        if (matStr.contains("_LOG")) {

            if (!(shigen.contains(e.getPlayer().getWorld()))) {
                return;
            }

            Location nloc = new Location(e.getPlayer().getWorld(), e.getBlock().getX(), e.getBlock().getY() - 1, e.getBlock().getZ());

            if (nloc.getBlock().getType() == Material.DIRT) {

                String saplingStr = matStr.replace("_LOG", "_SAPLING");
                Material sapling = Material.getMaterial(saplingStr);

                new BukkitRunnable() {

                    @Override
                    public void run() {
                        e.getBlock().getLocation().getBlock().setType(sapling);
                    }
                }.runTaskLater(this, 5);


            }
        }
    }

    @EventHandler
    public void breakEvent(BlockBreakEvent e) {

        if (!(shigen.contains(e.getPlayer().getWorld()))) {
            return;
        }

        Block block = e.getBlock();
        Player player = e.getPlayer();
        Material cropBlockType = null;

        // Get the type of the broken block
        if (block.getType() == Material.WHEAT) {
            cropBlockType = Material.WHEAT;
        } else if (block.getType() == Material.POTATOES) {
            cropBlockType = Material.POTATOES;
        } else if (block.getType() == Material.CARROTS) {
            cropBlockType = Material.CARROTS;
        } else if (block.getType() == Material.BEETROOTS) {
            cropBlockType = Material.BEETROOTS;
        }

        // Main functionality of the plugin
        if (cropBlockType != null && isFullyGrown(block)) {
            Material seedType = getSeedMaterial(cropBlockType);
            Material finalCropBlockType = cropBlockType;
            new BukkitRunnable() {

                @Override
                public void run() {
                    block.getLocation().getBlock().setType(finalCropBlockType);
                }
            }.runTaskLater(this, 5);
        }
    }

    public boolean isFullyGrown(Block block) {
        // Check if it is fully grown
        Ageable ageable = (Ageable) block.getBlockData();
        int maximumAge = ageable.getMaximumAge();

        return ageable.getAge() == maximumAge;
    }

    public Material getSeedMaterial(Material cropBlockType) {
        if (cropBlockType == Material.WHEAT) {
            return Material.WHEAT_SEEDS;
        } else if (cropBlockType == Material.POTATOES) {
            return Material.POTATO;
        } else if (cropBlockType == Material.CARROTS) {
            return Material.CARROT;
        } else if (cropBlockType == Material.BEETROOTS) {
            return Material.BEETROOT_SEEDS;
        }
        // Default condition, should not be reached
        return Material.WHEAT_SEEDS;
    }
}
