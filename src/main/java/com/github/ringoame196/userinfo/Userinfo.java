package com.github.ringoame196.userinfo;

import com.github.ringoame196.userinfo.Commands.userinfo;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Userinfo extends JavaPlugin {
    private static JavaPlugin plugin;
    private Events events;
    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();
        getCommand("userinfo").setExecutor(new userinfo());
        this.events = new Events();
        Bukkit.getPluginManager().registerEvents(this.events,this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        super.onDisable();
    }
    public static JavaPlugin getPlugin(){return plugin;}
}
