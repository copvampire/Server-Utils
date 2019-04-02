package Main;

import XZot1K.plugins.zb.ZotBox;
import XZot1K.plugins.zb.libraries.inventorylib.CustomInventory;
import XZot1K.plugins.zb.libraries.inventorylib.CustomItem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Main extends JavaPlugin implements Listener
{

    private ZotBox zotBox;

    @Override
    public void onEnable()
    {
    	
        if (!isZotBoxInstalled())
        {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e"
                    + getName() + " &cwas unable to enable, due to &bZot&7Box &cnot being installed."));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(this, this);
    	getServer().getPluginManager().registerEvents(new DoubleJump(this), this);
    	getServer().getPluginManager().registerEvents(new JumpPad(this), this);
    	getServer().getPluginManager().registerEvents(new JumpPad(this), this);
    	getServer().getPluginManager().registerEvents(new DamagePreventionListener(), this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        e.getPlayer().getInventory().setItem(0, getJoinItem());
        getZotBox().getPacketLibrary().getTabListManager().sendCustomTabList(e.getPlayer(), "&6The Crafters Network", "&aWebsite: &6thecrafters.net");

        Player player = e.getPlayer();
        Location location = new Location(player.getWorld(),0.5,122,0.5);
        player.teleport(location);
        player.setGameMode(GameMode.ADVENTURE);
        
        
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
    	
    	Player player = e.getPlayer();
    	if (player.getGameMode().equals(GameMode.CREATIVE)) return;
    	
    	e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMoveItem(PlayerPickupItemEvent e) {
    	
    	Player player = e.getPlayer();
    	if (player.getGameMode().equals(GameMode.CREATIVE)) return;
    	
    	e.setCancelled(true);
    }

    @EventHandler
    public void onMove(InventoryClickEvent e) {

    	Player player = (Player)e.getWhoClicked();
    	
    	if (player.getGameMode().equals(GameMode.CREATIVE)) return;
    	
    	e.setCancelled(true);
    }

    

    @EventHandler
    public void onClick(InventoryClickEvent e)
    {
        if(e.getInventory().getName().equals(getSeverSelector().getName()))
        {
            e.setCancelled(true);
            ItemStack itemClicked = e.getCurrentItem();

            ItemStack factionItem = new ItemStack(Material.DIAMOND_SWORD, 1);
            ItemStack creativeItem = new ItemStack(Material.GRASS, 1);

			if(itemClicked != null && getZotBox().getInventoryLibrary().doItemsMatch(itemClicked, factionItem))
            {
                getZotBox().getGeneralLibrary().switchServer((Player) e.getWhoClicked(), "factions");
            }
			if(itemClicked != null && getZotBox().getInventoryLibrary().doItemsMatch(itemClicked, creativeItem))
            {
                getZotBox().getGeneralLibrary().switchServer((Player) e.getWhoClicked(), "creative");
            }

        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e)
    {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            ItemStack itemInHand = e.getItem(), joinItem = getJoinItem();
            if (itemInHand != null && getZotBox().getInventoryLibrary().doItemsMatch(itemInHand, joinItem))
            {
                    e.setCancelled(true);
                    e.getPlayer().openInventory(getSeverSelector());
            }
        }
    }
    

    public Inventory getSeverSelector()
    {
        CustomInventory customInventory = new CustomInventory(null, "&cServer Selector", 27);

        // add items to inventory
        customInventory.setSlot(11, new CustomItem(Material.DIAMOND_SWORD, 1, (short) 0).setDisplayName("&cFactions").setLore(new ArrayList<String>(){
            {
                add("");
                add("&aClick to go to factions.");
            };
        }).getItemStack());
        
        customInventory.setSlot(15, new CustomItem(Material.GRASS, 1, (short) 0).setDisplayName("&bCreative").setLore(new ArrayList<String>(){
            {
                add("");
                add("&aClick to go to creative.");
            };
        }).getItemStack());


        return customInventory.getInventory();
    }

    public ItemStack getJoinItem()
    {
        CustomItem customItem = new CustomItem(Material.COMPASS, 1, (short) 0);
        customItem.setDisplayName("&cServer Selector");
        customItem.setLore(new ArrayList<String>()
        {
            {
                add("");
                add("&aClick to select server");
            }
        });

        return customItem.getItemStack();
    }


    private boolean isZotBoxInstalled()
    {
        ZotBox zotBox = (ZotBox) getServer().getPluginManager().getPlugin("ZotBox");
        if (zotBox != null)
        {
            setZotBox(zotBox);
            return true;
        }
        return false;
    }

    public ZotBox getZotBox()
    {
        return zotBox;
    }

    private void setZotBox(ZotBox zotBox)
    {
        this.zotBox = zotBox;
    }

}
