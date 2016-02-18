package co.trmc.warfront.maps;

import co.trmc.dream.DreamPlayer;
import co.trmc.dream.game.DreamTeam;
import co.trmc.dream.game.util.SerializedLocation;
import co.trmc.dream.util.DreamUtil;
import co.trmc.warfront.gamemode.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class Zoned extends Map {

    String mapName = "Zoned";
    String[] creators = {"ILavaYou", "SS_Officer", "Trilexium"};
    String[] gamemodes = {"CTF", "LTS"};
    Material[] disabledDrops = defaultDisabledDrops();
    DreamTeam team1 = new DreamTeam("Magenta Team", ChatColor.DARK_PURPLE, 25);
    DreamTeam team2 = new DreamTeam("Cyan Team", ChatColor.DARK_AQUA, 25);
    long timeLockTime = 14000L;

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

    @SuppressWarnings("unchecked")
    protected void readySpawns() {
        addTeamSpawn(team1, new SerializedLocation(37, 93, 98, 120.4F, 0F));
        addTeamSpawn(team1, new SerializedLocation(-43, 93, 99, 228.7F, 1.6F));

        addTeamSpawn(team2, new SerializedLocation(-43, 93, -76, 316F, 1.8F));
        addTeamSpawn(team2, new SerializedLocation(37, 93, -76, 46F, 0.6F));

        setSpectatorSpawn(new SerializedLocation(55, 92, 11, 90F, 0F));

        attributes.put("flags", new HashMap<String, SerializedLocation>());
        ((HashMap<String, SerializedLocation>) attributes.get("flags")).put(team1.getTeamName(), new SerializedLocation(-3, 89, 78));
        ((HashMap<String, SerializedLocation>) attributes.get("flags")).put(team2.getTeamName(), new SerializedLocation(-3, 89, -56));
    }

    protected void applyInventory(DreamPlayer target) {
        PlayerInventory inv = target.getPlayer().getInventory();

        ItemStack GADGET = DreamUtil.changeItem(new ItemStack(Material.EYE_OF_ENDER, 1), ChatColor.BLUE + "Eye of Vigor",
                ChatColor.BLUE + "Take a whiff and you'll be stealing flags like unchained bicyles!");

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.currentTeam));
        inv.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
        inv.setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), target.currentTeam));
        inv.setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.IRON_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(3, new ItemStack(Material.GOLDEN_APPLE, 1));
        inv.setItem(7, new ItemStack(Material.ENDER_PEARL, 3));
        inv.setItem(8, GADGET);
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        event.setTo(event.getTo().clone().add(0, 1, 0));
    }

    @EventHandler
    public void onDyeInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Action ac = event.getAction();
        if (ac == Action.RIGHT_CLICK_AIR || ac == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand().getType() == Material.EYE_OF_ENDER) {
                p.getInventory().remove(p.getItemInHand());
                p.sendMessage(ChatColor.GOLD + "You suddenly feel a great rush through your body...");
                p.updateInventory();
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 10 * 20, 9));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10 * 20, 2));
            }
        }
    }
}
