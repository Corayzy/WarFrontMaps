package co.trmc.warfront.maps;

import co.trmc.dream.DreamPlayer;
import co.trmc.dream.game.DreamTeam;
import co.trmc.dream.game.util.SerializedLocation;
import co.trmc.dream.util.DreamManager;
import co.trmc.dream.util.DreamUtil;
import co.trmc.warfront.RoundHelper;
import co.trmc.warfront.WFP;
import co.trmc.warfront.WarFront;
import co.trmc.warfront.gamemode.Map;
import co.trmc.warfront.gamemode.cores.util.Territory;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Conflict extends Map {
    ArrayList<String> selecting = new ArrayList<String>();

    String mapName = "Conflict";
    String[] creators = {"ILavaYou", "Milkbottle235"};
    String[] gamemodes = {"TDM", "LP", "DDM"};
    ArrayList<Territory> territories = new ArrayList<Territory>();
    Material[] disabledDrops = {Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE,
            Material.LEATHER_HELMET, Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_CHESTPLATE,
            Material.CHAINMAIL_HELMET, Material.IRON_BOOTS, Material.IRON_LEGGINGS, Material.IRON_CHESTPLATE,
            Material.IRON_HELMET, Material.GOLD_BOOTS, Material.GOLD_LEGGINGS, Material.GOLD_CHESTPLATE, Material.GOLD_HELMET,
            Material.DIAMOND_BOOTS, Material.DIAMOND_LEGGINGS, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_HELMET,
            Material.WOOD_SWORD, Material.STONE_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.DIAMOND_SWORD, Material.BOW,
            Material.ARROW, Material.POTION, Material.WEB, Material.BLAZE_ROD, Material.LEAVES, Material.FLINT,
            Material.DIRT, Material.IRON_PICKAXE, Material.STONE_PICKAXE, Material.STICK, Material.GOLDEN_APPLE,
            Material.FLINT_AND_STEEL, Material.TNT, Material.LOG, Material.WOOD, Material.SIGN, Material.FENCE,
            Material.STONE,};
    DreamTeam team1 = new DreamTeam("Battalion", ChatColor.DARK_GRAY, 30);
    DreamTeam team2 = new DreamTeam("Faction", ChatColor.GRAY, 30);
    long timeLockTime = 14500L;

    public void readyAttributes() {
        setMapName(mapName);
        setCreators(creators);
        setGamemodes(gamemodes);
        setDisabledDrops(disabledDrops);
        setAllowBuild(true, true);
        setTimeLockTime(timeLockTime);
        registerTeam(team1);
        registerTeam(team2);
    }

    protected void readySpawns() {
        addTeamSpawn(team1, new SerializedLocation(-60.5, 103, 145.5, 180F, 0F));
        addTeamSpawn(team2, new SerializedLocation(-60.5, 103, -12.5, 0F, 0F));
        setSpectatorSpawn(new SerializedLocation(-8.5, 111, 66.5, 90F, 0F));

        territories.add(new Territory(-57, 104, 132, -64, 107, 133, team1.getTeamName()));
        territories.add(new Territory(-65, 104, -1, -58, 107, 0, team2.getTeamName()));
        attributes.put("territories", territories);
    }

    protected void applyInventory(DreamPlayer target) {
        target.sendMessage(ChatColor.GRAY + "Click a sign to get a class!");
        target.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 8 * 20, 5));
        target.getPlayer().getInventory().addItem(new ItemStack(Material.WOOD_SWORD));
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();
            if (selecting.contains(player.getName())) {
                player.sendMessage(ChatColor.RED + "You are already selecting a class, please wait!");
                return;
            }
            Block block = event.getClickedBlock();

            if (block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
                BlockState state = block.getState();
                Sign sign = (Sign) state;
                // Sign options
                if (sign.getLine(1).contains("Great Warrior")) {
                    handKit(player, Group.GREAT_WARRIOR);
                }
                if (sign.getLine(1).contains("Thief")) {
                    handKit(player, Group.THIEF);
                    return;
                }
                if (sign.getLine(1).contains("Bowmaster")) {
                    handKit(player, Group.BOWMASTER);
                    return;
                }
                if (sign.getLine(1).contains("Medic")) {
                    handKit(player, Group.MEDIC);
                    return;
                }
                if (sign.getLine(1).contains("Miner")) {
                    handKit(player, Group.MINER);
                    return;
                }
                if (sign.getLine(1).contains("Rogue")) {
                    handKit(player, Group.ROGUE);
                    return;
                }
                if (sign.getLine(1).contains("Lumberjack")) {
                    handKit(player, Group.LUMBERJACK);
                    return;
                }
                if (sign.getLine(1).contains("Maniac")) {
                    handKit(player, Group.MANIAC);
                    return;
                }
                if (sign.getLine(1).contains("Fighter")) {
                    handKit(player, Group.FIGHTER);
                }
            }
        } catch (NullPointerException ex) {
            // Catches null ClickedBlock
        }
    }

    private void handKit(final Player player, final Group group) {
        selecting.add(player.getName());
        player.sendMessage(ChatColor.GREEN + "You have chosen " + ChatColor.AQUA + group.toString().toLowerCase().replace("_", " ") + ChatColor.GREEN + " as your class!");

        player.getInventory().clear();
        player.updateInventory();
        player.getInventory().setArmorContents(new ItemStack[4]);
        player.updateInventory();

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        final WFP wfp = (WFP) DreamManager.getInstance().getDreamPlayer(player);

        Bukkit.getScheduler().runTaskLater(WarFront.getInstance(), new Runnable() {
            public void run() {
                switch (group) {
                    case MANIAC:
                        ItemStack STICK = new ItemStack(Material.STICK, 1);

                        STICK.addUnsafeEnchantment(Enchantment.KNOCKBACK, 10);
                        STICK.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                        DreamUtil.changeItem(STICK, "Batter up!", "Knock enemies flying!");

                        player.getInventory().setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), wfp.currentTeam));
                        player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
                        player.getInventory().setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), wfp.currentTeam));
                        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));

                        player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD, 1));
                        player.getInventory().setItem(1, STICK);
                        player.getInventory().setItem(2, new ItemStack(Material.COOKED_BEEF, 3));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20000 * 20, 0));
                        break;
                    case GREAT_WARRIOR:
                        ItemStack SWORD = new ItemStack(Material.STONE_SWORD, 1, (short) -200);

                        SWORD.addEnchantment(Enchantment.KNOCKBACK, 2);
                        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
                        player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
                        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
                        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));

                        player.getInventory().setItem(0, SWORD);
                        player.getInventory().setItem(1, new ItemStack(Material.COOKED_BEEF, 3));
                        player.getInventory().setItem(8, new ItemStack(Material.WEB, 8));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20000 * 20, 1));
                        break;
                    case BOWMASTER:
                        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS, 1));
                        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
                        player.getInventory().setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), wfp.currentTeam));
                        player.getInventory().setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), wfp.currentTeam));

                        ItemStack AMMO = new ItemStack(Material.FLINT, 32);
                        ItemStack BLAZE_ROD = new ItemStack(Material.BLAZE_ROD, 1);
                        ItemStack SBOW = new ItemStack(Material.BOW, 1);

                        SBOW.addEnchantment(Enchantment.ARROW_DAMAGE, 1);

                        ItemMeta blaze_rod = BLAZE_ROD.getItemMeta();
                        blaze_rod.setDisplayName(ChatColor.BLUE + "Gun");
                        BLAZE_ROD.setItemMeta(blaze_rod);

                        ItemMeta ammo = AMMO.getItemMeta();
                        ammo.setDisplayName(ChatColor.BLUE + "Round");
                        AMMO.setItemMeta(ammo);

                        player.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD, 1));
                        player.getInventory().setItem(1, SBOW);
                        player.getInventory().setItem(2, new ItemStack(Material.COOKED_BEEF, 3));
                        player.getInventory().setItem(3, BLAZE_ROD);
                        player.getInventory().setItem(8, new ItemStack(Material.DIRT, 16));
                        player.getInventory().setItem(9, new ItemStack(Material.ARROW, 64));
                        player.getInventory().setItem(10, AMMO);
                        break;
                    case MEDIC:
                        player.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET, 1));
                        player.getInventory().setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1),
                                wfp.currentTeam));
                        player.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS, 1));
                        player.getInventory().setBoots(colorArmor(new ItemStack(Material.LEATHER_BOOTS, 1),
                                wfp.currentTeam));
                        player.getInventory().setItem(0, new ItemStack(Material.GOLD_SWORD, 1));
                        player.getInventory().setItem(1, new ItemStack(Material.POTION, 48, (short) 16437));
                        player.getInventory().setItem(2, new ItemStack(Material.COOKED_BEEF, 3));
                        player.getInventory().setItem(8, new ItemStack(Material.DIRT, 16));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20000 * 20, 0));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20000 * 20, 0));
                        break;
                    case ROGUE:
                        player.getInventory().setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), wfp.currentTeam));
                        player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
                        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));


                        player.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD, 1));
                        player.getInventory().setItem(1, new ItemStack(Material.GOLDEN_APPLE, 2));
                        player.getInventory().setItem(7, new ItemStack(Material.FLINT_AND_STEEL, 2));
                        player.getInventory().setItem(8, new ItemStack(Material.TNT, 16));

                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20000 * 20, 0));
                        break;
                    case THIEF:
                        player.getInventory().setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1),
                                wfp.currentTeam));
                        player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));

                        player.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD, 1));
                        player.getInventory().setItem(1, new ItemStack(Material.COOKED_BEEF, 3));

                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20000 * 20, 1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20000 * 20, 1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20000 * 20, 0));
                        break;
                    case MINER:
                        player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
                        player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
                        player.getInventory().setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), wfp.currentTeam));
                        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS, 1));

                        player.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD, 1));
                        player.getInventory().setItem(1, new ItemStack(Material.STONE_PICKAXE, 1));
                        player.getInventory().setItem(2, new ItemStack(Material.COOKED_BEEF, 3));
                        player.getInventory().setItem(8, new ItemStack(Material.STONE, 32));
                        break;
                    case LUMBERJACK:
                        player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
                        player.getInventory().setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1),
                                wfp.currentTeam));
                        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS, 1));

                        player.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD, 1));
                        player.getInventory().setItem(1, new ItemStack(Material.IRON_AXE, 1));
                        player.getInventory().setItem(2, new ItemStack(Material.COOKED_BEEF, 3));
                        player.getInventory().setItem(8, new ItemStack(Material.LOG, 8));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20000 * 20, 0));
                        break;
                    case FIGHTER:
                        player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET, 1));
                        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
                        player.getInventory().setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), wfp.currentTeam));
                        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));

                        player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD, 1));
                        player.getInventory().setItem(1, new ItemStack(Material.COOKED_BEEF, 3));
                        player.getInventory().setItem(6, new ItemStack(Material.WEB, 3));
                        player.getInventory().setItem(7, new ItemStack(Material.LEAVES, 16));
                        player.getInventory().setItem(8, new ItemStack(Material.DIRT, 32));
                        break;
                }
                selecting.remove(player.getName());
                if (player.getLocation().getZ() < 0)
                    player.teleport(new SerializedLocation(-60.5, 111, -9.5, 0F, 0F).toLocation(RoundHelper.getInstance()
                            .getCurrentWorld(), true));
                else
                    player.teleport(new SerializedLocation(-60.5, 111, 142.5, 180F, 0F).toLocation(RoundHelper
                            .getInstance().getCurrentWorld(), true));
            }
        }, 30L);
    }

    @EventHandler
    public void gun(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        Action action = event.getAction();
        ItemStack itemStack = player.getItemInHand();
        Inventory inventory = player.getInventory();
        Material tool = itemStack.getType();
        final World world = location.getWorld();

        if (tool == Material.BLAZE_ROD) {

            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {

                if (inventory.contains(Material.FLINT)) {
                    player.launchProjectile(Arrow.class);
                    world.playSound(location, Sound.COW_WALK, 10, 10);
                    ItemStack AMMO = new ItemStack(Material.FLINT, 1);
                    inventory.removeItem(AMMO);

                    ItemMeta ammo = AMMO.getItemMeta();
                    ammo.setDisplayName(ChatColor.BLUE + "Round");
                    AMMO.setItemMeta(ammo);
                    inventory.removeItem(AMMO);

                    // Make it remove normal flints, too.
                    player.updateInventory();
                } else {
                    world.playSound(location, Sound.CLICK, 10, 10);
                }

            }

        }
    }

    @EventHandler
    public void PotionsSplash(PotionSplashEvent event) {
        if (!(event.getPotion().getShooter() instanceof Player)) return;
        Player shooter = (Player) event.getPotion().getShooter();
        for (LivingEntity entity : event.getAffectedEntities()) {
            if (entity instanceof Player) {
                if ((entity.getName().equals(shooter.getName()))) {
                    shooter.sendMessage(ChatColor.GRAY + "Your potion had no effect on yourself!");
                    event.setIntensity(entity, 0);
                }
            }
        }
    }

    @EventHandler
    public void arrowAway(ProjectileHitEvent event) {
        Entity projectile = event.getEntity();
        if (projectile instanceof Arrow) {
            Arrow arrow = (Arrow) projectile;
            arrow.remove();
        }
    }

    @EventHandler
    public void explode(EntityExplodeEvent event) {
        ArrayList<Block> blocksToRemove = new ArrayList<Block>();
        for (Block b : event.blockList())
            if (!insideRegion(b.getLocation())) blocksToRemove.add(b);
        event.blockList().removeAll(blocksToRemove);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!insideRegion(event.getBlock().getLocation()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType() == Material.SIGN ||
                event.getBlockPlaced().getType() == Material.SIGN_POST ||
                event.getBlockPlaced().getType() == Material.WALL_SIGN)
            event.setCancelled(true);
        if (!insideRegion(event.getBlockPlaced().getLocation()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(HangingBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof ItemFrame)
            if (!insideRegion(event.getEntity().getLocation()))
                event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof ItemFrame)
            event.setCancelled(true);
    }

    @EventHandler
    public void drop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void click(InventoryClickEvent event) {
        if (event.getInventory().getType() != InventoryType.PLAYER && event.getInventory().getType() !=
                InventoryType.CRAFTING)
            event.setCancelled(true);
    }

    private boolean insideRegion(Location loc) {
        return loc.getZ() > 13 && loc.getZ() < 119 && loc.getX() > -88 && loc.getX() < -33;
    }

    public enum Group {
        MEDIC,
        BOWMASTER,
        ROGUE,
        THIEF,
        MINER,
        LUMBERJACK,
        GREAT_WARRIOR,
        MANIAC,
        FIGHTER
    }
}