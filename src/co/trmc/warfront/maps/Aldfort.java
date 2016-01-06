package co.trmc.warfront.maps;

import co.trmc.dream.DreamPlayer;
import co.trmc.dream.game.DreamTeam;
import co.trmc.dream.game.util.SerializedLocation;
import co.trmc.dream.util.DreamUtil;
import co.trmc.warfront.gamemode.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Random;

public class Aldfort extends Map {

    String mapName = "Aldfort";
    String[] creators = {"Bashdoor"};
    String[] gamemodes = {"FFA", "LMS",};
    Material[] disabledDrops = {Material.IRON_BOOTS, Material.IRON_LEGGINGS, Material.IRON_CHESTPLATE, Material.IRON_HELMET,
            Material.STONE_SWORD, Material.BOW, Material.BOW, Material.FISHING_ROD, Material.COOKED_BEEF, Material.POTION,
            Material.FEATHER, Material.ARROW};
    DreamTeam team1 = new DreamTeam("Rebels", ChatColor.DARK_AQUA, 30);
    long timeLockTime = 15000L;

    public void readyAttributes() {
        setMapName(mapName);
        setCreators(creators);
        setGamemodes(gamemodes);
        setDisabledDrops(disabledDrops);
        setAllowBuild(false, false);
        setTimeLockTime(timeLockTime);
        registerTeam(team1);
        attributes.put("ffaKills", 5);
    }

    protected void readySpawns() {
        addTeamSpawn(team1, new SerializedLocation(-33, 79, 26, 52F, -9.6F));
        addTeamSpawn(team1, new SerializedLocation(-45, 74, 7, 316F, -3F));
        addTeamSpawn(team1, new SerializedLocation(-56, 74, 35, 236F, -5.7F));
        addTeamSpawn(team1, new SerializedLocation(-47, 68, 51, 255.7F, 8.9F));
        addTeamSpawn(team1, new SerializedLocation(-34, 75, 54, 218.5F, -1.3F));
        addTeamSpawn(team1, new SerializedLocation(-22, 79, 50, 202.3F, -10F));
        addTeamSpawn(team1, new SerializedLocation(-5, 68, 55, 188F, -3F));
        addTeamSpawn(team1, new SerializedLocation(-14, 68, 39, 230F, -1F));
        addTeamSpawn(team1, new SerializedLocation(-3, 68, 28, 89.1F, 1F));
        addTeamSpawn(team1, new SerializedLocation(-5, 68, 4, 48F, -5F));
        addTeamSpawn(team1, new SerializedLocation(-23, 68, 7, 224.2F, -1.8F));
        addTeamSpawn(team1, new SerializedLocation(-16, 74, 15, 48F, -1F));

        setSpectatorSpawn(new SerializedLocation(-2, 72, 3.3, 45F, 20F));
    }

    protected void applyInventory(DreamPlayer target) {
        PlayerInventory inv = target.getPlayer().getInventory();

        ItemStack IRON_BOOTS = new ItemStack(Material.IRON_BOOTS, 1);
        IRON_BOOTS.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);

        inv.setHelmet(new ItemStack(Material.IRON_HELMET, 1));
        inv.setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
        inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
        inv.setBoots(new ItemStack(Material.IRON_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.STONE_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.FISHING_ROD, 1, (short) 50));
        inv.setItem(3, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(4, new ItemStack(Material.POTION, 1, (short) 8229));
        inv.setItem(8, DreamUtil.changeItem(new ItemStack(Material.FEATHER, 1), "Effect Feather", null));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onEffectFeatherInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Action ac = event.getAction();

        if (ac == Action.RIGHT_CLICK_AIR || ac == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand() != null && p.getItemInHand().getType() == Material.FEATHER) {
                ItemStack hand = p.getItemInHand();
                hand.setAmount(1);
                p.getInventory().removeItem(hand);
                p.addPotionEffect(new PotionEffect(randomEffect(), 25 * 20, 0));
            }
        }
    }

    private PotionEffectType randomEffect() {
        PotionEffectType[] types = {PotionEffectType.SPEED, PotionEffectType.JUMP, PotionEffectType.SLOW,
                PotionEffectType.WEAKNESS, PotionEffectType.REGENERATION, PotionEffectType.DAMAGE_RESISTANCE,
                PotionEffectType.CONFUSION, PotionEffectType.BLINDNESS, PotionEffectType.INVISIBILITY};
        return Arrays.asList(types).get(new Random().nextInt(types.length));
    }
}
