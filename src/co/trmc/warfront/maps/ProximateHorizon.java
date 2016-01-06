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

public class ProximateHorizon extends Map {

    String mapName = "Proximate Horizon";
    String[] creators = {"huego", "MiCkEyMiCE"};
    String[] gamemodes = {"LP", "DTM"};
    ArrayList<DTMMonument> monuments = new ArrayList<DTMMonument>();
    Material[] disabledDrops = defaultDisabledDrops();
    DreamTeam team1 = new DreamTeam("Red Team", ChatColor.RED, 50);
    DreamTeam team2 = new DreamTeam("Blue Team", ChatColor.BLUE, 50);
    long timeLockTime = 6000L;

    public void readyAttributes() {
        setMapName(mapName);
        setCreators(creators);
        setGamemodes(gamemodes);
        setDisabledDrops(disabledDrops);
        setAllowBuild(true, true);
        setTimeLockTime(timeLockTime);
        registerTeam(team1);
        registerTeam(team2);
        attributes.put("captureRequirement", 1);
    }

    @SuppressWarnings("unchecked")
    protected void readySpawns() {
        addTeamSpawn(team1, new SerializedLocation(-3, 23, 49, 90F, 0.7F));

        addTeamSpawn(team2, new SerializedLocation(-349, 23, 51, 270F, 2.6F));

        setSpectatorSpawn(new SerializedLocation(-176, 28, -24.5, 0F, 0F));

        ((ArrayList<Activatable>) attributes.get("noBuild")).add(new NoBuildRegion(-336, 42, -352, 60));
        ((ArrayList<Activatable>) attributes.get("noBuild")).add(new NoBuildRegion(-16, 58, 0, 40));

        monuments.add(new DTMMonument(-69, 47, 58, -73, 49, 63, Material.STAINED_CLAY, team1));
        monuments.add(new DTMMonument(-279, 47, 42, -283, 49, 37, Material.STAINED_CLAY, team2));
        attributes.put("monuments", monuments);
    }

    protected void applyInventory(DreamPlayer target) {
        PlayerInventory inv = target.getPlayer().getInventory();

        inv.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET, 1));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.currentTeam));
        inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
        inv.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.IRON_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.STONE_PICKAXE, 1));
        inv.setItem(3, new ItemStack(Material.PUMPKIN_PIE, 6));
        inv.setItem(4, new ItemStack(Material.GOLDEN_APPLE, 1));
        inv.setItem(5, new ItemStack(Material.LOG, 16));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Location loc = event.getBlockPlaced().getLocation();
        if (!(loc.getBlockZ() > -5 && loc.getBlockZ() < 102 && loc.getBlockX() < 3 && loc.getBlockX() > -355)
                || event.getBlockPlaced().getType() == Material.TNT) {
            event.setCancelled(true);
        }
    }
}
