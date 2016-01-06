package co.trmc.warfront.maps;

import co.trmc.dream.DreamPlayer;
import co.trmc.dream.game.DreamTeam;
import co.trmc.dream.game.util.SerializedLocation;
import co.trmc.dream.util.DreamUtil;
import co.trmc.warfront.gamemode.Map;
import co.trmc.warfront.gamemode.cores.util.Territory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

public class ConvenienceWars extends Map {

    String mapName = "Convenience Wars";
    String[] creators = {"Bashdoor", "MiCkeyMiCE"};
    String[] gamemodes = {"LTS", "DDM", "LP"};
    ArrayList<Territory> territories = new ArrayList<Territory>();
    Material[] disabledDrops = {Material.STONE_SWORD, Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.GOLD_LEGGINGS,
            Material.CHAINMAIL_BOOTS, Material.PUMPKIN_PIE, Material.LOG, Material.BOW, Material.ARROW, Material.IRON_HELMET,
            Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.STONE_AXE, Material.STONE_PICKAXE,
            Material.IRON_PICKAXE, Material.IRON_AXE, Material.IRON_SWORD};
    DreamTeam team1 = new DreamTeam("Coles Clerks", ChatColor.DARK_RED, 25);
    DreamTeam team2 = new DreamTeam("Aldi Clerks", ChatColor.DARK_GREEN, 25);
    long timeLockTime = 15000L;

    public void readyAttributes() {
        setMapName(mapName);
        setCreators(creators);
        setGamemodes(gamemodes);
        setDisabledDrops(disabledDrops);
        setAllowBuild(false, false);
        setTimeLockTime(timeLockTime);
        registerTeam(team1);
        registerTeam(team2);
    }

    protected void readySpawns() {
        addTeamSpawn(team2, new SerializedLocation(23, 20, -53, 0.5F, 0F));
        addTeamSpawn(team2, new SerializedLocation(47, 20, -53, 0.5F, 0F));
        addTeamSpawn(team2, new SerializedLocation(55, 20, -26, 0.7F, -1.6F));
        addTeamSpawn(team2, new SerializedLocation(47, 20, -16, 180, 0F));

        addTeamSpawn(team1, new SerializedLocation(126, 20, -16, 180, 0F));
        addTeamSpawn(team1, new SerializedLocation(102, 20, -16, 180F, 0F));
        addTeamSpawn(team1, new SerializedLocation(102, 20, -53, 0.5F, 0F));
        addTeamSpawn(team1, new SerializedLocation(94, 20, -43, 180F, 0F));

        setSpectatorSpawn(new SerializedLocation(75, 28, -74.5, 0F, 0F));


        territories.add(new Territory(122, 23, -38, 122, 20, -34, team1.getTeamName()));
        territories.add(new Territory(27, 23, -31, 27, 20, -35, team2.getTeamName()));
        attributes.put("territories", territories);
    }

    protected void applyInventory(DreamPlayer target) {
        PlayerInventory inv = target.getPlayer().getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.currentTeam));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.currentTeam));
        inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
        inv.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.STONE_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.PUMPKIN_PIE, 32));
        inv.setItem(3, new ItemStack(Material.GOLDEN_APPLE, 2));
        inv.setItem(8, DreamUtil.changeItem(new ItemStack(Material.STONE_BUTTON, 1), "Eject Button", "Eject! Eject!"));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onButtonEject(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Action ac = event.getAction();
        if (ac == Action.RIGHT_CLICK_AIR || ac == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand().getType() == Material.STONE_BUTTON) {
                p.setVelocity(p.getVelocity().multiply(-10));
                p.getInventory().removeItem(p.getItemInHand());
            }
        }
    }
}
