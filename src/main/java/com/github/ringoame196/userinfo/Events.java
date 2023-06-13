package com.github.ringoame196.userinfo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        String inv_name = e.getView().getTitle();
        ItemStack clickitem = e.getCurrentItem();
        String targetplayer = ChatColor.stripColor(inv_name.replace("の情報(userinfo)", ""));
        if(clickitem==null)
        {
            return;
        }
        if(inv_name.contains("の情報(userinfo)"))
        {//GUIクリック
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK,1,1);
            e.setCancelled(true);
            if(clickitem.getType().equals(Material.COMMAND)&&clickitem.getItemMeta().getDisplayName().equals(ChatColor.YELLOW+"OPを渡す"))
            {//OP
                Bukkit.dispatchCommand(player, "op "+targetplayer);
                player.closeInventory();
            }
            else if(clickitem.getType().equals(Material.ANVIL)&&clickitem.getItemMeta().getDisplayName().equals(ChatColor.RED+"キックする"))
            {//KICK
                Bukkit.dispatchCommand(player, "kick "+targetplayer);
                player.closeInventory();
            }
            else if(clickitem.getType().equals(Material.TNT)&&clickitem.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED+"BAN"))
            {//BAN
                Bukkit.dispatchCommand(player, "ban "+targetplayer);
                player.closeInventory();
            }
        }
        else if (inv_name.equals(ChatColor.DARK_AQUA+"プレイヤーリスト"))
        {//プレイヤーリストのクリック処理
            e.setCancelled(true);

            if(clickitem.getType().equals(Material.SKULL_ITEM))
            {
                player.playSound(player.getLocation(),Sound.ENTITY_ARROW_HIT_PLAYER,1,1);
                String player_name = clickitem.getItemMeta().getDisplayName();
                Bukkit.dispatchCommand(player, "userinfo " + player_name);
            }
        }
    }
}
