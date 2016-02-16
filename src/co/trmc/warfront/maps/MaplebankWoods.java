package co.trmc.warfront.maps;

import co.trmc.dream.DreamPlayer;
import co.trmc.dream.game.DreamTeam;
import co.trmc.dream.game.util.Activatable;
import co.trmc.dream.game.util.SerializedLocation;
import co.trmc.warfront.gamemode.Map;
import co.trmc.warfront.gamemode.cores.util.DTMMonument;
import co.trmc.warfront.gamemode.cores.util.NoBuildRegion;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

public class MaplebankWoods extends Map {

    String mapName = "Maplebank Woods";
    String[] creators = {"ILavaYou", "danshrdr"};
    String[] gamemodes = {"TDM", "KoTH", "DTM", "LP"};
    ArrayList<Activatable> monuments = new ArrayList<Activatable>();
    Material[] disabledDrops = defaultDisabledDrops();
    DreamTeam team1 = new DreamTeam("Forest Team", ChatColor.DARK_GREEN, 35);
    DreamTeam team2 = new DreamTeam("River Team", ChatColor.DARK_AQUA, 35);
    long timeLockTime = 15000L;

    public void readyAttributes() {
        setMapName(mapName);
        setCreators(creators);
        setGamemodes(gamemodes);
        setDisabledDrops(disabledDrops);
        setAllowBuild(true, true);
        setTimeLockTime(timeLockTime);
        registerTeam(team1);
        registerTeam(team2);
        attributes.put("remainRequirement", 0);
    }

    protected void readySpawns() {
        addTeamSpawn(team2, new SerializedLocation(41, 90, 90, 180F, 0F));
        addTeamSpawn(team1, new SerializedLocation(-13, 90, 90, 180F, 0F));

        setSpectatorSpawn(new SerializedLocation(14.5, 96, 96.5, 180F, 0F));

        monuments.add(new DTMMonument(-9, 103, 36, -14, 109, 32, Material.OBSIDIAN, team1));
        monuments.add(new DTMMonument(42, 103, 36, 38, 108, 32, Material.OBSIDIAN, team2));
        attributes.put("monuments", monuments);

        ((ArrayList<Activatable>) attributes.get("noBuild")).add(new NoBuildRegion(-8, 79, -18, 94));
        ((ArrayList<Activatable>) attributes.get("noBuild")).add(new NoBuildRegion(36, 79, 46, 94));

        attributes.put("kothFlag", new SerializedLocation(14, 100, -7));
    }

    protected void applyInventory(DreamPlayer target) {
        PlayerInventory inv = target.getPlayer().getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.currentTeam));
        inv.setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
        inv.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
        inv.setBoots(new ItemStack(Material.IRON_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.IRON_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.DIAMOND_PICKAXE, 1));
        inv.setItem(3, new ItemStack(Material.STONE_AXE, 1));
        inv.setItem(4, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(5, new ItemStack(Material.GOLDEN_APPLE, 2));
        inv.setItem(6, new ItemStack(Material.LOG, 16));
        inv.setItem(10, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Location equiv = event.getBlock().getLocation().clone();
        equiv.setY(59);
        if (equiv.getBlock().getType() != Material.BEDROCK) event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Location equiv = event.getBlock().getLocation().clone();
        equiv.setY(59);
        if (equiv.getBlock().getType() != Material.BEDROCK) event.setCancelled(true);
    }
}
