package co.trmc.warfront.maps;

import co.trmc.dream.DreamPlayer;
import co.trmc.dream.game.DreamTeam;
import co.trmc.dream.game.util.SerializedLocation;
import co.trmc.dream.util.DreamUtil;
import co.trmc.warfront.gamemode.Map;
import co.trmc.warfront.gamemode.cores.util.DTMMonument;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

public class Cubed extends Map {

    String mapName = "Cubed";
    String[] creators = {"ILavaYou"};
    String[] gamemodes = {"DTM", "TDM", "LP"};
    ArrayList<DTMMonument> monuments = new ArrayList<DTMMonument>();
    Material[] disabledDrops = {Material.IRON_SWORD, Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE,
            Material.LEATHER_HELMET, Material.GLASS_BOTTLE, Material.LOG, Material.BOW, Material.ARROW, Material.IRON_PICKAXE, Material.IRON_AXE, Material.FEATHER};
    DreamTeam team1 = new DreamTeam("Grass Team", ChatColor.DARK_GREEN, 40);
    DreamTeam team2 = new DreamTeam("Mycelium Team", ChatColor.DARK_PURPLE, 40);
    long timeLockTime = 14000L;
    int matchDuration = 300;

    public void readyAttributes() {
        setMapName(mapName);
        setCreators(creators);
        setGamemodes(gamemodes);
        setDisabledDrops(disabledDrops);
        setAllowBuild(true, true);
        setTimeLockTime(timeLockTime);
        setMatchDuration(matchDuration);
        registerTeam(team1);
        registerTeam(team2);
        attributes.put("remainRequirement", 0);
    }

    protected void readySpawns() {
        addTeamSpawn(team1, new SerializedLocation(-20.5, 106, 38, 270F, 0F));

        addTeamSpawn(team2, new SerializedLocation(109.5, 106, 38, 90F, 0F));

        setSpectatorSpawn(new SerializedLocation(44.5, 105, 96.5, 180F, 0F));

        monuments.add(new DTMMonument(-1, 89, 42, -10, 139, 33, Material.WOOD, team1));
        monuments.add(new DTMMonument(89, 89, 42, 98, 139, 33, Material.WOOD, team2));
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
        inv.setItem(3, new ItemStack(Material.IRON_AXE, 1));
        inv.setItem(4, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(5, new ItemStack(Material.POTION, 2, (short) 8229));
        inv.setItem(6, new ItemStack(Material.LOG, 16));
        inv.setItem(7, DreamUtil.changeItem(new ItemStack(Material.FEATHER, 1), "Soft-lander", "Hold this to take no fall damage!"));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void damage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL && event.getEntity() instanceof Player) {
            Player target = (Player) event.getEntity();
            if (target.getItemInHand().getType() == Material.FEATHER) {
                event.setDamage(0);
                target.sendMessage(ChatColor.BLUE + "Fall damage negated!");
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Location loc = event.getBlockPlaced().getLocation();
        if (!(loc.getBlockZ() >= -3 && loc.getBlockZ() <= 78 && loc.getBlockX() >= -13 && loc.getBlockX() <= 101)) {
            event.setCancelled(true);
        }
    }
}
