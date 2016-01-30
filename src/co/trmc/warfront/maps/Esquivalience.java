package co.trmc.warfront.maps;

import co.trmc.dream.DreamPlayer;
import co.trmc.dream.game.DreamTeam;
import co.trmc.dream.game.util.SerializedLocation;
import co.trmc.warfront.gamemode.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Esquivalience extends Map {

    String mapName = "Esquivalience";
    String[] creators = {"ILavaYou", "MiCkEyMiCE", "IIDashII"};
    String[] gamemodes = {"FFA", "LMS"};
    Material[] disabledDrops = {Material.STONE_SWORD, Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE,
            Material.LEATHER_HELMET, Material.BOW, Material.ARROW};
    DreamTeam team1 = new DreamTeam("Illusory", ChatColor.BLUE, 60);
    long timeLockTime = 14000L;

    public void readyAttributes() {
        setMapName(mapName);
        setCreators(creators);
        setGamemodes(gamemodes);
        setDisabledDrops(disabledDrops);
        setAllowBuild(false, false);
        setTimeLockTime(timeLockTime);
        registerTeam(team1);
    }

    protected void readySpawns() {
        addTeamSpawn(team1, new SerializedLocation(50, 68, -77, 59.5F, -4.5F));
        addTeamSpawn(team1, new SerializedLocation(36, 68, -100, 5.7F, -3.5F));
        addTeamSpawn(team1, new SerializedLocation(16, 68, -111, 300.7F, 0.8F));
        addTeamSpawn(team1, new SerializedLocation(15, 68, -88, 63.5F, -1.5F));
        addTeamSpawn(team1, new SerializedLocation(4, 70, -56, 136.77F, -3.4F));
        addTeamSpawn(team1, new SerializedLocation(11, 70, -43, 141.1F, 8.8F));
        addTeamSpawn(team1, new SerializedLocation(7, 80, -28, 163.2F, 6.6F));
        addTeamSpawn(team1, new SerializedLocation(18, 80, -15, 169F, 2.9F));
        addTeamSpawn(team1, new SerializedLocation(-13, 80, -7, 179.1F, 0.1F));
        addTeamSpawn(team1, new SerializedLocation(-18, 80, -37, 223.6F, -2F));
        addTeamSpawn(team1, new SerializedLocation(-45, 80, -39, 246.5F, 7F));
        addTeamSpawn(team1, new SerializedLocation(-51, 80, -60, 232.2F, 6.6F));
        addTeamSpawn(team1, new SerializedLocation(-55, 83, -90, 263F, 1.7F));
        addTeamSpawn(team1, new SerializedLocation(-47, 68, -121, 315.5F, -0.2F));
        addTeamSpawn(team1, new SerializedLocation(-39, 71, -99, 340F, 0.7F));
        addTeamSpawn(team1, new SerializedLocation(-35, 71, -80, 250F, 0F));
        addTeamSpawn(team1, new SerializedLocation(-13, 70, -84, 251.4F, -1.6F));
        addTeamSpawn(team1, new SerializedLocation(-22, 69, -129, 253.1F, -3F));

        setSpectatorSpawn(new SerializedLocation(46.5, 103, -39.5, 312F, 30F));
    }

    protected void applyInventory(DreamPlayer target) {
        PlayerInventory inv = target.getPlayer().getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.currentTeam));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.currentTeam));
        inv.setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), target.currentTeam));
        inv.setBoots(colorArmor(new ItemStack(Material.LEATHER_BOOTS, 1), target.currentTeam));

        inv.setItem(0, new ItemStack(Material.STONE_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(3, new ItemStack(Material.GOLDEN_APPLE, 2));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }
}
