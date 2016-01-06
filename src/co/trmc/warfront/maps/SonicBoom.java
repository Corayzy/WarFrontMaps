package co.trmc.warfront.maps;

import co.trmc.dream.DreamPlayer;
import co.trmc.dream.game.DreamTeam;
import co.trmc.dream.game.util.SerializedLocation;
import co.trmc.warfront.gamemode.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SonicBoom extends Map {

    String mapName = "Sonic Boom";
    String[] creators = {"s4y"};
    String[] gamemodes = {"FFA", "LMS"};
    Material[] disabledDrops = {Material.STONE_SWORD, Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE,
            Material.LEATHER_HELMET, Material.BOW, Material.ARROW};
    DreamTeam team1 = new DreamTeam("Survivors", ChatColor.YELLOW, 35);
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
        addTeamSpawn(team1, new SerializedLocation(0, 73, 22, 180F, -0.9F));
        addTeamSpawn(team1, new SerializedLocation(-17, 76, 17, 225F, 0.8F));
        addTeamSpawn(team1, new SerializedLocation(-22, 73, 0, 270F, 0.2F));
        addTeamSpawn(team1, new SerializedLocation(-16, 76, -16, 318F, -0.8F));
        addTeamSpawn(team1, new SerializedLocation(0, 73, 22, 359.6F, -0.5F));
        addTeamSpawn(team1, new SerializedLocation(16, 76, -16, 43.1F, 0.4F));
        addTeamSpawn(team1, new SerializedLocation(22, 73, 0, 89F, 0.5F));
        addTeamSpawn(team1, new SerializedLocation(17, 76, 17, 132.3F, 1.7F));
        addTeamSpawn(team1, new SerializedLocation(-40, 73, 0, 270F, -5.3F));
        addTeamSpawn(team1, new SerializedLocation(-29, 74, -29, 304F, -4.7F));
        addTeamSpawn(team1, new SerializedLocation(0, 73, -40, 358.7F, 0.444F));
        addTeamSpawn(team1, new SerializedLocation(29, 74, 29, 40.8F, -7F));
        addTeamSpawn(team1, new SerializedLocation(39, 73, 0, 87.3F, -0.1F));
        addTeamSpawn(team1, new SerializedLocation(29, 74, 29, 130.7F, -8.9F));
        addTeamSpawn(team1, new SerializedLocation(0, 73, 40, 180F, 2.6F));
        addTeamSpawn(team1, new SerializedLocation(-29, 74, 29, 223.1F, -10.2F));

        setSpectatorSpawn(new SerializedLocation(0.5, 72, 0.5, 0F, 0F));
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
