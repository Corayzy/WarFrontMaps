package co.trmc.warfront.maps.deprecated;

import co.trmc.dream.DreamPlayer;
import co.trmc.dream.game.DreamTeam;
import co.trmc.dream.game.util.SerializedLocation;
import co.trmc.warfront.gamemode.Map;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * This map is a tournament-only map and should not be set.
 *
 * @since 1.0.2
 */
public class DynamicGTM1 extends Map {

    String mapName = "Dynamic GTM 1";
    String[] creators = {"[TR]"};
    String[] gamemodes = {"GTM"};
    Material[] disabledDrops = defaultDisabledDrops();
    DreamTeam team1 = new DreamTeam("Defense", ChatColor.RED, 8);
    DreamTeam team2 = new DreamTeam("Offense", ChatColor.GREEN, 8);
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
        addTeamSpawn(team1, new SerializedLocation(0.5, 164, -39.5, 0F, 0F));
        addTeamSpawn(team1, new SerializedLocation(40.5, 164, 1.5, 90F, 0F));
        addTeamSpawn(team1, new SerializedLocation(0.5, 164, 41.5, 180F, 0F));
        addTeamSpawn(team1, new SerializedLocation(-40.5, 164, 1.5, 270F, 0F));

        addTeamSpawn(team2, new SerializedLocation(60.5, 155, 1.5, 90F, 0F));
        addTeamSpawn(team2, new SerializedLocation(0.5, 155, 61.5, 180F, 0F));
        addTeamSpawn(team2, new SerializedLocation(-60.5, 155, 1.5, 270F, 0F));
        addTeamSpawn(team2, new SerializedLocation(0.5, 155, -59.5, 0F, 0F));

        setSpectatorSpawn(new SerializedLocation(0, 102, 64, 180F, 0F));

        //objectives.add(new Monument(-2, 93, -1, 2, 97, 3, Material.STAINED_CLAY));
    }

    protected void applyInventory(DreamPlayer target) {
        PlayerInventory inv = target.getPlayer().getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.currentTeam));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.currentTeam));
        inv.setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), target.currentTeam));
        inv.setBoots(colorArmor(new ItemStack(Material.LEATHER_BOOTS, 1), target.currentTeam));

        inv.setItem(0, new ItemStack(Material.STONE_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.STONE_PICKAXE, 1));
        inv.setItem(3, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(4, new ItemStack(Material.GOLDEN_APPLE, 1));
        inv.setItem(5, new ItemStack(Material.STONE, 64));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Location equiv = event.getBlock().getLocation().clone();
        equiv.setY(81);
        if (equiv.getBlock().getType() != Material.BEDROCK) event.setCancelled(true);
        equiv.setY(90);
        if (equiv.getBlock().getType() == Material.ENDER_PORTAL_FRAME)
            event.setCancelled(true);
        equiv.setY(81);
        if (equiv.getBlock().getType() == Material.ENDER_PORTAL_FRAME)
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Location equiv = event.getBlock().getLocation().clone();
        if (equiv.getY() > 100) {
            event.setCancelled(true);
            return;
        }
        equiv.setY(81);
        if (equiv.getBlock().getType() != Material.BEDROCK) event.setCancelled(true);
        equiv.setY(90);
        if (equiv.getBlock().getType() == Material.ENDER_PORTAL_FRAME)
            event.setCancelled(true);
        equiv.setY(81);
        if (equiv.getBlock().getType() == Material.ENDER_PORTAL_FRAME)
            event.setCancelled(true);
    }
}
