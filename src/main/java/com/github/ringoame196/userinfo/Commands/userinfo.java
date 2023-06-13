package com.github.ringoame196.userinfo.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collections;

public class userinfo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
        // プレイヤーでない場合の処理
            commandSender.sendMessage(ChatColor.RED+"このコマンドはプレイヤーのみ実行できます");
            return true;
        }
        Player player = (Player) commandSender;
        if(!player.isOp())
        {//OPのみ実行可能
            player.sendMessage(ChatColor.RED+"このコマンドを実行する権限を持っていません");
            return true;
        }

        //チェストを開く音
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN,1,1);

        //プレイヤーヘッド
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta head_meta = (SkullMeta) head.getItemMeta();

        if(strings.length==0)
        {//全員表示
            int i = 0;
            Inventory inv = Bukkit.createInventory(null,27,ChatColor.DARK_AQUA+"プレイヤーリスト");
            player.openInventory(inv);
            for (Player forplayer : Bukkit.getOnlinePlayers()) {
                head_meta.setOwner(forplayer.getName());
                head_meta.setDisplayName(forplayer.getName());
                head.setItemMeta(head_meta);

                inv.setItem(i, head);
                i++;
                if(i==28)
                {//※二画面作るのがめんどかった(ボソ)
                    player.sendMessage(ChatColor.GOLD+"27人以上を同時表示できません");
                    return true;
                }
            }

            return true;
        }

        //GUI表示
        Player targetplayer= Bukkit.getPlayer(strings[0]);
        Inventory inv = Bukkit.createInventory(null,9,ChatColor.BLUE+targetplayer.getName()+"の情報(userinfo)");
        player.openInventory(inv);

        {
            if (targetplayer.isOp()) {//OPを持っていたら王冠表示
                ItemStack crown = new ItemStack(Material.GOLD_HELMET);
                ItemMeta meta = crown.getItemMeta();
                meta.setDisplayName(ChatColor.YELLOW + "王冠(OP)");
                crown.setItemMeta(meta);
                inv.setItem(0, crown);
            }
        }

        {//プレイヤーヘッドの設定
            head_meta.setOwner(targetplayer.getName());
            head_meta.setDisplayName(ChatColor.GREEN + "MCID:" + targetplayer.getName());
            head_meta.setLore(Collections.singletonList("UUID:" + targetplayer.getUniqueId().toString()));
            head.setItemMeta(head_meta);
            inv.setItem(1, head);
        }

        {
            ItemStack item = new ItemStack(Material.BEDROCK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD+"IP");
            meta.setLore(Collections.singletonList(targetplayer.getAddress().getAddress().getHostAddress()));
            item.setItemMeta(meta);
            inv.setItem(2, item);
        }

        {//ワールド名表示
            ItemStack item = new ItemStack(Material.GRASS);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA+"ワールド名");
            meta.setLore(Collections.singletonList(targetplayer.getWorld().getName()));
            item.setItemMeta(meta);
            inv.setItem(3, item);
        }

        {//ゲームムード表示
            ItemStack item = new ItemStack(Material.SLIME_BALL);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN+"ゲームモード");
            meta.setLore(Collections.singletonList(targetplayer.getGameMode().toString()));
            item.setItemMeta(meta);
            inv.setItem(4, item);
        }

        {//OP渡し
            ItemStack item = new ItemStack(Material.COMMAND);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW+"OPを渡す");
            item.setItemMeta(meta);
            inv.setItem(6, item);
        }

        {//KICK
            ItemStack item = new ItemStack(Material.ANVIL);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.RED+"キックする");
            item.setItemMeta(meta);
            inv.setItem(7, item);
        }

        {//BAN
            ItemStack item = new ItemStack(Material.TNT);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.DARK_RED+"BAN");
            item.setItemMeta(meta);
            inv.setItem(8, item);
        }

        return true;
    }
}
