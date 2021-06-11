package com.yiorno.mofureplant;

import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class Data {

    static List<Material> seedCrops = new ArrayList<>();
    static List<World> shigen = new ArrayList<>();

    //List<Material> rawCrops = new ArrayList<>();

    public void registerMaterials(){

        seedCrops.add(Material.WHEAT);
        seedCrops.add(Material.BEETROOT);

    }

    public void registerWorlds(){

        shigen.add(getServer().getWorld("saishu"));
        shigen.add(getServer().getWorld("shigen_normal"));
        shigen.add(getServer().getWorld("shigen_nether"));
        shigen.add(getServer().getWorld("shigen_the_end"));

    }

}
