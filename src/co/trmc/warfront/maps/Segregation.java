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
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

public class Segregation extends Map {

    String mapName = "Segregation";
    String[] creators = {"huego", "ILavaYou"};
    String[] gamemodes = {"TDM", "DTM", "LP", "KoTH"};
    ArrayList<DTMMonument> monuments = new ArrayList<DTMMonument>();
    Material[] disabledDrops = {Material.STONE_SWORD, Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.GOLD_LEGGINGS,
            Material.DIAMOND_BOOTS, Material.BREAD, Material.LOG, Material.BOW, Material.ARROW, Material.IRON_HELMET,
            Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.STONE_AXE, Material.DIAMOND_PICKAXE,
            Material.IRON_PICKAXE, Material.IRON_AXE, Material.IRON_SWORD};
    DreamTeam team1 = new DreamTeam("Isolated", ChatColor.DARK_GRAY, 55);
    DreamTeam team2 = new DreamTeam("Protected", ChatColor.GOLD, 55);
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
        addTeamSpawn(team2, new SerializedLocation(43, 78, 51, 218F, 2.2F));
        addTeamSpawn(team1, new SerializedLocation(-45, 78, -52, 37.6F, 1.4F));

        setSpectatorSpawn(new SerializedLocation(-0.5, 81, 95.5, 180F, 0F));

        monuments.add(new DTMMonument(-67, 67, 9, -70, 98, 16, Material.STAINED_CLAY, team1));
        monuments.add(new DTMMonument(65, 67, -10, 68, 98, -17, Material.STAINED_CLAY, team2));
        attributes.put("monuments", monuments);

        ((ArrayList<Activatable>) attributes.get("noBuild")).add(new NoBuildRegion(47, 48, 39, 56));
        ((ArrayList<Activatable>) attributes.get("noBuild")).add(new NoBuildRegion(-49, -49, -41, -57));

        attributes.put("kothFlag", new SerializedLocation(-1, 57, -1));
    }

    protected void applyInventory(DreamPlayer target) {
        PlayerInventory inv = target.getPlayer().getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.currentTeam));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.currentTeam));
        inv.setLeggings(new ItemStack(Material.GOLD_LEGGINGS, 1));
        inv.setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.IRON_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.DIAMOND_PICKAXE, 1));
        inv.setItem(3, new ItemStack(Material.STONE_AXE, 1));
        inv.setItem(4, new ItemStack(Material.BREAD, 32));
        inv.setItem(5, new ItemStack(Material.GOLDEN_APPLE, 2));
        inv.setItem(6, new ItemStack(Material.WOOD, 16));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Location equiv = event.getBlock().getLocation().clone();
        equiv.setY(29);
        if (equiv.getBlock().getType() != Material.BEDROCK) event.setCancelled(true);
    }
}
