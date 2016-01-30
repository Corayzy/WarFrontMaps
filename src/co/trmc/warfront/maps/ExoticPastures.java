package co.trmc.warfront.maps;

import co.trmc.dream.DreamPlayer;
import co.trmc.dream.game.DreamTeam;
import co.trmc.dream.game.util.Activatable;
import co.trmc.dream.game.util.SerializedLocation;
import co.trmc.dream.util.DreamUtil;
import co.trmc.warfront.gamemode.Map;
import co.trmc.warfront.gamemode.cores.util.DTMMonument;
import co.trmc.warfront.gamemode.cores.util.NoBuildRegion;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Egg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;

public class ExoticPastures extends Map {

    String mapName = "Exotic Pastures";
    String[] creators = {"danshrdr", "doritopig", "ILavaYou"};
    String[] gamemodes = {"TDM", "LP", "DTM"};
    ArrayList<DTMMonument> monuments = new ArrayList<DTMMonument>();
    Material[] disabledDrops = defaultDisabledDrops();
    DreamTeam team1 = new DreamTeam("Farmhands", ChatColor.GOLD, 40);
    DreamTeam team2 = new DreamTeam("Ranchers", ChatColor.DARK_GREEN, 40);
    long timeLockTime = 15000L;

    public void readyAttributes() {
        setMapName(mapName);
        setCreators(creators);
        setGamemodes(gamemodes);
        setDisabledDrops(disabledDrops);
        setAllowBuild(true, true);
        setTimeLockTime(timeLockTime);
        registerTeam(team1);
        registerTeam(team2);
        attributes.put("remainRequirement", 0);
    }

    protected void readySpawns() {
        addTeamSpawn(team2, new SerializedLocation(-48, 134, -45, 90F, 0F));
        addTeamSpawn(team1, new SerializedLocation(46, 134, -1, 270F, 0F));

        setSpectatorSpawn(new SerializedLocation(-0.5, 110, -74.5, 0F, 0F));


        ((ArrayList<Activatable>) attributes.get("noBuild")).add(new NoBuildRegion(-44, -41, -52, -49));
        ((ArrayList<Activatable>) attributes.get("noBuild")).add(new NoBuildRegion(42, -5, 50, 3));

        monuments.add(new DTMMonument(36, 108, -22, 38, 112, -24, Material.OBSIDIAN, team1));
        monuments.add(new DTMMonument(-38, 108, -24, -40, 112, -22, Material.OBSIDIAN, team2));
        attributes.put("monuments", monuments);
    }

    protected void applyInventory(DreamPlayer target) {
        PlayerInventory inv = target.getPlayer().getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.currentTeam));
        inv.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE, 1));
        inv.setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), target.currentTeam));
        inv.setBoots(new ItemStack(Material.IRON_BOOTS, 1));

        ItemStack HOE = new ItemStack(Material.GOLD_HOE, 1);
        HOE.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);

        ItemStack ROTOTILL = new ItemStack(Material.EGG, 5);
        ROTOTILL = DreamUtil.changeItem(ROTOTILL, "Insta-Rototill", "Throw this at grass to instantly become an awesome farmer!");

        inv.setItem(0, HOE);
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.DIAMOND_PICKAXE, 1));
        inv.setItem(3, new ItemStack(Material.STONE_AXE, 1));
        inv.setItem(4, new ItemStack(Material.BREAD, 6));
        inv.setItem(5, new ItemStack(Material.GOLDEN_APPLE, 2));
        inv.setItem(6, new ItemStack(Material.LOG, 16));
        inv.setItem(7, ROTOTILL);
        inv.setItem(10, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Location equiv = event.getBlock().getLocation().clone();
        equiv.setY(77);
        if (equiv.getBlock().getType() != Material.BEDROCK) event.setCancelled(true);
    }

    @EventHandler
    public void rototill(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Egg)) return;
        BlockIterator iterator = new BlockIterator(event.getEntity().getWorld(), event.getEntity().getLocation().toVector(), event.getEntity().getVelocity().normalize(), 0.0D, 4);
        Block hitBlock = null;
        while (iterator.hasNext()) {
            hitBlock = iterator.next();

            if (hitBlock.getTypeId() != 0) {
                break;
            }
        }

        assert hitBlock != null;
        if (hitBlock.getType() == Material.GRASS) {
            if (hitBlock.getRelative(BlockFace.UP).getType() == Material.AIR) {
                hitBlock.getWorld().playEffect(hitBlock.getLocation(), Effect.STEP_SOUND, hitBlock.getTypeId());
                hitBlock.setType(Material.SOIL);
                hitBlock.setData((byte) 1);
                hitBlock.getRelative(BlockFace.UP).setType(Material.CROPS);
                hitBlock.getRelative(BlockFace.UP).setData((byte) 7);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Location equiv = event.getBlock().getLocation().clone();
        equiv.setY(77);
        if (equiv.getBlock().getType() != Material.BEDROCK) event.setCancelled(true);
    }
}
