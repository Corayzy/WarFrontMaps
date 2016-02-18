package co.trmc.warfront.maps;

import co.trmc.dream.DreamPlayer;
import co.trmc.dream.game.DreamTeam;
import co.trmc.dream.game.util.Activatable;
import co.trmc.dream.game.util.SerializedLocation;
import co.trmc.warfront.gamemode.Map;
import co.trmc.warfront.gamemode.cores.util.DTMMonument;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

public class Squared extends Map {

    String mapName = "Squared";
    String[] creators = {"ILavaYou", "jakemake2"};
    String[] gamemodes = {"DTM", "TDM", "LP"};
    ArrayList<Activatable> monuments = new ArrayList<Activatable>();
    Material[] disabledDrops = defaultDisabledDrops();
    DreamTeam team1 = new DreamTeam("Red Team", ChatColor.RED, 25);
    DreamTeam team2 = new DreamTeam("Green Team", ChatColor.GREEN, 25);

    public void readyAttributes() {
        setMapName(mapName);
        setCreators(creators);
        setGamemodes(gamemodes);
        setDisabledDrops(disabledDrops);
        setAllowBuild(true, true);
        registerTeam(team1);
        registerTeam(team2);
    }

    protected void readySpawns() {
        addTeamSpawn(team1, new SerializedLocation(-116, 93, -20, 270F, -1.75F));

        addTeamSpawn(team2, new SerializedLocation(24, 93, -20, 90F, -0.4F));

        setSpectatorSpawn(new SerializedLocation(-45, 103, -76.5, 0F, 0F));

        monuments.add(new DTMMonument(-80, 93, -20, -81, 98, -19, Material.STAINED_GLASS, team1));
        monuments.add(new DTMMonument(-11, 93, -20, -10, 98, -19, Material.STAINED_GLASS, team2));
        attributes.put("monuments", monuments);
    }

    protected void applyInventory(DreamPlayer target) {
        PlayerInventory inv = target.getPlayer().getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.currentTeam));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.currentTeam));
        inv.setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), target.currentTeam));
        inv.setBoots(colorArmor(new ItemStack(Material.LEATHER_BOOTS, 1), target.currentTeam));

        inv.setItem(0, new ItemStack(Material.IRON_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.IRON_PICKAXE, 1));
        inv.setItem(3, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(4, new ItemStack(Material.POTION, 1, (short) 8229));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Location loc = event.getBlockPlaced().getLocation();
        if (!(loc.getBlockZ() >= -51 && loc.getBlockZ() <= 12 && loc.getBlockX() >= -103 && loc.getBlockX() <= 12)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockBreakEvent event) {
        Location loc = event.getBlock().getLocation();
        if (!(loc.getBlockZ() >= -51 && loc.getBlockZ() <= 12 && loc.getBlockX() >= -103 && loc.getBlockX() <= 12)) {
            event.setCancelled(true);
        }
    }
}
