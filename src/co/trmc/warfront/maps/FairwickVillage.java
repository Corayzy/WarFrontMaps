package co.trmc.warfront.maps;

import co.trmc.dream.DreamPlayer;
import co.trmc.dream.game.DreamTeam;
import co.trmc.dream.game.util.SerializedLocation;
import co.trmc.dream.util.DreamManager;
import co.trmc.dream.util.DreamUtil;
import co.trmc.warfront.gamemode.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class FairwickVillage extends Map {

    String mapName = "Fairwick Village";
    String[] creators = {"ILavaYou"};
    String[] gamemodes = {"LTS", "CTF"};
    Material[] disabledDrops = defaultDisabledDrops();
    DreamTeam team1 = new DreamTeam("Red Team", ChatColor.RED, 35);
    DreamTeam team2 = new DreamTeam("Blue Team", ChatColor.BLUE, 35);
    long timeLockTime = 14000L;

    public void readyAttributes() {
        setMapName(mapName);
        setCreators(creators);
        setGamemodes(gamemodes);
        setDisabledDrops(disabledDrops);
        setAllowPlace(false);
        setTimeLockTime(timeLockTime);
        registerTeam(team1);
        registerTeam(team2);
        attributes.put("captureRequirement", 3);
    }

    @SuppressWarnings("unchecked")
    protected void readySpawns() {
        addTeamSpawn(team1, new SerializedLocation(108.5, 73, 26.5, 0.76F, -1F));
        addTeamSpawn(team1, new SerializedLocation(36.5, 73, 26.5, 0.76F, -1F));

        addTeamSpawn(team2, new SerializedLocation(36.5, 73, 107.5, 180F, -0.767F));
        addTeamSpawn(team2, new SerializedLocation(108.5, 73, 107.5, 180F, -0.767F));

        setSpectatorSpawn(new SerializedLocation(72.5, 78, 67.5, -90F, 0F));

        attributes.put("flags", new HashMap<String, SerializedLocation>());
        ((HashMap<String, SerializedLocation>) attributes.get("flags")).put(team1.getTeamName(), new SerializedLocation(72, 74, -2));
        ((HashMap<String, SerializedLocation>) attributes.get("flags")).put(team2.getTeamName(), new SerializedLocation(72, 74, 136));
    }

    protected void applyInventory(DreamPlayer target) {
        PlayerInventory inv = target.getPlayer().getInventory();

        ItemStack GADGET = DreamUtil.changeItem(new ItemStack(Material.WATCH, 1), ChatColor.BLUE + "Invisi-gadget", null);

        inv.setHelmet(new ItemStack(Material.IRON_HELMET, 1));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.currentTeam));
        inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
        inv.setBoots(new ItemStack(Material.IRON_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.IRON_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(3, new ItemStack(Material.GOLDEN_APPLE, 1));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
        inv.setItem(8, GADGET);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        Material brokenMat = event.getBlock().getType();
        if (brokenMat != Material.STAINED_GLASS_PANE) event.setCancelled(true);
    }

    @EventHandler
    public void onSpyWatchInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Action ac = event.getAction();
        if (ac == Action.RIGHT_CLICK_AIR || ac == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand().getType() == Material.WATCH) {
                p.getInventory().remove(p.getItemInHand());
                p.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
                p.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
                p.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
                p.getInventory().setBoots(new ItemStack(Material.AIR, 1));
                p.getInventory().remove(new ItemStack(Material.IRON_BOOTS));
                p.getInventory().remove(new ItemStack(Material.IRON_LEGGINGS));
                p.getInventory().remove(new ItemStack(Material.IRON_HELMET));
                ItemStack LEATHER_CHESTPLATE = colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), DreamManager.getInstance().getDreamPlayer(p).currentTeam);
                p.getInventory().remove(LEATHER_CHESTPLATE);
                p.getInventory().addItem(new ItemStack(Material.IRON_BOOTS));
                p.getInventory().addItem(new ItemStack(Material.IRON_LEGGINGS));
                p.getInventory().addItem(new ItemStack(Material.IRON_HELMET));
                p.getInventory().addItem(LEATHER_CHESTPLATE);
                p.updateInventory();
                p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 15 * 20, 0));
            }
        }
    }
}
