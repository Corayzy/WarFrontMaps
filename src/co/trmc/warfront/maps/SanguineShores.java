package co.trmc.warfront.maps;

import co.trmc.dream.DreamPlayer;
import co.trmc.dream.game.DreamTeam;
import co.trmc.dream.game.util.SerializedLocation;
import co.trmc.warfront.gamemode.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SanguineShores extends Map {

    String mapName = "Sanguine Shores";
    String[] creators = {"Bashdoor", "ep1cn00bt00b"};
    String[] gamemodes = {"TDM", "KoTH"};
    Material[] disabledDrops = defaultDisabledDrops();
    DreamTeam team1 = new DreamTeam("Tourists", ChatColor.DARK_PURPLE, 20);
    DreamTeam team2 = new DreamTeam("Locals", ChatColor.AQUA, 20);
    long timeLockTime = 6000L;

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
        addTeamSpawn(team1, new SerializedLocation(68, 93, -3, 88.9F, 1.7F));

        addTeamSpawn(team2, new SerializedLocation(-69, 93, 3, 263.8F, -1.75F));

        setSpectatorSpawn(new SerializedLocation(0.5, 108, 52.5, 180F, 10F));
        attributes.put("kothFlag", new SerializedLocation(0, 93, 0));
    }

    protected void applyInventory(DreamPlayer target) {
        PlayerInventory inv = target.getPlayer().getInventory();

        inv.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET, 1));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.currentTeam));
        inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
        inv.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.IRON_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.PUMPKIN_PIE, 6));
        inv.setItem(3, new ItemStack(Material.GOLDEN_APPLE, 1));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }
}
