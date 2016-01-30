package co.trmc.warfront.maps;

import co.trmc.dream.DreamPlayer;
import co.trmc.dream.game.DreamTeam;
import co.trmc.dream.game.util.Activatable;
import co.trmc.dream.game.util.SerializedLocation;
import co.trmc.warfront.gamemode.Map;
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

public class TheRebellion extends Map {

    String mapName = "The Rebellion";
    String[] creators = {"ILavaYou", "danshrdr"};
    String[] gamemodes = {"TDM", "LP"};
    Material[] disabledDrops = defaultDisabledDrops();
    DreamTeam team1 = new DreamTeam("Protectors", ChatColor.RED, 55);
    DreamTeam team2 = new DreamTeam("Invaders", ChatColor.BLUE, 55);
    long timeLockTime = 14000L;

    public void readyAttributes() {
        setMapName(mapName);
        setCreators(creators);
        setGamemodes(gamemodes);
        setDisabledDrops(disabledDrops);
        setAllowBuild(true, true);
        setTimeLockTime(timeLockTime);
        registerTeam(team1);
        registerTeam(team2);
    }

    protected void readySpawns() {
        addTeamSpawn(team1, new SerializedLocation(-112, 146, 43, 180F, -0.3F));
        addTeamSpawn(team1, new SerializedLocation(-67, 146, 100, 0F, 0F));

        addTeamSpawn(team2, new SerializedLocation(-13, 89, 39, 127.7F, 2F));
        addTeamSpawn(team2, new SerializedLocation(-26, 89, 12, 37F, -1F));

        setSpectatorSpawn(new SerializedLocation(1.2, 112, -0.3, 45F, 25F));

        ((ArrayList<Activatable>) attributes.get("noBuild")).add(new NoBuildRegion(-28, 13, -25, 10));
        ((ArrayList<Activatable>) attributes.get("noBuild")).add(new NoBuildRegion(-15, 37, -12, 40));
        ((ArrayList<Activatable>) attributes.get("noBuild")).add(new NoBuildRegion(-106, 49, -118, 37));
        ((ArrayList<Activatable>) attributes.get("noBuild")).add(new NoBuildRegion(-73, 106, -61, 94));
    }

    protected void applyInventory(DreamPlayer target) {
        PlayerInventory inv = target.getPlayer().getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.currentTeam));
        inv.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE, 1));
        inv.setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), target.currentTeam));
        inv.setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.IRON_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.STONE_PICKAXE, 1));
        inv.setItem(3, new ItemStack(Material.IRON_AXE, 1));
        inv.setItem(4, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(5, new ItemStack(Material.GOLDEN_APPLE, 1));
        inv.setItem(6, new ItemStack(Material.LOG, 16));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Location equiv = event.getBlock().getLocation().clone();
        equiv.setY(65);
        if (equiv.getBlock().getType() != Material.BEDROCK) event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Location equiv = event.getBlock().getLocation().clone();
        equiv.setY(65);
        if (equiv.getBlock().getType() != Material.BEDROCK) event.setCancelled(true);
    }
}
